package com.weather.db.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.db.entity.SysUser;
import com.weather.db.service.UserService;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Resource
	private UserService userService;
	
	@RequestMapping("login")
	public String login(String name, String pwd, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SysUser user = userService.getUser(name, pwd);
		if (user == null) {
			model.addAttribute("failure",true);
			return "login";
		}
		request.getSession().setAttribute("USER", user);
		
		return "redirect:/weatherdata/list";
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("USER");
		return "login";
	}
	
	@RequestMapping("checkPwd")
	public void checkPwd(String oldPwd, HttpServletRequest request, HttpServletResponse response) throws IOException {
		SysUser user = (SysUser)request.getSession().getAttribute("USER");
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		if(user == null){
			out.write("{\"result\":false,\"msg\":\"用户失效，请重新登录!\"}");
		}
		else if(user.getPwd().equals(oldPwd)){
			out.write("{\"result\":true}");
		}
		else {
			out.write("{\"result\":false,\"msg\":\"原密码错误!\"}");
		}
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("changePwd")
	public void changePwd(String newPwd, HttpServletRequest request, HttpServletResponse response) throws IOException {
		SysUser user = (SysUser)request.getSession().getAttribute("USER");
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		if(user != null){
			user.setPwd(newPwd);
			userService.saveOrUpdate(user);
			request.getSession().setAttribute("USER", user);
			out.write("{\"result\":true}");
		}
		else {
			out.write("{\"result\":false,\"msg\":\"用户失效，请重新登录!\"}");
		}
		
		out.flush();
		out.close();
	}
}
