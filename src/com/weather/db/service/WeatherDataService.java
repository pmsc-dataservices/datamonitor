package com.weather.db.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OraclePreparedStatement;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import base.service.DefaultEntityManager;

import com.weather.db.entity.WeatherData;
import com.weather.db.util.DataUtil;

@Service
public class WeatherDataService extends DefaultEntityManager<WeatherData>{
	
	Logger logger=Logger.getLogger(WeatherDataService.class);

	public List<WeatherData> getAll(String date, String cityType, String city, String fctime){
		StringBuffer sql = new StringBuffer("select * from web_weather_forecast t where 1=1 ");  
		
		if(date != null && !date.isEmpty()){
			sql.append("and t.fcdate='" + date + "'");
		}
		if(city != null && !city.isEmpty()){
			if("name".equals(cityType)){
				sql.append("and t." + cityType + " like '%" + city + "%'");
			}
			else {
				sql.append(" and t." + cityType + " = '" + city + "'");
			}
		}
		if(fctime != null && !fctime.isEmpty()){
			sql.append(" and t.fctime=" + fctime);
		}
  
        return entityDao.list(sql.toString(), new Object[] {}, -1, 0, WeatherData.class, setField());  
	}
	
	public List<WeatherData> getBJ(String date, String fctime){
		StringBuffer sql = new StringBuffer("select t.* from web_weather_forecast t join base.forecast_bj_dict_station d on t.stationid=d.stationid ");  
		
		if(date != null && !date.isEmpty()){
			sql.append("and t.fcdate='" + date + "' ");
		}
		if(fctime != null && !fctime.isEmpty()){
			sql.append("and t.fctime=" + fctime);
		}
  
        return entityDao.list(sql.toString(), new Object[] {}, -1, 0, WeatherData.class, setField());  
	}
	
	public WeatherData getOldData(WeatherData data){
		StringBuffer sql = new StringBuffer("select * from web_weather_forecast t");  
		sql.append(" where name='" + data.getName() + "'");
		sql.append(" and stationid='" + data.getStationID() + "'");
		sql.append(" and fcdate='" + data.getFcdate() + "'");
		sql.append(" and fctime='" + data.getFctime() + "'");
		sql.append(" and vti='" + data.getVti() + "'");
		
		List<WeatherData> list = entityDao.list(sql.toString(), new Object[] {}, -1, 0, WeatherData.class, setField());
        if(list != null && list.size() > 0){
        	return list.get(0);
        }
		return null;
	}
	
	public void updateData(WeatherData data){
		StringBuffer sql = new StringBuffer("update web_weather_forecast ");
		
		sql = updateSql(sql,data);
		sql.append(" where stationid='" + data.getStationID() + "'");
		sql.append(" and fcdate='" + data.getFcdate() + "'");
		sql.append(" and fctime='" + data.getFctime() + "'");
		sql.append(" and vti='" + data.getVti() + "'");
		
		logger.info(sql.toString());
		
		entityDao.executeSql(sql.toString());
	}
	
	public void insertData(WeatherData data){
		StringBuffer sql = new StringBuffer("insert into web_weather_forecast values(");
		
		sql.append("'" + data.getStationID() + "',");
		sql.append("'" + data.getName() + "',");
		sql.append("'" + data.getFcdate() + "',");
		sql.append("'" + data.getFctime() + "',");
		sql.append("'" + data.getFcdate().substring(0,4) + "',");
		sql.append("'" + data.getFcdate().substring(4,6) + "',");
		sql.append("'" + data.getFcdate().substring(6,8) + "',");
		sql.append("'" + data.getFctime() + "',");
		sql.append("'" + data.getVti() + "',");
		sql.append("'" + data.getWeather1_code() + "',");
		sql.append("'" + data.getWeather1() + "',");
		sql.append("'" + data.getWeather2_code() + "',");
		sql.append("'" + data.getWeather2() + "',");
		sql.append("'" + data.getWd1_code() + "',");
		sql.append("'" + data.getWd1() + "',");
		sql.append("'" + data.getWd2_code() + "',");
		sql.append("'" + data.getWd2() + "',");
		sql.append("'" + data.getWs1_code() + "',");
		sql.append("'" + data.getWs1() + "',");
		sql.append("'" + data.getWs2_code() + "',");
		sql.append("'" + data.getWs2() + "',");
		sql.append("'" + data.getTemp1() + "',");
		sql.append("'" + data.getTemp2() + "',");
		sql.append("'MAN')");
		
		logger.info(sql.toString());
		
		entityDao.executeSql(sql.toString());
	}
	
