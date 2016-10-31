package com.weather.db.service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OraclePreparedStatement;

import org.springframework.stereotype.Service;

import base.service.DefaultEntityManager;

import com.google.gson.Gson;
import com.weather.db.entity.Assignment;
import com.weather.db.entity.WeatherData;
import com.weather.db.quartz.DBJob;
import com.weather.db.quartz.DBScheduler;

@Service
public class AssignmentService extends DefaultEntityManager<Assignment> {

	Connection conn = null;
	
	public String save(Assignment task) {
		Gson json = new Gson();
		Map<String, Object> msg = new HashMap<String, Object>();
		Assignment exist = entityDao.findUniqueByProperty("name", task.getName());
		if(exist != null){
			msg.put("result", false);
			msg.put("msg", "该任务名已存在!");
			return json.toJson(msg);
		}
		boolean conndata = conncect(task.getType(), task.getIp(),
				task.getPort(), task.getDatabase(), task.getUsername(),
				task.getPwd());
		
		//连接数据库
		if(!conndata){
			msg.put("result", false);
			msg.put("msg", "连接数据库失败!");
			return json.toJson(msg);
		}
		
		//判断SQL语句是否可执行
		if(!checkSql(task.getSql())){
			msg.put("result", false);
			msg.put("msg", "SQL语句执行失败!");
			return json.toJson(msg);
		}
		
		//判断保存文件路径是否有效
		if(!checkPath(task.getPath())){
			msg.put("result", false);
			msg.put("msg", "文件保存路径不是有效路径!");
			return json.toJson(msg);
		}
		
		//保存入数据库
		entityDao.saveOrUpdate(task);
		
		//执行任务
		excute(task);
		msg.put("result", true);
		return json.toJson(msg);
	}

	private boolean conncect(String dataType, String ip, String port,
			String database, String username, String pwd) {
		String url = "";

		if ("oracle".equals(dataType)) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + database;
		}
		try {
			conn = DriverManager.getConnection(url, username, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 

		return true;
	}

	private boolean checkSql(String sql) {
		try {
			OraclePreparedStatement stmt = (OraclePreparedStatement) conn.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	private boolean checkPath(String path) {
		File fpath = new File(path);
		if (fpath.isDirectory() && !fpath.exists()) {
			return fpath.mkdirs();
		}
		return true;
	}
	
	private void excute(Assignment task){
		if("immediately".equals(task.getTime()) && "once".equals(task.getExecutivemode())){
			entityDao.saveOrUpdate(DBJob.doJob(task));
		}
		else {
			DBScheduler.createTask(task);
		}
	}
	
	public Assignment get(Integer id){
		Assignment ass = entityDao.findUniqueByProperty("id", id);
		return ass;
	}
	
}
