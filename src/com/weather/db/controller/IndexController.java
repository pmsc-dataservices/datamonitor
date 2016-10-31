package com.weather.db.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.db.service.IndexService;
import com.weather.db.util.Executor;
/**
 * 
 * 指数
 *
 */
@Controller
@RequestMapping("/index/")
public class IndexController {
	
	@Resource
	private IndexService indexService;
	
	@RequestMapping("3d")
	public String realtimemonitor(Model model) throws Exception{
		List<Object[]> list = indexService.list3d();
		model.addAttribute("index3dlist", list);
		return "index3d";
	}
	
	@RequestMapping("redo")
	public void map(String type,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.redo_index3d(type);
		} catch (Exception e) {
			info = "{\"success\":false}";
		}
		
		out.write(info);
		out.flush();
		out.close();
	}
	
}
