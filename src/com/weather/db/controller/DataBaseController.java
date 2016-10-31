package com.weather.db.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.db.entity.DataBaseInfo;
import com.weather.db.service.DataBaseService;

@Controller
@RequestMapping("/database/")
public class DataBaseController {

	@Resource
	private DataBaseService databaseService;
	
	@RequestMapping("dblist")
	public String list(Model model){
		List<DataBaseInfo> dblist = databaseService.loadAll();
		model.addAttribute("dblist", dblist);
		
		return "dblist";
	}
	
	@RequestMapping("addDatabase")
	public void add(DataBaseInfo db, HttpServletResponse response){
		
		
	}
}
