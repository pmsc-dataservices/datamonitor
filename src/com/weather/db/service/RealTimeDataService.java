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
public class RealTimeDataService extends DefaultEntityManager<WeatherData>{

	public List<Object[]> getRealTimeErrData() throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
//		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
//		String c_bjtime = fmt.format(new Date());
//		String sql = "select max(C_BJTIME) from OBSERVE.ELE_AWST_MONITOR";
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		ResultSet rs = pstmt.executeQuery();
//		if(rs.next()){
//			c_bjtime = rs.getString(1).substring(0,8);
//		}
//		
//		sql = "select * from OBSERVE.ELE_AWST_MONITOR where c_bjtime >= '" + c_bjtime + "'" +
//				" order by C_BJTIME desc, TABLE_NAME asc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		sql = "select * from (select * from OBSERVE.ELE_AWST_MONITOR " +
				" order by C_BJTIME desc, TABLE_NAME asc) where rownum <=12";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<Object[]> list = new ArrayList<Object[]>();
		while(rs.next()){
			Object[] objs = new Object[8];
			for(int i=0;i<8;i++){
				objs[i] = rs.getString(i+1);
			}
			list.add(objs);
		}
		
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
		return list;
	}
	
	public Map<Integer,String[]> getDetailErrData() throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		String c_bjtime = fmt.format(new Date());
		String sql = "select max(C_BJTIME) from OBSERVE.NATION_AWST_MONITOR";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			c_bjtime = rs.getString(1);
		}
		Date d = fmt.parse(c_bjtime);
		d.setHours(d.getHours()-6);//最近6个小时
		c_bjtime = fmt.format(d);
		sql = "select * from OBSERVE.NATION_AWST_MONITOR " +
				"where c_bjtime >='" + c_bjtime + "'" +
				" order by c_bjtime desc";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		Map<Integer,String[]> map = new HashMap<Integer,String[]>();
		Map<String,Integer> index = new HashMap<String,Integer>();
		int i = 0;
		while(rs.next()){
			String key = rs.getString("c_bjtime") + rs.getString("provcn");
			String[] items = {rs.getString("c_bjtime"),rs.getString("provcn"),rs.getString("sta_info"),"1"};
			if(index.get(key) != null){
				String[] detail = map.get(index.get(key));
				detail[2] += "," + items[2];
				detail[3] = Integer.parseInt(detail[3]) + 1 + "";
				items = detail;
			}else{
				i++;
				index.put(key, i);
			}
			map.put(index.get(key), items);
		}
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
		return map;
	}
	
	public List<Object[]> getJobsInfo() throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		String sql = "select JOB_NAME,CONTACTS_MAN,JOB_PROCESS_NAME,JOB_PROCESS_TIME,SERVICE_TYPE " +
				"from observe.sms_job_baseinfo";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
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
	
	public String map() throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHH");
		String c_bjtime = fmt.format(new Date());
		
		String sql = "select max(C_BJTIME) from OBSERVE.ELE_AWST_MONITOR";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			c_bjtime = rs.getString(1);
		}
		
		sql = "select prov," +
				"(case when station_count=cnt then 1 else 0 end ) as status" +
				" from dictionary.flash_station " +
				"join " +
				"(select t.proven prov, count(distinct(t.stationid)) cnt " +
				"from " +
				"(select t1.v01000 stationid,t1.c_bjtime,t2.proven from observe.ele_awst_nation t1 " +
				"join dictionary.dict_station t2 " +
				"on t1.v01000 = t2.observe_id " +
				"and t1.c_bjtime = '" + c_bjtime + "0000') t group by t.proven) " +
				"on proven=prov";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		StringBuffer json = new StringBuffer();
		json.append("{\"date\":\"" + c_bjtime + "\",");
		while(rs.next()){
			json.append('"' + rs.getString("prov") + '"' + ":" + rs.getString("status") + ",");
		}
		json.setLength(json.length()-1);
		json.append("}");
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
		return json.toString();
	}
	
	public String detail(String jobName) throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		String sql = "select * from observe.sms_job_baseinfo where JOB_NAME = '" + jobName + "'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		StringBuffer json = new StringBuffer();
		json.append("{");
		if(rs.next()){
			json.append("\"phone\":\"" + rs.getString("MOBILE_PHONE") + "\",");
			json.append("\"sDB\":\"" + rs.getString("SOURCE_DATABASE") + "\",");
			json.append("\"sTable\":\"" + rs.getString("SOURCE_DATA_TABLE") + "\",");
			json.append("\"sDBinfo\":\"" + rs.getString("SOURCE_DATA_INFO") + "\",");
			json.append("\"jobIp\":\"" + rs.getString("JOB_PROCESS_IP") + "\",");
			json.append("\"tIp\":\"" + rs.getString("SERVER_IP") + "\",");
			json.append("\"fNameFormat\":\"" + rs.getString("DATA_NAME_FORMAT") + "\",");
			json.append("\"fPath\":\"" + rs.getString("DATA_DIR") + "\",");
			json.append("\"tDB\":\"" + rs.getString("TARGET_DATABASE") + "\",");
			json.append("\"tTable\":\"" + rs.getString("TARGET_DATA_TABLE") + "\",");
			json.append("\"tDBinfo\":\"" + rs.getString("TARGET_DATA_INFO") + "\",");
			json.append("\"user\":\"" + rs.getString("DATA_USER") + "\",");
			json.append("\"note\":\"" + rs.getString("REMARK") + "\"");
		}
		json.append("}");
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
		return json.toString();
	}
	
	public List<Object[]> list (String begin, String end, String cityType, String city) throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		StringBuffer sql = new StringBuffer("select d.namecn,t.* from OBSERVE.ELE_AWST_NATION t " +
				"join DICTIONARY.DICT_STATION d on t.v01000=d.observe_id ");  
		
		sql.append("and t.c_bjtime>='" + begin + "0000'");
		sql.append("and t.c_bjtime<='" + end + "0000'");
		sql.append("and t.c_bjtime like '%0000'");
		if("name".equals(cityType)){
			sql.append("and d." + cityType + " like '%" + city + "%'");
		}
		else {
			sql.append(" and d." + cityType + " = '" + city + "'");
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		List<Object[]> list = new ArrayList<Object[]>();
		String value = "";
		while(rs.next()){
			Object[] objs = new Object[18];
			objs[0] = rs.getString(1);
			objs[1] = rs.getString(2);
			objs[2] = rs.getString(4);
			for(int i=3;i<18;i++){
				value = rs.getString(i+5);
				objs[i] = value.indexOf(".")==0?"0"+value:value;
			}
			list.add(objs);
		}
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
		return list;
	}
	
	public void eidt(Data obj) throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		StringBuffer sql = new StringBuffer("update OBSERVE.ELE_AWST_NATION set ");  
		sql.append("c_itime=to_date(?,'yyyymmddhh24miss'),");
		sql.append("V12001=?,");
		sql.append("S12001=?,");
		sql.append("V11043=?,");
		sql.append("V11041=?,");
		sql.append("S11041=?,");
		sql.append("V13003=?,");
		sql.append("V13019=?,");
		sql.append("V13021=?,");
		sql.append("V13022=?,");
		sql.append("V13023=?,");
		sql.append("V20001=?,");
		sql.append("V10004=?,");
		sql.append("S10004=?,");
		sql.append("V20010=?,");
		sql.append("V20003=?");
		sql.append(" where V01000=?");
		sql.append(" and C_BJTIME=?");
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		pstmt.setString(1,fmt.format(new Date()));//入库时间
		pstmt.setString(2,obj.getColumn4());
		pstmt.setString(3,obj.getColumn5());
		pstmt.setString(4,obj.getColumn6());
		pstmt.setString(5,obj.getColumn7());
		pstmt.setString(6,obj.getColumn8());
		pstmt.setString(7,obj.getColumn9());
		pstmt.setString(8,obj.getColumn10());
		pstmt.setString(9,obj.getColumn11());
		pstmt.setString(10,obj.getColumn12());
		pstmt.setString(11,obj.getColumn13());
		pstmt.setString(12,obj.getColumn14());
		pstmt.setString(13,obj.getColumn15());
		pstmt.setString(14,obj.getColumn16());
		pstmt.setString(15,obj.getColumn17());
		pstmt.setString(16,obj.getColumn18());
		pstmt.setString(17,obj.getColumn1());
		pstmt.setString(18,obj.getColumn3());
		
		pstmt.executeUpdate();
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
	}
	
	public void add(Data obj) throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		StringBuffer sql = new StringBuffer("insert into OBSERVE.ELE_AWST_NATION values(?,to_date(?,'yyyymmddhh24miss'),?," +
				"999999,999999,999999,999999,?,?,?,?,?,?,?,?,?,?,999999,?,999999,999999) ");  
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		pstmt.setString(1,obj.getColumn1());//站号
		pstmt.setString(2,fmt.format(new Date()));//入库时间
		pstmt.setString(3,obj.getColumn2());//北京时间
		pstmt.setString(4,obj.getColumn3());//服务气温
		pstmt.setString(5,obj.getColumn4());//服务风向
		pstmt.setString(6,obj.getColumn6());//服务风速
		pstmt.setString(7,obj.getColumn5());//服务风级别
		pstmt.setString(8,obj.getColumn7());//相对湿度
		pstmt.setString(9,obj.getColumn8());//1小时降水
		pstmt.setString(10,obj.getColumn9());//6小时降水
		pstmt.setString(11,obj.getColumn10());//12小时降水
		pstmt.setString(12,obj.getColumn11());//24小时降水
		pstmt.setString(13,obj.getColumn12());//能见度
		pstmt.setString(14,obj.getColumn13());//服务气压
		
		pstmt.executeUpdate();
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}}
	}
	
	public boolean validate(String stationid) throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		String sql = "select * from dictionary.monitor_nation_awst_station where stationid='" + stationid + "'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		boolean exist = false;
		if(rs.next()){
			exist = true;
		}
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}}
		
		return exist;
	}
	
	public boolean validate(String stationid,String c_bjtime) throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		String sql = "select * from OBSERVE.ELE_AWST_NATION where v01000='" + stationid + "' and C_BJTIME='" + c_bjtime + "'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		boolean exist = false;
		if(rs.next()){
			exist = true;
		}
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}}
		
		return exist;
	}
	
	public String latestTime() throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.80.url","jdbc.80.username","jdbc.80.password");
		
		String sql = "select max(c_bjtime) from OBSERVE.ELE_AWST_NATION where v01000='54511'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		String time = "";
		if(rs.next()){
			time = rs.getString(1);
		}
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}}
		
		return time;
	}
	
	public List<Object[]> wOrList (String cityType,String city,String time,String type) throws Exception{
		Connection conn = DataUtil.getOtherConn("realtime.url","realtime.username","realtime.password");
		
		StringBuffer sql = new StringBuffer("select d.name,t.* from " + type +" t " +
				"join dictionary.view_dict_station_area d on t.v01000=d.stationid ");  
		
		sql.append("and t.c_bjtime='" + time + "0000'");
		if("name".equals(cityType)){
			sql.append("and d." + cityType + " like '%" + city + "%'");
		}
		else {
			sql.append(" and d." + cityType + " = '" + city + "'");
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		List<Object[]> list = new ArrayList<Object[]>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		while(rs.next()){
			Object[] objs = new Object[columnCount];
			for(int i=0;i<columnCount;i++){
				if("NUMBER".equals(rsmd.getColumnTypeName(i+1))){
					objs[i] = rs.getFloat(i+1);
				}
				else{
					objs[i] = rs.getString(i+1);
				}
			}
			list.add(objs);
		}
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
		
		return list;
	}
	
	public void eidtWoR(Data obj) throws Exception{
		Connection conn = DataUtil.getOtherConn("realtime.url","realtime.username","realtime.password");
		
		String type = obj.getColumn1();
		String sql = "";
		if("wind".equals(type)){
			sql = "update OBSERVE.ELE_AWST_WIND set " +
				  "WIND_DIRECT='" + obj.getColumn5() + "'," +
				  "WIND_DIRECT_LEVEL='" + obj.getColumn6() + "'," +
				  "WIND_SPEED='" + obj.getColumn7() + "'," +
				  "WIND_SPEED_LEVEL='" + obj.getColumn8() + "'" +
				  "where V01000='" + obj.getColumn2() + "' " +
				  "and C_BJTIME='" + obj.getColumn4() + "'";
		}
		else if("rain".equals(type)){
			sql = "update OBSERVE.ELE_AWST_RAIN set " +
				  "RAIN='" + obj.getColumn5() + "'" +
				  "where V01000='" + obj.getColumn2() + "' " +
				  "and C_BJTIME='" + obj.getColumn4() + "'";
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
	}
	
	public void delWoR(Data obj) throws Exception{
		Connection conn = DataUtil.getOtherConn("realtime.url","realtime.username","realtime.password");
		
		String type = obj.getColumn1();
		String sql = "";
		if("wind".equals(type)){
			type = "OBSERVE.ELE_AWST_WIND";
		}
		else if("rain".equals(type)){
			type = "OBSERVE.ELE_AWST_RAIN";
		}
		
		sql = "delete from " + type + 
				  " where V01000='" + obj.getColumn2() + "' " +
				  " and C_BJTIME='" + obj.getColumn3() + "'";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		
		if(pstmt!=null){try{pstmt.close();}catch(Exception e){}} 
		if(conn!=null){try{conn.close();}catch(Exception e){}} 
	}
}
