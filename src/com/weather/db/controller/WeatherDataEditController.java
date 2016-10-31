package com.weather.db.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.db.entity.SysUser;
import com.weather.db.entity.WeatherData;
import com.weather.db.service.WeatherDataService;
import com.weather.db.util.Executor;

@Controller
@RequestMapping("/weatherdata/")
public class WeatherDataEditController {
	@Resource
	private WeatherDataService weatherDataService;
	
	/**
	 * 数据订正查询
	 * @param fcdate
	 * @param city
	 * @param cityType
	 * @param fctime
	 * @param model
	 * @param bj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list(String fcdate, String city,String cityType, String fctime, Model model, boolean bj) throws Exception {
		if((fcdate == null && city == null) || (fcdate.isEmpty() && city.isEmpty())){
			
		}
		else{
			if(city != null){
				city = new String(city.getBytes("iso-8859-1"),"UTF-8");
			}
			city = city.trim();
			fctime = fctime.trim();
			List<WeatherData> datalist;
			if(bj){
				datalist = weatherDataService.getBJ(fcdate,fctime);
				model.addAttribute("cityType", "name");
				model.addAttribute("city", "北京");
				model.addAttribute("bj", bj);
			}
			else{
				datalist = weatherDataService.getAll(fcdate,cityType,city,fctime);
				model.addAttribute("cityType", cityType);
				model.addAttribute("city", city);
			}
			model.addAttribute("datalist", datalist);
			model.addAttribute("fcdate", fcdate);
			model.addAttribute("fctime", fctime);
		}
		
		//天气现象编码表
		List<Object[]> txcode = weatherDataService.getCode("dictionary.dict_weather");
		model.addAttribute("txcode", txcode);
		
		//风向编码表
		List<Object[]> fxcode = weatherDataService.getCode("base.forecast_babj_dict_wd");
		model.addAttribute("fxcode", fxcode);
		
		//风速编码表
		List<Object[]> fscode = weatherDataService.getCode("base.forecast_babj_dict_ws");
		model.addAttribute("fscode", fscode);
		
		return "list";
	}
	
	/**
	 * 错站订正查询
	 * @param fcdate
	 * @param stationid
	 * @param fctime
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("errEdit")
	public String errEdit(String fcdate, String stationid, String fctime, Model model) throws Exception {
		List<Object[]> datalist = weatherDataService.getErrStation(fcdate,stationid,fctime);
				
		model.addAttribute("stationid", stationid);
		model.addAttribute("datalist", datalist);
		model.addAttribute("fcdate", fcdate);
		model.addAttribute("fctime", fctime);
		
		//天气现象编码表
		List<Object[]> txcode = weatherDataService.getCode("dictionary.dict_weather");
		model.addAttribute("txcode", txcode);
		
		//风向编码表
		List<Object[]> fxcode = weatherDataService.getCode("base.forecast_babj_dict_wd");
		model.addAttribute("fxcode", fxcode);
		
		//风速编码表
		List<Object[]> fscode = weatherDataService.getCode("base.forecast_babj_dict_ws");
		model.addAttribute("fscode", fscode);
		
		return "errEdit";
	}
	
	/**
	 * 错站订正保存修改
	 * @param data
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("correct")
	public void correct(WeatherData data, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		try{
			weatherDataService.insertCorrect(data);
			WeatherData newData = weatherDataService.getNewStation(data);
			weatherDataService.push(newData);
			weatherDataService.updateErrStation(data);
			
			Executor.redo_f_7d();
			
			out.write("{\"result\":true}");
		}catch (Exception e) {
			out.write("{\"result\":false}");
			e.printStackTrace();
		}finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 数据订正保存修改
	 * @param data
	 * @param model
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("save")
	public void edit(WeatherData data, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		try{
			WeatherData oldData = weatherDataService.getOldData(data);
			if(oldData == null){
				oldData = new WeatherData();
				
				weatherDataService.insertData(data);
			}
			else{
				weatherDataService.updateData(data);
			}
			
			//更新其他表数据
			//weatherDataService.updateCity_Forecast(data);
			//weatherDataService.updateForecast(data);
			
			
			
			//将更新信息插入日志文件
			SysUser user = (SysUser)request.getSession().getAttribute("USER");
			weatherDataService.addLog(user.getName(), oldData, data);
			
			//单独按钮
//			Executor.redo_f_7d();
			
			out.write("{\"result\":true}");
		}catch (Exception e) {
			out.write("{\"result\":false}");
			e.printStackTrace();
		}finally {
			out.flush();
			out.close();
		}
	}
	
	@RequestMapping("order")
	public String order(Model model){
		
		//最高温前10
		List<Object[]> top10 = weatherDataService.getCityTop("desc");
		model.addAttribute("top10", top10);
		
		//最低温前10
		List<Object[]> last10 = weatherDataService.getCityTop("asc");
		model.addAttribute("last10", last10);
		
		return "order";
	}
	
	/**
	 * 7天预报数据订正推送
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("handout")
	public void redo(HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.update_f_7d();
			Executor.redo_f_7d();
		} catch (Exception e) {
			info = "{\"success\":false}";
			e.printStackTrace();
		}
		
		out.write(info);
		out.flush();
		out.close();
	}
	
	/**
	 * 7天预报数据订正推送(05前)
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("handout05")
	public void redo05(HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.redo05();
		} catch (Exception e) {
			info = "{\"success\":false}";
			e.printStackTrace();
		}
		
		out.write(info);
		out.flush();
		out.close();
	}
	
	/**
	 * 精细化自动订正
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("autoup")
	public void autoup(HttpServletResponse response,String type) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.pmsc_fine_autoup(type);
		} catch (Exception e) {
			info = "{\"success\":false}";
			e.printStackTrace();
		}
		
		out.write(info);;
		out.flush();
		out.close();
	}
}
