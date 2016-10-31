package com.weather.db.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.db.entity.Data;
import com.weather.db.service.LogService;
import com.weather.db.service.RealTimeDataService;
import com.weather.db.util.Executor;

@Controller
@RequestMapping("/realtimedata/")
public class RealTimeDataController {
	@Resource
	private RealTimeDataService realTimeDataService;
	
	@RequestMapping("monitor")
	public String realtimemonitor(Model model) throws Exception{
		List<Object[]> list = realTimeDataService.getRealTimeErrData();
		model.addAttribute("list", list);
		Map<Integer,String[]> delist = realTimeDataService.getDetailErrData();
		model.addAttribute("delist", delist);
		
		return "realtime";
	}
	
	@RequestMapping("map")
	public void map(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		String json = realTimeDataService.map();
		out.write(json);
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("list")
	public String list(String begin,String end,String cityType,String city,Model model) throws Exception{
		if(begin != null && end !=null && city != null && !begin.isEmpty() && !end.isEmpty() && !city.isEmpty()){
			city = new String(city.getBytes("iso-8859-1"),"UTF-8");
			List<Object[]> list = realTimeDataService.list(begin, end, cityType, city);
			model.addAttribute("list", list);
			model.addAttribute("begin", begin);
			model.addAttribute("end", end);
			model.addAttribute("cityType", cityType);
			model.addAttribute("city", city);
		}
		model.addAttribute("latestTime", realTimeDataService.latestTime());
		
		return "rtlist";
	}
	
	@RequestMapping("edit")
	public void edit(Data data, String name,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		try {
			realTimeDataService.eidt(data);
			out.write("{\"result\":true}");
		} catch (Exception e) {
			out.write("{\"result\":false}");
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("validate")
	public void validate(String stationid, String name,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		try {
			if(realTimeDataService.validate(stationid)){
				out.write("{\"result\":true}");
			}
			else{
				out.write("{\"result\":false,\"msg\":\"该站点不存在!\"}");
			}
		} catch (Exception e) {
			out.write("{\"result\":false,\"msg\":\"查询异常,请联系管理员!\"}");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("add")
	public void add(Data data,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		try {
			if(realTimeDataService.validate(data.getColumn1(),data.getColumn2())){
				out.write("{\"result\":false,\"msg\":\"该时刻数据已存在!\"}");
			}
			else{
				realTimeDataService.add(data);
				LogService.insertLog(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), data.getColumn2(), "人工补录");
				out.write("{\"result\":true}");
			}
		} catch (Exception e) {
			out.write("{\"result\":false,\"msg\":\"实况数据添加失败!\"}");
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("windorrain")
	public String windorrain(String cityType,String city,String time,String type,Model model) throws Exception{
		model.addAttribute("type", "OBSERVE.ELE_AWST_WIND");
		if(city != null && city !=null && time != null && !time.isEmpty() && !type.isEmpty() && !type.isEmpty()){
			city = new String(city.getBytes("iso-8859-1"),"UTF-8");
			List<Object[]> list = realTimeDataService.wOrList(cityType, city,time ,type);
			if("OBSERVE.ELE_AWST_RAIN".equals(type)){
				model.addAttribute("rainlist", list);
			}else{
				model.addAttribute("windlist", list);
			}
			
			model.addAttribute("type", type);
			model.addAttribute("time", time);
			model.addAttribute("cityType", cityType);
			model.addAttribute("city", city);
		}
		
		return "rtwindorrain";
	}
	
	@RequestMapping("editWoR")
	public void editWoR(Data data,String type,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		try {
			realTimeDataService.eidtWoR(data);
			out.write("{\"result\":true}");
		} catch (Exception e) {
			out.write("{\"result\":false,\"msg\":\"修改失败!\"}");
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("delWoR")
	public void delWoR(Data data,String type,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		try {
			realTimeDataService.delWoR(data);
			out.write("{\"result\":true}");
		} catch (Exception e) {
			out.write("{\"result\":false,\"msg\":\"删除失败!\"}");
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("handout")
	public void redo(HttpServletResponse response,String supplytime) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.realtime_add(supplytime);
		} catch (Exception e) {
			info = "{\"success\":false}";
			e.printStackTrace();
		}
		
		out.write(info);
		out.flush();
		out.close();
	}
	
	@RequestMapping("dzbd")
	public void dzbd(HttpServletRequest request,HttpServletResponse response,String supplytime) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		List<String> params = new ArrayList<String>();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
		for(int i=1;i<=5;i++){
			if(request.getParameter("time"+i) != ""){
				Date d = df.parse(request.getParameter("time"+i));
				d.setHours(d.getHours()-8);
				params.add(df.format(d) + " " +request.getParameter("stations"+i));
			}
		}
		String info = "{\"success\":true}";
		try {
			Executor.dzbd(params);
		} catch (Exception e) {
			info = "{\"success\":false}";
			e.printStackTrace();
		}
		
		out.write(info);
		out.flush();
		out.close();
	}
}
