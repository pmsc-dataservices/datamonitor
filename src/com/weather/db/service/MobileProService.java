package com.weather.db.service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import base.service.DefaultEntityManager;

import com.weather.db.entity.Data;
import com.weather.db.entity.WeatherData;
import com.weather.db.util.DataUtil;

@Service
public class MobileProService extends DefaultEntityManager<WeatherData>{

	public List<Object[]> getTempData(String city) throws Exception{
		Connection conn = DataUtil.getOtherConn("realtime.url","realtime.username","realtime.password");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		sql = "select v01000,namecn,c_bjtime,product_name,temp from observe.mobile_temp_top where namecn like '%"+ city +"%'";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<Object[]> list = new ArrayList<Object[]>();
		while(rs.next()){
			Object[] objs = new Object[5];
			for(int i=0;i<5;i++){
				objs[i] = rs.getString(i+1);
			}
			list.add(objs);
		}
		
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
		return list;
	}
	
	public List<Object[]> getRainData(String city) throws Exception{
		Connection conn = DataUtil.getOtherConn("realtime.url","realtime.username","realtime.password");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		sql = "select v01000,districtcn,c_bjtime,product_name,top_id,acc_rain from observe.mobile_rain_top where districtcn like '%"+ city +"%'";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<Object[]> list = new ArrayList<Object[]>();
		while(rs.next()){
			Object[] objs = new Object[6];
			for(int i=0;i<6;i++){
				objs[i] = rs.getString(i+1);
			}
			list.add(objs);
		}
		
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
		return list;
	}
	
	public void eidtTemp(String station,String c_bjtime,String ntemp) throws Exception{
		Connection conn = DataUtil.getOtherConn("realtime.url","realtime.username","realtime.password");
		
		StringBuffer sql = new StringBuffer("update observe.mobile_temp_top set ");  
		sql.append("temp=?");
		sql.append(" where V01000=?");
		sql.append(" and C_BJTIME=?");
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		pstmt.setString(1,ntemp);
		pstmt.setString(2,station);
		pstmt.setString(3,c_bjtime);
		
		pstmt.executeUpdate();
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
	}
	
	public void eidtRain(String station,String c_bjtime,String ntemp) throws Exception{
		Connection conn = DataUtil.getOtherConn("realtime.url","realtime.username","realtime.password");
		
		StringBuffer sql = new StringBuffer("update observe.mobile_rain_top set ");  
		sql.append("acc_rain=?");
		sql.append(" where V01000=?");
		sql.append(" and C_BJTIME=?");
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		pstmt.setString(1,ntemp);
		pstmt.setString(2,station);
		pstmt.setString(3,c_bjtime);
		
		pstmt.executeUpdate();
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
	}
}
