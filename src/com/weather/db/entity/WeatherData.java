package com.weather.db.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherData {

	private String stationID;
	
	private String name;
	
	private String fcdate;
	
	private String fctime;
	
	private String fcyear;
	
	private String fcmon;
	
	private String fcday;
	
	private String fchh;
	
	private String vti;
	
	private String weather1;
	
	private String weather2;
	
	private String wd1;
	
	private String wd2;
	
	private String ws1;
	
	private String ws2;
	
	private String weather1_code;
	
	private String weather2_code;
	
	private String wd1_code;
	
	private String wd2_code;
	
	private String ws1_code;
	
	private String ws2_code;
	
	private String temp1;
	
	private String temp2;
	
	private String dataSource;
	
	//自定义的发布日期
	private String releasedate;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

	public String getStationID() {
		return stationID;
	}

	public void setStationID(String stationID) {
		this.stationID = stationID;
	}

	public String getFcdate() {
		return fcdate;
	}

	public void setFcdate(String fcdate) {
		this.fcdate = fcdate;
	}

	public String getVti() {
		return vti;
	}

	public void setVti(String vti) {
		this.vti = vti;
	}

	public String getWeather1() {
		return weather1;
	}

	public void setWeather1(String weather1) {
		this.weather1 = weather1;
	}

	public String getWeather2() {
		return weather2;
	}

	public void setWeather2(String weather2) {
		this.weather2 = weather2;
	}

	public String getWd1() {
		return wd1;
	}

	public void setWd1(String wd1) {
		this.wd1 = wd1;
	}

	public String getWd2() {
		return wd2;
	}

	public void setWd2(String wd2) {
		this.wd2 = wd2;
	}

	public String getWs1() {
		return ws1;
	}

	public void setWs1(String ws1) {
		this.ws1 = ws1;
	}

	public String getWs2() {
		return ws2;
	}

	public void setWs2(String ws2) {
		this.ws2 = ws2;
	}

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFctime() {
		return fctime;
	}

	public void setFctime(String fctime) {
		this.fctime = fctime;
	}

	public String getFchh() {
		return fchh;
	}

	public void setFchh(String fchh) {
		this.fchh = fchh;
	}

	public String getWeather1_code() {
		return weather1_code;
	}

	public void setWeather1_code(String weather1_code) {
		this.weather1_code = weather1_code;
	}

	public String getWeather2_code() {
		return weather2_code;
	}

	public void setWeather2_code(String weather2_code) {
		this.weather2_code = weather2_code;
	}

	public String getWd1_code() {
		return wd1_code;
	}

	public void setWd1_code(String wd1_code) {
		this.wd1_code = wd1_code;
	}

	public String getWd2_code() {
		return wd2_code;
	}

	public void setWd2_code(String wd2_code) {
		this.wd2_code = wd2_code;
	}

	public String getWs1_code() {
		return ws1_code;
	}

	public void setWs1_code(String ws1_code) {
		this.ws1_code = ws1_code;
	}

	public String getWs2_code() {
		return ws2_code;
	}

	public void setWs2_code(String ws2_code) {
		this.ws2_code = ws2_code;
	}

	public String getFcyear() {
		return fcyear;
	}

	public void setFcyear(String fcyear) {
		this.fcyear = fcyear;
	}

	public String getFcmon() {
		return fcmon;
	}

	public void setFcmon(String fcmon) {
		this.fcmon = fcmon;
	}

	public String getFcday() {
		return fcday;
	}

	public void setFcday(String fcday) {
		this.fcday = fcday;
	}
	
	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getReleasedate() {
		
		try {
			Date fd = df.parse(fcdate);
			
			int index = Integer.parseInt(vti)/24 - 1;
			
			fd.setDate(fd.getDate() + index);
			
			releasedate = df.format(fd);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return releasedate;
	}

	public void setReleasedate(String releasedate) {
		this.releasedate = releasedate;
	}
	
}
