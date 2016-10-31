package com.weather.db.service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import base.service.DefaultEntityManager;

import com.weather.db.entity.Data;
import com.weather.db.entity.WeatherData;
import com.weather.db.util.DataUtil;

@Service
public class ForecastService extends DefaultEntityManager<WeatherData>{
	Logger logger=Logger.getLogger(ForecastService.class);

	public List<Object[]> f_15dayList (String tabType, String cityType, String city,String vti,boolean bj) throws Exception{
		Connection conn = DataUtil.getOtherConn("f15d.jdbc.url","f15d.jdbc.username","f15d.jdbc.password");
		
		StringBuffer sql = new StringBuffer("select f.stationid,d.name,to_char(utc_time,'yyyymmddhh24'),vti,");  
		String tabName = "forecast.pmsc_fine_3h_15day";
		if("24h".equals(tabType)){
			tabName = "forecast.pmsc_fine_24h_15day";
			sql.append("fchh,weather1_code,weather1_desc_cn,weather1_desc_en," +
					"weather2_code,weather2_desc_cn,weather2_desc_en," +
					"wd1_code,wd1_desc_cn,wd1_desc_en,wd2_code,wd2_desc_cn,wd2_desc_en," +
					"ws1_code,ws1_desc_cn,ws1_desc_en,ws2_code,ws2_desc_cn,ws2_desc_en," +
					"temp1,temp2");
		}
		else{
			sql.append("max_temp,min_temp,rain,ff,ff_level,ff_desc_cn,ff_desc_en,dd,dd_level," +
					"dd_desc_cn,dd_desc_en,cloud,weather,weather_desc_cn,weather_desc_en,rh");
			if("12h".equals(tabType)){
				sql.append(",rh_max,rh_min");
				tabName = "forecast.pmsc_fine_12h_15day";
			}
		}
		sql.append(" from " + tabName + " f join dictionary.dict_station_fine d " +
				"on f.stationid=d.stationid");
		
		if("3h".equals(tabType)){
			sql.append(" and vti>0");
		}
		
		if(bj){
			sql.append(" and proven='beijing' and nameen!='honghua' ");
		}
		else{
			if("name".equals(cityType)){
				sql.append(" and d." + cityType + " like '%" + city + "%' ");
			}
			else {
				sql.append(" and d." + cityType + " = '" + city + "' ");
			}
		}
		if(vti != null && !vti.isEmpty()){
			sql.append(" and vti=" + vti + " ");
		}
		
		sql.append(" order by f.stationid desc,vti asc");
		System.out.println(sql.toString());
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		List<Object[]> list = new ArrayList<Object[]>();
		String value = "";
		while(rs.next()){
			Object[] objs = new Object[columnCount];
			for(int i=0;i<columnCount;i++){
				value = rs.getString(i+1);
				objs[i] = value.indexOf(".")==0?"0"+value:value;
			}
			list.add(objs);
		}
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
		return list;
	}
	
