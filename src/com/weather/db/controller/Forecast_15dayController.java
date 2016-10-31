package com.weather.db.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.db.entity.Data;
import com.weather.db.service.ForecastService;
import com.weather.db.service.WeatherDataService;
import com.weather.db.util.Executor;

@Controller
@RequestMapping("/forecast_15day/")
public class Forecast_15dayController {
	@Resource
	private ForecastService forecastService;
	
	@RequestMapping("list")
	public String list(String tabType, String city,String cityType,String vti,boolean bj, Model model) throws Exception {
		if((city != null && !city.isEmpty()) || bj){
			city = new String(city.getBytes("iso-8859-1"),"UTF-8");
			city = city.trim();
			List<Object[]> datalist = forecastService.f_15dayList(tabType, cityType, city,vti, bj);
			model.addAttribute("datalist", datalist);
			model.addAttribute("bj", bj);
			model.addAttribute("vti", vti);
			if(!bj){
				model.addAttribute("cityType", cityType);
				model.addAttribute("city", city);
			}
		}
		
		//天气现象编码表
		/*List<Object[]> txcode = weatherDataService.getCode("dictionary.dict_weather");
		model.addAttribute("txcode", txcode);
		
		//风向编码表
		List<Object[]> fxcode = weatherDataService.getCode("base.forecast_babj_dict_wd");
		model.addAttribute("fxcode", fxcode);
		
		//风速编码表
		List<Object[]> fscode = weatherDataService.getCode("base.forecast_babj_dict_ws");
		model.addAttribute("fscode", fscode);*/
		
		if("12h".equals(tabType)){
			return "f12h15d_list";
		}
		else if("24h".equals(tabType)){
			return "f24h15d_list";
		}
		else{
			return "f3h15d_list";
		}
	}
	
	@RequestMapping("save")
	public void edit(Data data,String tabType, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		try{
			if("3h".equals(tabType)){
				forecastService.save_3hor12h_15day(data,"3h");
				Executor.pmsc_fine_3h_15day();
			}
			else if("12h".equals(tabType)){
				forecastService.save_3hor12h_15day(data,"12h");
				Executor.pmsc_fine_12h_15day();
			}
			else if("24h".equals(tabType)){
				forecastService.save_24h_15day(data);
				System.out.println("5=========="+new Date());
				Executor.pmsc_fine_24h_15day();
				System.out.println("6=========="+new Date());
			}
			out.write("{\"result\":true}");
		}catch (Exception e) {
			out.write("{\"result\":false}");
			e.printStackTrace();
		}finally {
			out.flush();
			out.close();
		}
	}
	
	@RequestMapping("handout")
	public void redo(HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.handout_fine_15d();
		} catch (Exception e) {
			info = "{\"success\":false}";
			e.printStackTrace();
		}
		
		out.write(info);
		out.flush();
		out.close();
	}
}
