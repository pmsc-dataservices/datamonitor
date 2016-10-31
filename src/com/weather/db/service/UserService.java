package com.weather.db.service;

import java.util.List;

import org.springframework.stereotype.Service;

import base.service.DefaultEntityManager;

import com.weather.db.entity.SysUser;

@Service
public class UserService extends DefaultEntityManager<SysUser>{
	
	public SysUser getUser(String name, String pwd) {
		List<SysUser> user = entityDao.findByProperty("name", name);
		if(user == null){
			return null;
		}
		for(SysUser u : user){
			if (pwd.equals(u.getPwd())) {
				return u;
			}
		}
		return null;
	}
}