	public void save_3hor12h_15day(Data data,String tabType) throws Exception{
		Connection conn = DataUtil.getOtherConn("f15d.jdbc.url","f15d.jdbc.username","f15d.jdbc.password");		
		
		String tabName = "forecast.pmsc_fine_3h_15day";
		if("12h".equals(tabType)){
			tabName = "forecast.pmsc_fine_12h_15day";
		}
		
		StringBuffer sql = new StringBuffer("update " + tabName + " set ");  
		sql.append("max_temp=?,");
		sql.append("min_temp=?,");
		sql.append("rain=?,");
		sql.append("ff=?,");
		sql.append("ff_level=?,");
		sql.append("ff_desc_cn=?,");
		sql.append("ff_desc_en=?,");
		sql.append("dd=?,");
		sql.append("dd_level=?,");
		sql.append("dd_desc_cn=?,");
		sql.append("dd_desc_en=?,");
		sql.append("cloud=?,");
		sql.append("weather=?,");
		sql.append("weather_desc_cn=?,");
		sql.append("weather_desc_en=?,");
		sql.append("rh=?");
//		sql.append("DATA_SOURCE='MAN'");
		if("12h".equals(tabType)){
			sql.append(",rh_max=?,rh_min=?");
		}
		sql.append(" where stationid=?");
		sql.append(" and utc_time = to_date(?,'yyyymmddhh24')");
		sql.append(" and vti=?");
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		logger.info(sql.toString());
		StringBuffer values = new StringBuffer("update " + tabName + " values(");
		
		Field[] fields = data.getClass().getDeclaredFields();//获得属性
		for (Field field : fields) {
		    PropertyDescriptor pd = new PropertyDescriptor(field.getName(),data.getClass());
		    Method getMethod = pd.getReadMethod();
	    	int name = Integer.parseInt(getMethod.getName().substring(9));
	    	if(name >  4 && name < 21){//对应data方法data.getColumn5()-data.getColumn20()
	    		pstmt.setString(name-4, getMethod.invoke(data).toString());
	    		values.append(getMethod.invoke(data).toString()+",");
	    	}
	    }
		if("12h".equals(tabType)){
			pstmt.setString(17,data.getColumn21());
			pstmt.setString(18,data.getColumn22());
			pstmt.setString(19,data.getColumn1());
			pstmt.setString(20,data.getColumn3());
			pstmt.setString(21,data.getColumn4());
			values.append(data.getColumn21()+","+data.getColumn22()+","+data.getColumn1()+","
					+data.getColumn3()+"," +data.getColumn4()+")");
		}
		else{
			pstmt.setString(17,data.getColumn1());
			pstmt.setString(18,data.getColumn3());
			pstmt.setString(19,data.getColumn4());
			values.append(data.getColumn1()+","
					+data.getColumn3()+"," +data.getColumn4()+")");
		}
		logger.info(values.toString());
		pstmt.executeUpdate();
		
		//20160826修改，去除注解
		sql.setLength(0);
		sql.append("select to_char(LOCAL_TIME,'yyyymmddhh24') LOCAL_TIME,TIME_ZONE,to_char(PUBLIC_TIME,'yyyymmddhh24') PUBLIC_TIME,to_char(BEIJING_TIME,'yyyymmddhh24') BEIJING_TIME,to_char(FORECAST_TIME,'yyyymmddhh24') FORECAST_TIME,temp from " + tabName);
		sql.append(" where stationid=?");
		sql.append(" and utc_time = to_date(?,'yyyymmddhh24')");
		sql.append(" and vti=?");
		pstmt = conn.prepareStatement(sql.toString());
		pstmt.setString(1,data.getColumn1());
		pstmt.setString(2,data.getColumn3());
		pstmt.setString(3,data.getColumn4());
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		data.setColumn23(rs.getString("LOCAL_TIME"));
		data.setColumn24(rs.getString("TIME_ZONE"));
		data.setColumn25(rs.getString("PUBLIC_TIME"));
		data.setColumn26(rs.getString("BEIJING_TIME"));
		data.setColumn27(rs.getString("FORECAST_TIME"));
		data.setColumn28(rs.getString("TEMP"));
		insert(conn,pstmt,data,tabType);
		//
		conn.commit();
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}}
	}
	
	private void insert(Connection conn,PreparedStatement pstmt,Data data,String tabType) throws Exception{
		String delsql = "";
		
		String sql = "insert into ";
		StringBuffer values = new StringBuffer();
		if("12h".equals(tabType)){
			sql += "forecast.pmsc_fine_12h_15day_update values(?,to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),?,to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			delsql = "delete from forecast.pmsc_fine_12h_15day_update where"
				+" stationid=" + data.getColumn1()
				+" and utc_time=to_date('" + data.getColumn3() + "','yyyymmddhh24')"
				+" and vti=" + data.getColumn4();
			values.append("update forecast.pmsc_fine_12h_15day_update values(");
		}
		else{
			sql += "forecast.pmsc_fine_3h_15day_update values(?,to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),?,to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			delsql = "delete from forecast.pmsc_fine_3h_15day_update where"
				+" stationid=" + data.getColumn1()
				+" and utc_time=to_date('" + data.getColumn3() + "','yyyymmddhh24')"
				+" and vti=" + data.getColumn4();
			values.append("update forecast.pmsc_fine_3h_15day_update values(");
		}
		
		logger.info(delsql);
		
		pstmt = conn.prepareStatement(delsql);
		pstmt.executeUpdate();
		
		logger.info(sql);
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,data.getColumn1());
		String c_itime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		pstmt.setString(2,c_itime);//c_itime 
		pstmt.setString(3,data.getColumn3());//utc_time
		pstmt.setString(4,data.getColumn23());//LOCAL_TIME
		pstmt.setString(5,data.getColumn24());//TIME_ZONE
		pstmt.setString(6,data.getColumn25());//PUBLIC_TIME
		pstmt.setString(7,data.getColumn26());//BEIJING_TIME
		pstmt.setString(8,data.getColumn27());//FORECAST_TIME
		pstmt.setString(9,data.getColumn4());//vti
		pstmt.setString(10,data.getColumn28());//temp
		
		values.append(data.getColumn1() + "," + c_itime + "," + data.getColumn3() + "," + 
		data.getColumn23() + "," + data.getColumn24() + "," + data.getColumn25() + "," +
		data.getColumn26() + "," +data.getColumn27() + "," +data.getColumn4() + "," +
		data.getColumn28());
		
		Field[] fields = data.getClass().getDeclaredFields();//获得属性
		for (Field field : fields) {
		    PropertyDescriptor pd = new PropertyDescriptor(field.getName(),data.getClass());
		    Method getMethod = pd.getReadMethod();
	    	int name = Integer.parseInt(getMethod.getName().substring(9));
	    	if(name >  4 && name < 21){//对应data方法data.getColumn5()-data.getColumn20()
	    		pstmt.setString(name+6, getMethod.invoke(data).toString());
	    		values.append("," + getMethod.invoke(data).toString());
	    	}
	    }
		if("12h".equals(tabType)){
			pstmt.setString(27,data.getColumn21());
			pstmt.setString(28,data.getColumn22());
			values.append("," + data.getColumn21() + "," + data.getColumn22());
		}
		values.append(")");
		logger.info(values.toString());
		pstmt.executeUpdate();
	}
	
	public void save_24h_15day(Data data) throws Exception{
		System.out.println("1=========="+new Date());
		Connection conn = DataUtil.getOtherConn("f15d.jdbc.url","f15d.jdbc.username","f15d.jdbc.password");		
		
		StringBuffer sql = new StringBuffer("update forecast.pmsc_fine_24h_15day set ");  
		sql.append("fchh=?,");
		sql.append("weather1_code=?,");
		sql.append("weather1_desc_cn=?,");
		sql.append("weather1_desc_en=?,");
		sql.append("weather2_code=?,");
		sql.append("weather2_desc_cn=?,");
		sql.append("weather2_desc_en=?,");
		sql.append("wd1_code=?,");
		sql.append("wd1_desc_cn=?,");
		sql.append("wd1_desc_en=?,");
		sql.append("wd2_code=?,");
		sql.append("wd2_desc_cn=?,");
		sql.append("wd2_desc_en=?,");
		sql.append("ws1_code=?,");
		sql.append("ws1_desc_cn=?,");
		sql.append("ws1_desc_en=?,");
		sql.append("ws2_code=?,");
		sql.append("ws2_desc_cn=?,");
		sql.append("ws2_desc_en=?,");
		sql.append("temp1=?,");
		sql.append("temp2=?");
//		sql.append("DATA_SOURCE='MAN'");
		sql.append(" where stationid=?");
		sql.append(" and utc_time = to_date(?,'yyyymmddhh24')");
		sql.append(" and vti=?");
		
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		logger.info(sql.toString());
		StringBuffer values = new StringBuffer("update forecast.pmsc_fine_24h_15day values(");
		
		Field[] fields = data.getClass().getDeclaredFields();//获得属性
		for (Field field : fields) {
		    PropertyDescriptor pd = new PropertyDescriptor(field.getName(),data.getClass());
		    Method getMethod = pd.getReadMethod();
	    	int name = Integer.parseInt(getMethod.getName().substring(9));
	    	if(name >  4 && name < 26){//对应data方法data.getColumn5()-data.getColumn25()
	    		pstmt.setString(name-4, getMethod.invoke(data).toString());
	    		values.append(getMethod.invoke(data).toString()+",");
	    	}
	    }
		pstmt.setString(22,data.getColumn1());
		pstmt.setString(23,data.getColumn3());
		pstmt.setString(24,data.getColumn4());
		values.append(data.getColumn1() + "," + data.getColumn3() + "," +data.getColumn4() + ")");
		
		logger.info(values.toString());
		
		pstmt.executeUpdate();
		System.out.println("2=========="+new Date());
		
		//20160826修改，去除注解
		
		sql.setLength(0);
		sql.append("delete from forecast.pmsc_fine_24h_15day_update where");
		sql.append(" stationid=" + data.getColumn1());
		sql.append(" and utc_time=to_date('" + data.getColumn3() + "','yyyymmddhh24')");
		sql.append(" and vti=" + data.getColumn4());
				
		logger.info(sql.toString());
		
		pstmt = conn.prepareStatement(sql.toString());
		pstmt.executeUpdate();
		System.out.println("3=========="+new Date());
		
		sql.setLength(0);
		sql.append("select to_char(LOCAL_TIME,'yyyymmddhh24') LOCAL_TIME,TIME_ZONE,to_char(PUBLIC_TIME,'yyyymmddhh24') PUBLIC_TIME,to_char(BEIJING_TIME,'yyyymmddhh24') BEIJING_TIME,to_char(FORECAST_TIME,'yyyymmddhh24') FORECAST_TIME from forecast.pmsc_fine_24h_15day");
		sql.append(" where stationid=?");
		sql.append(" and utc_time = to_date(?,'yyyymmddhh24')");
		sql.append(" and vti=?");
		pstmt = conn.prepareStatement(sql.toString());
		pstmt.setString(1,data.getColumn1());
		pstmt.setString(2,data.getColumn3());
		pstmt.setString(3,data.getColumn4());
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		
		sql.setLength(0);
		sql.append("insert into forecast.pmsc_fine_24h_15day_update values(?,to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),?,to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		pstmt = conn.prepareStatement(sql.toString());
		
		values.setLength(0);
		pstmt.setString(1,data.getColumn1());
		String c_itime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		pstmt.setString(2,c_itime);//c_itime 
		pstmt.setString(3,data.getColumn3());//utc_time
		pstmt.setString(4,rs.getString("LOCAL_TIME"));//LOCAL_TIME
		pstmt.setString(5,rs.getString("TIME_ZONE"));//TIME_ZONE
		pstmt.setString(6,rs.getString("PUBLIC_TIME"));//PUBLIC_TIME
		pstmt.setString(7,rs.getString("BEIJING_TIME"));//BEIJING_TIME
		pstmt.setString(8,rs.getString("FORECAST_TIME"));//FORECAST_TIME
		pstmt.setString(9,data.getColumn5());//fchh
		pstmt.setString(10,data.getColumn4());//vti
		
		values.append(data.getColumn1() + "," + c_itime + "," + data.getColumn3() + "," + 
				rs.getString("LOCAL_TIME") + "," + rs.getString("TIME_ZONE") + "," + rs.getString("PUBLIC_TIME") + "," +
				rs.getString("BEIJING_TIME") + "," +rs.getString("FORECAST_TIME") + "," +data.getColumn5() + "," +
				data.getColumn4());
		
		for (Field field : fields) {
		    PropertyDescriptor pd = new PropertyDescriptor(field.getName(),data.getClass());
		    Method getMethod = pd.getReadMethod();
	    	int name = Integer.parseInt(getMethod.getName().substring(9));
	    	if(name >  5 && name < 26){//对应data方法data.getColumn6()-data.getColumn25()
	    		pstmt.setString(name+5, getMethod.invoke(data).toString());
	    		values.append("," + getMethod.invoke(data).toString());
	    	}
	    }
		values.append(")");
		logger.info(sql.toString());
		logger.info(values.toString());
		
		pstmt.executeUpdate();
		System.out.println("4=========="+new Date());
		/////////////////
		conn.commit();
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}}
	}
	
	public List<Object[]> forecastService(String type) throws Exception{
		Connection conn = null;
		
		String sql = "";
		if("intranet".equals(type)){
			conn = DataUtil.getOtherConn("jdbc.53.url","jdbc.53.username","jdbc.53.password");
			sql = "select count(distinct(f.stationid)),fchh,to_char(sysdate,'yyyymmdd') " +
					"from PRODUCT.FORECAST f,product.forecast_babj_station_snwfd d " +
					"where fcdate >= to_date(to_char(sysdate,'yyyymmdd'),'yyyymmddhh24miss') " +
					"and f.stationid=d.stationid " +
					"group by fchh";
		}
		else if("dmz".equals(type)){
			sql = "select count(distinct(stationid)),fctime,fcdate from SERVICE.WEB_WEATHER_FORECAST " +
					"where fcdate = to_char(sysdate,'yyyymmdd') " +
					"group by fctime,fcdate";
			
			return entityDao.createSQLQuery(sql, null).list();
		}
		else if("outernet".equals(type)){
			conn = DataUtil.getOtherConn("jdbc.181","jdbc.username","jdbc.password");
			sql = "select count(distinct(f.stationid)),fchh,to_char(sysdate,'yyyymmdd') from FORECAST.city_FORECAST f " +
					"where fcdate >= to_date(to_char(sysdate,'yyyymmdd'),'yyyymmddhh24miss') " +
					"and stationid in (select distinct(forecast_id) from dictionary.dict_station where nationcn='中国') " +
					"group by fchh";
		}
		else return null;
		
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
	
	public String dmzerr(String fctime) throws Exception{
		
		String sql = "select count(distinct(stationid)) from BASE.FORECAST_BABJ_ERROR " +
				"where fcdate = to_char(sysdate,'yyyymmdd') " +
				"and fctime ='" + fctime + "'";
		
		
		return entityDao.createSQLQuery(sql, null).uniqueResult().toString();
	}
}
