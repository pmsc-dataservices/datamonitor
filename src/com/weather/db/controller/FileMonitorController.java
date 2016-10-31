package com.weather.db.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.db.service.FileMonitorService;

@Controller
@RequestMapping("/filemonitor/")
public class FileMonitorController {
	@Resource
	private FileMonitorService fileMonitorService;

	@RequestMapping("list")
	public String list(Model model) {
		Object[] sourceServiceName = fileMonitorService.getBusinessName("source");
		Object[] targetServiceName = fileMonitorService.getBusinessName("target");
		
		model.addAttribute("sourceServiceName",sourceServiceName);
		model.addAttribute("targetServiceName",targetServiceName);
		return "file";
	}
	
	@RequestMapping("loadpath")
	public void loadpath(String serviceName,HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		Object[] path = fileMonitorService.getFilePath(serviceName);
		
		StringBuffer json = new StringBuffer();
		json.append("{[");
		
		for(int i=0;i<path.length;i++){
			json.append("\""+path[i]+"\",");
		}
		if(path.length > 0){
			json.setLength(json.length()-1);
		}
		
		json.append("]}");
		
		out.write(json.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("loadfiles")
	public void loadfiles(String serviceName,HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		List<Object[]> files = fileMonitorService.list(serviceName);
		
		String json = list2json(files);
		out.write(json);
		
		out.flush();
		out.close();
	}
	
	String list2json(List<Object[]> list){
		StringBuffer json = new StringBuffer();
		json.append("{[");
		for(Object[] o: list){
			json.append("{");
			
			for(int i=0;i<o.length;i++){
				json.append("\"v"+i+"\":\"" + o[i] + "\",");
			}
			
//			json.append("\"name\":\"" + o[1]+"/"+o[0] + "\",");
//			json.append("\"size\":\"" + o[2] + "\",");
//			json.append("\"time\":\"" + o[3] + "\"");
			json.append("},");
		}
		if(list.size() != 0){
			json.setLength(json.length()-1);
		}
		
		json.append("]}");
		
		return json.toString();
	}
	
	@RequestMapping("radar")
	public String radar(Model model) throws Exception{
		Object[] provs = fileMonitorService.getRadarProv();
		model.addAttribute("provs",provs);
		
		return "radar";
	}
	
	@RequestMapping("loadradar")
	public void loadradar(String provname,HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		List<Object[]> radars = fileMonitorService.getRadarByProv(provname);
		
		String json = list2json(radars);
		out.write(json);
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("getRadarInfo")
	public void getRadarInfo(String stationid,HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		List<Object[]> radarsInfo = fileMonitorService.getRadarInfo(stationid);
		
		String json = list2json(radarsInfo);
		out.write(json);
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("delaycount")
	public void delaycount(HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		List<Object[]> delay = fileMonitorService.delaycount();
		
		String json = list2json(delay);
		out.write(json);
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("avgdelay")
	public void avgdelay(HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		List<Object[]> avg = fileMonitorService.avgdelay();
		
		String json = list2json(avg);
		out.write(json);
		
		out.flush();
		out.close();
	}
}
