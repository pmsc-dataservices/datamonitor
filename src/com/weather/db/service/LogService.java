package com.weather.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.weather.db.util.DataUtil;

public class LogService {

	public static void insertLog(String intime,String bjtime) throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		String sql = "insert into OBSERVE.ELE_AWST_MONITOR_JOB values(?,?,'全站自动'," +
				"(select count(*) from OBSERVE.ELE_AWST_NATION where c_bjtime=?),0)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, intime);
		pstmt.setString(2, bjtime+"0000");
		pstmt.setString(3, bjtime+"0000");
		pstmt.executeUpdate();
		
		conn.close();
	}
	
	public static void updateLog(String intime,String bjtime) throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		String sql = "update OBSERVE.ELE_AWST_MONITOR_JOB " +
				"set FILL_POINT=(select count(*) from OBSERVE.ELE_AWST_NATION where c_bjtime=?)" +
				" where C_ITIME=? and C_BJTIME=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, bjtime+"0000");
		pstmt.setString(2, intime);
		pstmt.setString(3, bjtime+"0000");
		pstmt.executeUpdate();
		
		conn.close();
	}
	
	public static void insertLog(String intime,String bjtime,String type) throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		String sql = "insert into OBSERVE.ELE_AWST_MONITOR_JOB(c_itime,c_bjtime,job_type) values(?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, intime);
		pstmt.setString(2, bjtime);
		pstmt.setString(3, type);
		pstmt.executeUpdate();
		
		conn.close();
	}
}