	List<String> setField(){
		List<String> fieldList = new ArrayList<String>();
		fieldList.add("stationID");
        fieldList.add("name");  
        fieldList.add("fcdate");  
        fieldList.add("fctime");
        fieldList.add("fcyear");
        fieldList.add("fcmon");
        fieldList.add("fcday");
        fieldList.add("vti");  
        fieldList.add("weather1"); 
        fieldList.add("weather2");
        fieldList.add("wd1");
        fieldList.add("wd2");
        fieldList.add("ws1");
        fieldList.add("ws2");
        fieldList.add("weather1_code"); 
        fieldList.add("weather2_code");
        fieldList.add("wd1_code");
        fieldList.add("wd2_code");
        fieldList.add("ws1_code");
        fieldList.add("ws2_code");
        fieldList.add("temp1");
        fieldList.add("temp2");
		return fieldList;
	}
	
	public void addLog(String userName, WeatherData oldData, WeatherData newData){
		StringBuffer sql = new StringBuffer("insert into FORECAST_UPDATE values(");
		sql.append("'" + userName + "',");
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		sql.append("to_date('" + dateFormat.format(date) + "','yyyymmddhh24miss'),");
		sql.append("'" + oldData.getStationID() + "',");
		sql.append("to_date('" + newData.getFcdate() + newData.getFctime() + "','yyyymmddhh24miss'),");
		sql.append("'" + oldData.getFcyear() + "',");
		sql.append("'" + oldData.getFcmon() + "',");
		sql.append("'" + oldData.getFcday() + "',");
		sql.append("'" + oldData.getFctime() + "',");
		sql.append("" + oldData.getVti() + ",");
		sql.append("'" + oldData.getWeather1_code() + "',");
		sql.append("'" + oldData.getWeather2_code() + "',");
		sql.append("'" + oldData.getWd1_code() + "',");
		sql.append("'" + oldData.getWd2_code() + "',");
		sql.append("'" + oldData.getWs1_code() + "',");
		sql.append("'" + oldData.getWs2_code() + "',");
		sql.append("'" + oldData.getTemp1() + "',");
		sql.append("'" + oldData.getTemp2() + "',");
		sql.append("'" + newData.getWeather1_code() + "',");
		sql.append("'" + newData.getWeather2_code() + "',");
		sql.append("'" + newData.getWd1_code() + "',");
		sql.append("'" + newData.getWd2_code() + "',");
		sql.append("'" + newData.getWs1_code() + "',");
		sql.append("'" + newData.getWs2_code() + "',");
		sql.append("'" + newData.getTemp1() + "',");
		sql.append("'" + newData.getTemp2() + "')");
		
		entityDao.executeSql(sql.toString());
	}
	
	public void updateCity_Forecast(WeatherData data) throws Exception{
		
		StringBuffer sql = new StringBuffer("update forecast.city_forecast@racdb "); 
		
		sql = updateSql(sql,data);
		sql.append(" where stationid='" + data.getStationID() + "'");
		sql.append(" and fcdate=to_date('" + data.getFcdate() + data.getFctime() + "','yyyymmddhh24miss')");
		sql.append(" and vti=" + data.getVti());
		
		logger.info(sql.toString());
		
		entityDao.executeSql(sql.toString());
	}
	
