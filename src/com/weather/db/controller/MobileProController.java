package com.weather.db.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.db.entity.Data;
import com.weather.db.service.MobileProService;

@Controller
@RequestMapping("/mobile/")
public class MobileProController {
	@Resource
	private MobileProService mobileProService;
	
	@RequestMapping("templist")
	public String templist(Model model,String city) throws Exception{
		if(city != null && !city.isEmpty()){
			city = new String(city.getBytes("iso-8859-1"),"UTF-8");
			List<Object[]> list = mobileProService.getTempData(city);
			model.addAttribute("templist", list);
			model.addAttribute("city", city);
		}
		
		return "mobiletemp";
	}
	
	@RequestMapping("rainlist")
	public String rainlist(Model model,String city) throws Exception{
		if(city != null && !city.isEmpty()){
			city = new String(city.getBytes("iso-8859-1"),"UTF-8");
			List<Object[]> list = mobileProService.getRainData(city);
			model.addAttribute("rainlist", list);
			model.addAttribute("city", city);
		}
		
		return "mobilerain";
	}
	
	@RequestMapping("edittemp")
	public void edittemp(String station, String c_bjtime,String ntemp,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		try {
			mobileProService.eidtTemp(station,c_bjtime,ntemp);
			out.write("{\"result\":true}");
		} catch (Exception e) {
			out.write("{\"result\":false}");
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("editrain")
	public void editrain(String station, String c_bjtime,String ntemp,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		try {
			mobileProService.eidtRain(station,c_bjtime,ntemp);
			out.write("{\"result\":true}");
		} catch (Exception e) {
			out.write("{\"result\":false}");
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
		
	}
}
