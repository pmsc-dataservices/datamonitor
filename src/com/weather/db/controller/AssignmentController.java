package com.weather.db.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.db.entity.Assignment;
import com.weather.db.service.AssignmentService;

@Controller
@RequestMapping("/assignment/")
public class AssignmentController {

	@Resource
	private AssignmentService assignmentService;

	@RequestMapping("list")
	public String list(Model model) {
		List<Assignment> asslist = assignmentService.loadAll();
		model.addAttribute("asslist", asslist);
		
		return "index";
	}

	@RequestMapping("add")
	public void add(Assignment task, HttpServletResponse response)
			throws IOException {

		String result = assignmentService.save(task);
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.flush();
		out.close();
	}

	@RequestMapping("edit")
	public String edit() {

		return "redirect:/list";
	}

	@RequestMapping("dele")
	public String dele() {

		return "redirect:/list";
	}

	@RequestMapping("showResult")
	public String showResult(Integer assId, Model model) {
		List<Assignment> asslist = assignmentService.loadAll();
		List<File> files = new ArrayList<File>();
		Assignment ass = null;
		if (asslist != null && asslist.size() > 0) {
			ass = asslist.get(0);
		}
		if (assId != null) {
			ass = assignmentService.get(assId);
		}
		if (ass != null) {
			File dir = new File(ass.getPath());
			File[] file = dir.listFiles();
			for (File f : file) {
				if (f.getName().indexOf(ass.getFileName()) != -1
						&& f.getName().indexOf(ass.getSuffix()) != -1) {
					files.add(f);
				}
			}
		}
		model.addAttribute("asslist", asslist);
		model.addAttribute("selectedAss", ass);
		model.addAttribute("files", files);
		
		model.addAttribute("flag", "taskresult");
		return "result";
	}

	@RequestMapping("loadFiles")
	public void loadFiles() {

	}
}
