package com.weather.db.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import base.service.DefaultEntityManager;

import com.weather.db.entity.Data;
import com.weather.db.util.DataUtil;

@Service
public class FileMonitorService  extends DefaultEntityManager<Data>{

	public List<Object[]> list(String servicename) throws Exception{
		String sql = "select distinct(filename),directory,filesize,to_char(filereceivetime,'yyyymmddhh24miss') " +
				"from monitor.monitor_files " +
				"where business_name='"+ servicename + "' " +
				"and filereceivetime>sysdate-3/24 order by to_char(filereceivetime,'yyyymmddhh24miss') desc";
		
		return entityDao.createSQLQuery(sql, null).list();
	}
	
	public Object[] getBusinessName(String type){
		
		String sql = "select distinct(BUSINESS_NAME) from MONITOR.MONITOR_FILES t where BUSINESS_NAME like '" + type +"%' order by BUSINESS_NAME desc";
		return entityDao.createSQLQuery(sql, null).list().toArray();
	}
	
	public Object[] getFilePath(String servicename){
		
		String sql = "select distinct(directory) " +
				"from monitor.monitor_files " +
				"where business_name='"+ servicename + "' " +
				"and filereceivetime>sysdate-3/24";
		return entityDao.createSQLQuery(sql, null).list().toArray();
	}
	
	public Object[] getRadarProv() throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		String sql = "select distinct(provcn) from DICTIONARY.DICT_RADAR t";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<String> list = new ArrayList<String>();
		while(rs.next()){
			list.add(rs.getString(1));
		}
		
		return list.toArray();
	}
	
	public List<Object[]> getRadarByProv(String provname) throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		
		String sql = "select RADAR_NAME,STATIONID from DICTIONARY.DICT_RADAR t where t.provcn='"+provname+"'";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<Object[]> list = new ArrayList<Object[]>();
		while(rs.next()){
			Object[] objs = new Object[2];
			objs[0] = rs.getString(1);
			objs[1] = rs.getString(2);
			list.add(objs);
		}
		
		return list;
	}
	
	public List<Object[]> getRadarInfo(String stationid) throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		
		String sql = "select substr(product_time,11,2)," +
				"to_char(to_date(product_time,'yyyymmddhh24miss')+8/24,'yyyymmddhh24miss')," +
				"C_ITIME" +
				" from observe.monitor_data_info " +
				"where " +
				"substr(product_time,1,10)=" +
					"(select max(substr(product_time,1,10)) from observe.monitor_data_info " +
						"where substr(filename,10,5)='" + stationid + "') " +
				"and substr(filename,10,5)='" + stationid + "' " +
				"and target_ip='10.30.16.240' " +
				"order by product_time asc";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<Object[]> list = new ArrayList<Object[]>();
		while(rs.next()){
			Object[] objs = new Object[3];
			objs[0] = rs.getString(1);
			objs[1] = rs.getString(2);
			objs[2] = rs.getString(3);
			list.add(objs);
		}
		
		return list;
	}
	
	public List<Object[]> delaycount() throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		
		String sql = "select round(to_number(sysdate-max(to_date(product_time,'yyyymmddhh24miss')+8/24))*1440)-6," +
				"radar_name,provcn,stationid " +
				"from observe.monitor_data_info,DICTIONARY.DICT_RADAR " +
				"where substr(filename,10,5)=stationid " +
				"and target_ip='10.30.16.240' " +
				"group by provcn,radar_name,stationid " +
				"order by provcn";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<Object[]> list = new ArrayList<Object[]>();
		while(rs.next()){
			Object[] objs = new Object[4];
			objs[0] = rs.getString(1);
			objs[1] = rs.getString(2);
			objs[2] = rs.getString(3);
			objs[3] = rs.getString(4);
			list.add(objs);
		}
		
		return list;
	}
	
	public List<Object[]> avgdelay() throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		
		String sql = "select round(avg(delay),2),radar_name,provcn,stationid from ("+
						"select round(to_number((to_date(t.c_itime,'yyyymmddhh24miss'))-(to_date(t.product_time,'yyyymmddhh24miss')+8/24))*1440) delay,"+
						       "d.radar_name,d.provcn,d.stationid from OBSERVE.MONITOR_DATA_INFO t,DICTIONARY.DICT_RADAR d,"+
						       "(select max(substr(product_time,1,10)) ptime,substr(filename,10,5) rid "+
						               "from observe.monitor_data_info group by substr(filename,10,5)) t2 "+ 
						       "where substr(product_time,1,10)= t2.ptime "+
						       "and substr(t.filename,10,5) = t2.rid "+
						       "and substr(t.filename,10,5)=d.stationid "+
						       "and target_ip='10.30.16.240') "+
					"group by radar_name,provcn,stationid "+
					"order by provcn";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<Object[]> list = new ArrayList<Object[]>();
		while(rs.next()){
			Object[] objs = new Object[4];
			objs[0] = rs.getString(1);
			objs[1] = rs.getString(2);
			objs[2] = rs.getString(3);
			objs[3] = rs.getString(4);
			list.add(objs);
		}
		
		return list;
	}
}