	public StringBuffer updateForecast(WeatherData data){
		
		StringBuffer sql = new StringBuffer("update forecast.forecast@racdb ");  
		
		sql.append("set weather1='" + data.getWeather1_code() + "',");
		sql.append("weather2='" + data.getWeather2_code() + "',");
		sql.append("wd1='" + data.getWd1_code() + "',");
		sql.append("wd2='" + data.getWd2_code() + "',");
		sql.append("ws1='" + data.getWs1_code() + "',");
		sql.append("ws2='" + data.getWs2_code() + "',");
		sql.append("temp1='" + data.getTemp1() + "',");
		sql.append("temp2='" + data.getTemp2() + "'");
		sql.append(" where stationid='" + data.getStationID() + "'");
		sql.append(" and fcdate=to_date('" + data.getFcdate() + data.getFctime() + "','yyyymmddhh24miss')");
		sql.append(" and vti=" + data.getVti());
		
		logger.info(sql.toString());
		
		entityDao.executeSql(sql.toString());
		return sql;
	}
	
	StringBuffer updateSql(StringBuffer sql, WeatherData data){
		sql.append("set weather1='" + data.getWeather1() + "',");
		sql.append("weather1_code='" + data.getWeather1_code() + "',");
		sql.append("weather2='" + data.getWeather2() + "',");
		sql.append("weather2_code='" + data.getWeather2_code() + "',");
		sql.append("wd1='" + data.getWd1() + "',");
		sql.append("wd1_code='" + data.getWd1_code() + "',");
		sql.append("wd2='" + data.getWd2()+ "',");
		sql.append("wd2_code='" + data.getWd2_code() + "',");
		sql.append("ws1='" + data.getWs1() + "',");
		sql.append("ws1_code='" + data.getWs1_code() + "',");
		sql.append("ws2='" + data.getWs2() + "',");
		sql.append("ws2_code='" + data.getWs2_code() + "',");
		sql.append("temp1='" + data.getTemp1() + "',");
		sql.append("temp2='" + data.getTemp2() + "',");
		sql.append("DATA_SOURCE='MAN'");
		
		return sql;
	}
	
	public List<Object[]> getCode(String tab){
		StringBuffer sql = new StringBuffer("select name,code from ");
		sql.append(tab);
		sql.append(" order by code asc");
		return entityDao.createSQLQuery(sql.toString(), null).list();
	}
	
