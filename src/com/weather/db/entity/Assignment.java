package com.weather.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

//@Entity
public class Assignment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 服务名称
	 */
	private String name;
	
	/**
	 * 查询语句，用通配符表示可变参数
	 */
	//@Column(name = "sql", length = 2000)
	private String sql;
	
	/**
	 * 连接数据库类型
	 */
	private String type;
	
	/**
	 * 数据库IP
	 */
	private String ip;
	
	/**
	 * 数据库端口
	 */
	private String port;
	
	/**
	 * 数据库名
	 */
	private String database;
	
	/**
	 * 数据库用户名
	 */
	private String username;
	
	/**
	 * 数据库密码
	 */
	private String pwd;
	
	
	/**
	 * 执行方式[once一次，period周期]
	 */
	private String executivemode;
	
	/**
	 * 执行时间[immediately立即]
	 */
	private String time;
	
	/**
	 * 执行状态[unexectued未执行，executing执行中，success已执行，failure执行失败]
	 */
	private String status;
	
	/**
	 * 执行周期(时)
	 */
	private String period;
	
	/**
	 * 查询参数
	 */
//	private Object[] parameters = null;
	
	/**
	 * 文件保存路径(只支持txt文件)
	 */
	private String path;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 文件名后缀
	 */
	private String suffix;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getExecutivemode() {
		return executivemode;
	}

	public void setExecutivemode(String executivemode) {
		this.executivemode = executivemode;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

//	public Object[] getParameters() {
//		return parameters;
//	}
//
//	public void setParameters(Object[] parameters) {
//		this.parameters = parameters;
//	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
}
