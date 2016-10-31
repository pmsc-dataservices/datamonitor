package com.weather.db.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.db.service.ForecastService;
import com.weather.db.service.RealTimeDataService;
import com.weather.db.util.Executor;
/**
 * 
 * 业务管理
 *
 */
@Controller
@RequestMapping("/jobmanage/")
public class JobController {
	@Resource
	private RealTimeDataService realTimeDataService;
	
	@Resource
	private ForecastService forecastService;
	
	@RequestMapping("list")
	public String realtimemonitor(Model model) throws Exception{
		List<Object[]> list = realTimeDataService.getJobsInfo();
		model.addAttribute("list", list);
		
		return "joblist";
	}
	
	@RequestMapping("detail")
	public void map(String jobName,HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		String json = realTimeDataService.detail(jobName);
		out.write(json);
		
		out.flush();
		out.close();
	}
	
	/**
	 * 预报业务监控
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("forecast")
	public String forecast(Model model) throws Exception{
		List<Object[]> intranetList = forecastService.forecastService("intranet");
		List<Object[]> dmzList = forecastService.forecastService("dmz");
		List<Object[]> outernetList = forecastService.forecastService("outernet");
		model.addAttribute("intranetList", intranetList);
		model.addAttribute("dmzList", dmzList);
		model.addAttribute("outernetList", outernetList);
		
		return "forecastservice";
	}
	
	/**
	 * DMZ错站
	 * @param fctime
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("dmzerr")
	public void dmzerr(String fctime,HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		String cnt = forecastService.dmzerr(fctime);
		out.write(cnt);
		
		out.flush();
		out.close();
	}
	
	
	/**
	 * 预报业务监控补转
	 * @param type
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("redofs")
	public void redofs(String fname,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.redofs(fname);
		} catch (Exception e) {
			info = "{\"success\":false}";
		}
		
		out.write(info);
		out.flush();
		out.close();
	}
}