	public List<Object[]> getCityTop(String sort){
		//查询出时次和时效
		String fc = "select max(fctime),max(fcdate) as fctime from service.web_weather_forecast " +
				"where fcdate=" +
				"(" +
				"select max(fcdate) from service.web_weather_forecast where stationid='54511'" +
				")";
		Object[] o = (Object[])entityDao.createSQLQuery(fc.toString(), null).list().get(0);
		String fctime = o[0].toString();
		String fcdate = o[1].toString();
		String temp = "temp1";
		if(sort.equals("asc")){
			temp = "temp2";
		}
		if(fctime.equals("20")){
			if(sort.equals("asc")){
				temp = "temp1";
			}
			else{
				temp = "temp2";
			}
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select distinct(stationid),name,fcdate,fctime,vti,");
		sql.append("to_number(trim(" + temp + ")) as " + temp + "");
		sql.append(" from service.web_weather_forecast ");
		sql.append("where  fcdate='");
		sql.append(fcdate);
		sql.append("' and fctime=");
		sql.append(fctime);
		sql.append(" order by ");
		sql.append(temp);
		sql.append(" ");
		sql.append(sort);
		sql.append(") where rownum<=10");
		return entityDao.createSQLQuery(sql.toString(), null).list();
	}
	
	//产品相关
	@SuppressWarnings({ "unused", "rawtypes" })
	public Map<String,Object[]> getProductInfo(){
		Map<String,Object[]> map = new HashMap<String, Object[]>();
		List l = new ArrayList();
		String sql1 = "select trim(max(fcdate)),trim(max(fchh)),count(1) from BASE.INDEX_PMSC3H_DATA " +
				"where fcdate= (select max(fcdate) from BASE.INDEX_PMSC3H_DATA " +
					"where stationid='54511') and fchh=(select max(fchh) from BASE.INDEX_PMSC3H_DATA " +
						"where stationid='54511' and fcdate= (select max(fcdate) from BASE.INDEX_PMSC3H_DATA " +
							"where stationid='54511'))";
		String sql2 = "select trim(max(fcdate)),trim(max(fchh)),count(1) from BASE.INDEX_PMSC7D_DATA " +
				"where fcdate= (select max(fcdate) from BASE.INDEX_PMSC7D_DATA " +
					"where stationid='54511') and fchh=(select max(fchh) from BASE.INDEX_PMSC7D_DATA " +
						"where stationid='54511' and fcdate= (select max(fcdate) from BASE.INDEX_PMSC7D_DATA " +
							"where stationid='54511'))";
		
		Object[] dmz3h = (Object[])entityDao.createSQLQuery(sql1, null).list().get(0);
		Object[] dmz7d = (Object[])entityDao.createSQLQuery(sql2, null).list().get(0);
		
		map.put("dmz3h", dmz3h);
		map.put("dmz7d", dmz7d);
		return map;
	}
	
	public Map<String,Object[]> getRefinePro(){
		Map<String,Object[]> map = new HashMap<String, Object[]>();
		Connection conn = null;
		OraclePreparedStatement stmt = null;
		try {
			conn = DataUtil.getOtherConn("jdbc.url2","jdbc.username","jdbc.password");
			String[] tab = new String[]{"forecast_fine_3h","forecast_fine_12h","forecast_fine_24h"};
			for(String name : tab){
				String tsql = "select max(substr(to_char(utc_time,'yyyymmddhh24'),1,8))," +
						  "max(substr(to_char(utc_time,'yyyymmddhh24'),9,2))" +
						  "from forecast."+ name +
						  " where utc_time=(select max(utc_time) from forecast."+ name +
						  " where stationid='54511')";
				stmt = (OraclePreparedStatement)conn.prepareStatement(tsql);
				
				ResultSet rs = stmt.executeQuery();
				int date = 0;
				String time = "";
				int cnt = 0;
				while(rs.next()){
					date = rs.getInt(1);
					time = rs.getString(2);
				}
				String sql = "select count(1) from FORECAST." + name +
						" where utc_time= to_date('" + date + time + "','yyyymmddhh24')";
				stmt = (OraclePreparedStatement)conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				while(rs.next()){
					cnt = rs.getInt(1);
				}
				
				map.put(name, new Object[]{date,time,cnt});
			}
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{ 
		    if(stmt!=null){try{stmt.close();}catch(Exception e){}} 
		    if(conn!=null){try{conn.close();}catch(Exception e){}} 
	    } 
		
		return map;
	}
	
	public List<String[]> getForecast_15d(){
		List<String[]> list = new ArrayList<String[]>();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DataUtil.getOtherConn("realtime.url","realtime.username","realtime.password");
//			String sql = "select distinct(table_name) from MONITOR.PMSC_FORECAST_FINE t";
//			stmt = conn.prepareStatement(sql);
//			ResultSet rs = stmt.executeQuery();
			
			String sql = "select * from" +
					"(select t.*, t.rowid from MONITOR.PMSC_FORECAST_FINE t " +
					"where table_name like '3%' order by utc_time desc)" +
					"where rownum=1";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			String[] f_15d_3h = {"3小时15天精细化预报","","","","0"};
			if(rs.next()){
				f_15d_3h[1] = rs.getString(2).substring(0,8);
				f_15d_3h[2] = rs.getString(2).substring(8,10);
				f_15d_3h[3] = rs.getString(3);
			}
			list.add(f_15d_3h);
			
			sql = "select * from" +
					"(select t.*, t.rowid from MONITOR.PMSC_FORECAST_FINE t " +
					"where table_name like '24%' order by utc_time desc)" +
					"where rownum=1";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			String[] f_15d_24h = {"24小时15天精细化预报","","","","0"};
			if(rs.next()){
				f_15d_24h[1] = rs.getString(2).substring(0,8);
				f_15d_24h[2] = rs.getString(2).substring(8,10);
				f_15d_24h[3] = rs.getString(3);
			}
			list.add(f_15d_24h);
			
			conn = DataUtil.getOtherConn("jdbc.181","outer.username","outer.password");
			sql = "select count(*) from forecast.pmsc_fine_3h_15day " +
					"where utc_time=to_date('" + f_15d_3h[1] + f_15d_3h[2] + "0000','yyyy/mm/dd hh24:miss')";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				list.get(0)[4] = rs.getString(1);
			}
			sql = "select count(*) from forecast.pmsc_fine_24h_15day " +
					"where utc_time=to_date('" + f_15d_24h[1] + f_15d_24h[2] + "0000','yyyy/mm/dd hh24:miss')";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				list.get(1)[4] = rs.getString(1);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{ 
		    if(stmt!=null){try{stmt.close();}catch(Exception e){}} 
		    if(conn!=null){try{conn.close();}catch(Exception e){}} 
	    } 
		
		return list;
	}
	
	public List<Object[]> getErrStation(String date, String stationId, String fctime) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		StringBuffer sql = new StringBuffer("select to_char(t.fcdate,'yyyyMMddhh24'),t.* from PRODUCT.FORECAST_ERRSTATION t where ISUPDATE=0 ");  
		
		if(date != null && !date.isEmpty()){
			sql.append("and to_char(t.fcdate,'yyyyMMddhh24') like '" + date + "%'");
		}
		if(stationId != null && !stationId.isEmpty()){
			sql.append(" and t.stationid='" + stationId + "'");
		}
		if(fctime != null && !fctime.isEmpty()){
			sql.append(" and t.fchh=" + fctime);
		}
		
		Connection conn = DataUtil.getOtherConn("jdbc.53.url","jdbc.53.username","jdbc.53.password");;
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		ResultSet rs = pstmt.executeQuery();
		List<Object[]> list = new ArrayList<Object[]>();
		while(rs.next()){
			Object[] objs = new Object[23];
			for(int i=0;i<23;i++){
				objs[i] = rs.getString(i+1);
			}
			list.add(objs);
		}
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
		return list;
	}
	
