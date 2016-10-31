package com.weather.db.controller;

import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.weather.db.util.Executor;

@Controller
@RequestMapping("/dataintegration/")
public class DataIntegrationController {
	@RequestMapping("redo")
	public void redo(String type,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String info = "{\"success\":true}";
		try {
			Executor.dataintegration(type);
		} catch (Exception e) {
			info = "{\"success\":false}";
			e.printStackTrace();
		}
		
		out.write(info);
		out.flush();
		out.close();
		
	}
	
}
