package com.weather.db.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.db.service.WeatherDataService;
import com.weather.db.util.Executor;


@Controller
@RequestMapping("/product/")
public class ProductController {
	@Resource
	private WeatherDataService weatherDataService;

	@RequestMapping("compare")
	public String comparepro(Model model){
		
		Map<String,Object[]> map= weatherDataService.getProductInfo();
		model.addAttribute("dmz3h", map.get("dmz3h"));
		model.addAttribute("dmz7d", map.get("dmz7d"));
		
		return "product";
	}
	
	@RequestMapping("forecast")
	public String forecast(Model model){
		
		/*Map<String,Object[]> map= weatherDataService.getRefinePro();
		Object[] f3h = map.get("forecast_fine_3h");
		Object[] f12h = map.get("forecast_fine_12h");
		Object[] f24h = map.get("forecast_fine_24h");
		model.addAttribute("f3h", f3h);
		model.addAttribute("f12h", f12h);
		model.addAttribute("f24h", f24h);*/
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();
		String time = fmt.format(date);
		String year = time.substring(0,8);
//		int hour = date.getHours();
//		int differDay = Integer.parseInt(year) - Integer.parseInt(f3h[0].toString());
		
		/*model.addAttribute("normal", false);
		if(f3h[0].equals(f12h[0]) && f12h[0].equals(f24h[0])){
			if("00".equals(f3h[1]) && differDay <= 0 && hour < 19){
				model.addAttribute("normal", true);
			}
			else if("12".equals(f3h[1]) && (differDay == 0 || (hour <8 && differDay == 1))){
				model.addAttribute("normal", true);
			}
		}*/
		
		List<String[]> f_15d = weatherDataService.getForecast_15d();
		model.addAttribute("normal_15d", false);
		if(f_15d.get(0)[1].equals(f_15d.get(1)[1]) && f_15d.get(0)[2].equals(f_15d.get(1)[2])){
			String hstr = time.substring(8);
			int differDay = Integer.parseInt(year) - Integer.parseInt(f_15d.get(0)[1]);
			if("00".equals(f_15d.get(0)[2]) && ((differDay == 0 && "1845".compareTo(hstr) > 0) || differDay < 0)){
				model.addAttribute("normal_15d", true);
			}
			else if("12".equals(f_15d.get(0)[2]) && (differDay == 0 || ("0745".compareTo(hstr) > 0 && differDay == 1))){
				model.addAttribute("normal_15d", true);
			}
		}
		model.addAttribute("f_15d", f_15d);
		
		return "forecast";
	}
	
	@RequestMapping("executive")
	public void executive(HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.redo_jingxihua();
		} catch (Exception e) {
			info = "{\"success\":false}";
		}
		
		out.write(info);
		out.flush();
		out.close();
	}
	
	@RequestMapping("worldcup")
	public String worldcup(Model model){
		String[] log = weatherDataService.getWorldCupLog();
		model.addAttribute("log", log);
		return "worldcup";
	}
	
	@RequestMapping("redo_worldcup")
	public void redo_worldcup(HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.redo_worldcup();
		} catch (Exception e) {
			info = "{\"success\":false}";
		}
		
		out.write(info);
		out.flush();
		out.close();
	}
	
	@RequestMapping("redo_f_15d")
	public void redo_f_15d(String type,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.redo_f_15d(type);
		} catch (Exception e) {
			info = "{\"success\":false}";
		}
		
		out.write(info);
		out.flush();
		out.close();
	}
	
	@RequestMapping("redo_7d")
	public void redo_7d(String type,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.redo_7d(type);
		} catch (Exception e) {
			info = "{\"success\":false}";
		}
		
		out.write(info);
		out.flush();
		out.close();
	}
}