	public void updateErrStation(WeatherData data) throws Exception{
		StringBuffer sql = new StringBuffer("update PRODUCT.FORECAST_ERRSTATION ");  
		sql.append("set weather1='" + data.getWeather1() + "',");
		sql.append("weather2='" + data.getWeather1_code() + "',");
		sql.append("wd1='" + data.getWeather2() + "',");
		sql.append("wd2='" + data.getWeather2_code() + "',");
		sql.append("ws1='" + data.getWd1() + "',");
		sql.append("ws2='" + data.getWd1_code() + "',");
		sql.append("temp1='" + data.getWd2()+ "',");
		sql.append("temp2='" + data.getWd2_code() + "',");
		sql.append("cold1='" + data.getWs1() + "',");
		sql.append("cold2='" + data.getWs1_code() + "',");
		sql.append("rain1='" + data.getWs2() + "',");
		sql.append("rain2='" + data.getWs2_code() + "',");
		sql.append("humidity1='" + data.getTemp1() + "',");
		sql.append("humidity2='" + data.getTemp2() + "',");
		sql.append("isupdate=1");
		
		sql.append(" where stationid='" + data.getStationID() + "'");
		sql.append(" and fcdate=to_date('" + data.getFcdate() + "','yyyy/mm/dd hh24')");
		sql.append(" and fchh='" + data.getFctime() + "'");
		sql.append(" and vti='" + data.getVti() + "'");
		
		Connection conn = DataUtil.getOtherConn("jdbc.53.url","jdbc.53.username","jdbc.53.password");;
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		logger.info(sql.toString());
		
		pstmt.execute();
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
	}
	
