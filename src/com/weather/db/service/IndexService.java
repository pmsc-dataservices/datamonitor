package com.weather.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import base.service.DefaultEntityManager;

import com.weather.db.entity.Data;
import com.weather.db.entity.WeatherData;
import com.weather.db.util.DataUtil;

@Service
public class IndexService extends DefaultEntityManager<WeatherData>{

	public List<Object[]> list3d() throws Exception{
		Connection conn = DataUtil.getOtherConn("jdbc.53.url","jdbc.53.username","jdbc.53.password");
		String sql = "select indexab,d.name,fchh,vti,count(stationid) from product.indexfc_new f,product.dict_index d " +
					"where fcdate=to_char(sysdate,'yyyymmdd') " +
					"and fchh=(select max(fchh) from product.indexfc_new where fcdate=to_char(sysdate,'yyyymmdd')) " +
					"and indexab=code " +
					"group by indexab,d.name,vti,fchh " +
					"order by indexab,d.name,vti,fchh";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<Object[]> list = new ArrayList<Object[]>();
		String index = "";
		String[] objs = new String[4];
		while(rs.next()){
			String vti_cnt = rs.getString(4)+"-"+rs.getString(5);
			if(rs.getString(5).compareTo("2404") < 0){
				vti_cnt = "<span style='color:red;'>" + vti_cnt + "</span>";
			}
			if(index.equals(rs.getString("indexab"))){
				
				objs[3] += "<br>" + vti_cnt;
			}
			else {
				if(!index.equals("")){
					list.add(objs);
				}
				
				index = rs.getString("indexab");
				objs = new String[4];
				objs[0] = index;
				objs[1] = rs.getString(2);
				objs[2] = rs.getString(3);
				objs[3] = vti_cnt;
			}
		}
		
		return list;
	}
	
}
