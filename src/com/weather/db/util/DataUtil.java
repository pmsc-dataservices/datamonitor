package com.weather.db.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import oracle.jdbc.driver.OraclePreparedStatement;

public class DataUtil {

	public static Connection getOtherConn(String url_n, String username_n, String pwd_n) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");    
		String url = bundle.getString(url_n);
		String username = bundle.getString(username_n);
		String pwd = bundle.getString(pwd_n);
		
        Connection conn = DriverManager.getConnection(url, username, pwd);
		return conn;
	}
	
	public static void main(String[] args) {
		try {
			Connection conn = getOtherConn("jdbc.url2","jdbc.username","jdbc.password");
			String[] tab = new String[]{"forecast_fine_3h","forecast_fine_12h","forecast_fine_24h"};
			for(String name : tab){
				String tsql = "select max(substr(to_char(utc_time,'yyyymmddhh24'),1,8))," +
						  "max(substr(to_char(utc_time,'yyyymmddhh24'),9,2))" +
						  "from forecast."+ name +
						  " where utc_time=(select max(utc_time) from forecast."+ name +
						  " where stationid='54511')";
				OraclePreparedStatement stmt = (OraclePreparedStatement)conn.prepareStatement(tsql);
				
				ResultSet rs = stmt.executeQuery();
				String date = "";
				String time = "";
				String cnt = "";
				while(rs.next()){
					date = rs.getString(1);
					time = rs.getString(2);
				}
				String sql = "select count(1) from FORECAST." + name +
						" where utc_time= to_date('" + date + time + "','yyyymmddhh24')";
				stmt = (OraclePreparedStatement)conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				while(rs.next()){
					cnt = rs.getString(1);
				}
				System.out.println(name+":date="+date+",time="+time+",cnt="+cnt);
//				map.put(name, new Object[]{date,time,cnt});
			}
			conn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