	public void insertCorrect(WeatherData data) throws Exception{
		StringBuffer sql = new StringBuffer("delete from PRODUCT.FORECAST where");
		sql.append(" stationid='"+ data.getStationID()+"'");
		sql.append(" and fcdate=to_date('" + data.getFcdate() + "','yyyy/mm/dd hh24')");
		sql.append(" and vti=" + data.getVti());
		sql.append(" and fchh=" + data.getFctime());
		
		Connection conn = DataUtil.getOtherConn("jdbc.53.url","jdbc.53.username","jdbc.53.password");;
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		logger.info(sql.toString());
		
		pstmt.executeUpdate();
		
		sql.setLength(0);
		sql.append("insert into PRODUCT.FORECAST ");
		sql.append("values ('" + data.getStationID() + "',");
		sql.append("to_date('" + data.getFcdate() + "','yyyy/mm/dd hh24'),");
		sql.append("'" + data.getFcdate().substring(0,4) + "',");
		sql.append("'" + data.getFcdate().substring(4,6) + "',");
		sql.append("'" + data.getFcdate().substring(6,8) + "',");
		sql.append("'" + data.getFctime() + "',");
		sql.append("'" + data.getVti() + "',");
		
		sql.append("'" + data.getWeather1() + "',");
		sql.append("'" + data.getWeather1_code() + "',");
		sql.append("'" + data.getWeather2() + "',");
		sql.append("'" + data.getWeather2_code() + "',");
		sql.append("'" + data.getWd1() + "',");
		sql.append("'" + data.getWd1_code() + "',");
		sql.append("'" + data.getWd2()+ "',");
		sql.append("'" + data.getWd2_code() + "',");
		sql.append("'" + data.getWs1() + "',");
		sql.append("'" + data.getWs1_code() + "',");
		sql.append("'" + data.getWs2() + "',");
		sql.append("'" + data.getWs2_code() + "',");
		sql.append("'" + data.getTemp1() + "',");
		sql.append("'" + data.getTemp2() + "')");
		
		pstmt = conn.prepareStatement(sql.toString());
		
		logger.info(sql.toString());
		
		pstmt.executeUpdate();
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
	}
	
	public WeatherData getNewStation(WeatherData data) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		StringBuffer sql = new StringBuffer("select t.*,to_char(t.fcdate,'yyyymmdd') fmtdate,t2.name c_name  from PRODUCT.CITYFORECAST t ");  
		sql.append("join product.dict_station_2348 t2");
		
		sql.append(" on t.stationid='" + data.getStationID() + "'");
		sql.append(" and t.fcdate=to_date('" + data.getFcdate() + "','yyyy/mm/dd hh24')");
		sql.append(" and t.fchh='" + data.getFctime() + "'");
		sql.append(" and t.vti='" + data.getVti() + "'");
		sql.append(" and t2.stationid='" + data.getStationID() + "'");
		
		Connection conn = DataUtil.getOtherConn("jdbc.53.url","jdbc.53.username","jdbc.53.password");;
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		ResultSet rs = pstmt.executeQuery();
		
		WeatherData newData = new WeatherData();
		if(rs.next()){
			newData.setStationID(rs.getString("stationid"));
			newData.setName(rs.getString("c_name"));
			newData.setFcdate(rs.getString("fmtdate"));//2014/5/8 20:00:00->20140508
			newData.setFcyear(rs.getString("fcyear"));
			newData.setFcmon(rs.getString("fcmon"));
			newData.setFcday(rs.getString("fcday"));
			newData.setFctime(rs.getString("fchh").substring(0,2));//0800->08
			newData.setVti(rs.getString("vti"));
			
			newData.setWeather1(rs.getString("weather1"));
			newData.setWeather1_code(rs.getString("weather1_code"));
			newData.setWeather2(rs.getString("weather2"));
			newData.setWeather2_code(rs.getString("weather2_code"));
			newData.setWd1(rs.getString("wd1"));
			newData.setWd1_code(rs.getString("wd1_code"));
			newData.setWd2(rs.getString("wd2"));
			newData.setWd2_code(rs.getString("wd2_code"));
			newData.setWs1(rs.getString("ws1"));
			newData.setWs1_code(rs.getString("ws1_code"));
			newData.setWs2(rs.getString("ws2"));
			newData.setWs2_code(rs.getString("ws2_code"));
			newData.setTemp1(rs.getString("temp1"));
			newData.setTemp2(rs.getString("temp2"));
		}
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
		return newData;
	}
	
	public void push(WeatherData data) throws Exception{
		insertDMZ(data);
		insertCityForecast(data);
		insertForecast(data);
	}
	
	void insertDMZ(WeatherData data){
		StringBuffer sql = new StringBuffer("delete from web_weather_forecast where stationid='"+
				data.getStationID()+"' and fcdate="+data.getFcdate()+
				" and vti="+data.getVti() +
				" and fchh="+data.getFctime());  

		logger.info(sql.toString());
		
		entityDao.executeSql(sql.toString());
		
		sql.setLength(0);
		sql.append("insert into web_weather_forecast ");
		sql.append("values ('" + data.getStationID() + "',");
		sql.append("'" + data.getName() + "',");
		sql.append("'" + data.getFcdate() + "',");
		sql.append("'" + data.getFctime() + "',");
		sql.append("'" + data.getFcyear() + "',");
		sql.append("'" + data.getFcmon() + "',");
		sql.append("'" + data.getFcday() + "',");
		sql.append("'" + data.getFctime() + "',");//对应表中的fchh
		sql.append("'" + data.getVti() + "',");
		sql.append("'" + data.getWeather1_code() + "',");
		sql.append("'" + data.getWeather1() + "',");
		sql.append("'" + data.getWeather2_code() + "',");
		sql.append("'" + data.getWeather2() + "',");
		sql.append("'" + data.getWd1_code() + "',");
		sql.append("'" + data.getWd1() + "',");
		sql.append("'" + data.getWd2_code() + "',");
		sql.append("'" + data.getWd2() + "',");
		sql.append("'" + data.getWs1_code() + "',");
		sql.append("'" + data.getWs1() + "',");
		sql.append("'" + data.getWs2_code() + "',");
		sql.append("'" + data.getWs2() + "',");
		sql.append("'" + data.getTemp1() + "',");
		sql.append("'" + data.getTemp2() + "',");
		sql.append("'MAN')");
//		System.out.println(sql.toString());
		
		logger.info(sql.toString());
		
		entityDao.executeSql(sql.toString());
	}
	
	void insertCityForecast(WeatherData data){
		StringBuffer sql = new StringBuffer("delete from forecast.city_forecast@racdb where stationid='"+
				data.getStationID()+"' and fcdate=to_date('" + data.getFcdate() + data.getFctime() + "','yyyy/mm/dd hh24')" +
				" and vti="+data.getVti()+
				" and fchh="+data.getFctime());  
		
		logger.info(sql.toString());
		
		entityDao.executeSql(sql.toString());
		
		sql.setLength(0);
		sql.append("insert into forecast.city_forecast@racdb ");  
		sql.append("values ('" + data.getStationID() + "',");
		sql.append("'" + data.getName() + "',");
		sql.append("to_date('" + data.getFcdate() + data.getFctime() + "','yyyy/mm/dd hh24'),");
		sql.append("'" + data.getFcyear() + "',");
		sql.append("'" + data.getFcmon() + "',");
		sql.append("'" + data.getFcday() + "',");
		sql.append("'" + data.getFctime() + "',");
		sql.append("'" + data.getVti() + "',");
		sql.append("'" + data.getWeather1_code() + "',");
		sql.append("'" + data.getWeather1() + "',");
		sql.append("'" + data.getWeather2_code() + "',");
		sql.append("'" + data.getWeather2() + "',");
		sql.append("'" + data.getWd1_code() + "',");
		sql.append("'" + data.getWd1() + "',");
		sql.append("'" + data.getWd2_code() + "',");
		sql.append("'" + data.getWd2()+ "',");
		sql.append("'" + data.getWs1_code() + "',");
		sql.append("'" + data.getWs1() + "',");
		sql.append("'" + data.getWs2_code() + "',");
		sql.append("'" + data.getWs2() + "',");
		
		sql.append("'" + data.getTemp1() + "',");
		sql.append("'" + data.getTemp2() + "',");
		sql.append("'MAN')");
//		System.out.println(sql.toString());
		
		logger.info(sql.toString());
		
		entityDao.executeSql(sql.toString());
	}
	
	void insertForecast(WeatherData data){
		StringBuffer sql = new StringBuffer("delete from forecast.forecast@racdb where stationid='"+
				data.getStationID()+
				"' and fcdate=to_date('" + data.getFcdate() + data.getFctime() + "','yyyy/mm/dd hh24')" +
				" and vti="+data.getVti() +
				" and fchh="+data.getFctime());  
		
		logger.info(sql.toString());
		
		entityDao.executeSql(sql.toString());
		
		sql.setLength(0);
		sql.append("insert into forecast.forecast@racdb ");  
		sql.append("values ('" + data.getStationID() + "',");
		sql.append("to_date('" + data.getFcdate()+data.getFctime() + "','yyyy/mm/dd hh24'),");
		sql.append("'" + data.getFcyear() + "',");
		sql.append("'" + data.getFcmon() + "',");
		sql.append("'" + data.getFcday() + "',");
		sql.append("'" + data.getFctime() + "',");
		sql.append("'" + data.getVti() + "',");
		sql.append("'" + data.getWeather1_code() + "',");
		sql.append("'" + data.getWeather2_code() + "',");
		sql.append("'" + data.getWd1_code() + "',");
		sql.append("'" + data.getWd2_code() + "',");
		sql.append("'" + data.getWs1_code() + "',");
		sql.append("'" + data.getWs2_code() + "',");
		sql.append("'" + data.getTemp1() + "',");
		sql.append("'" + data.getTemp2() + "')");
//		System.out.println(sql.toString());
		
		logger.info(sql.toString());
		
		entityDao.executeSql(sql.toString());
	}
	
	public String[] getWorldCupLog(){
		String[] log = new String[7];
		Connection conn = null;
		OraclePreparedStatement stmt = null;
		try {
			conn = DataUtil.getOtherConn("jdbc.url2","jdbc.username","jdbc.password");
			String sql = "select job_ip,job_name,product_name," +
					"to_char(job_time,'yyyymmddhh24miss') job_time,to_char(finish_time,'yyyymmddhh24miss') finish_time," +
					"status,remark" +
					" from FORECAST.MONITOR_FTP_FILE_LOG " +
					"where job_time=(select max(job_time) from FORECAST.MONITOR_FTP_FILE_LOG)";
			stmt = (OraclePreparedStatement)conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				log[0] = rs.getString("job_ip");
				log[1] = rs.getString("job_name");
				log[2] = rs.getString("product_name");
				log[3] = rs.getString("job_time");
				log[4] = rs.getString("finish_time");
				log[5] = rs.getString("status");
				log[6] = rs.getString("remark");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{ 
		    if(stmt!=null){try{stmt.close();}catch(Exception e){}} 
		    if(conn!=null){try{conn.close();}catch(Exception e){}} 
	    }
		
		return log;
	}
}
