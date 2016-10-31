package com.weather.db.service;

import org.springframework.stereotype.Service;

import base.service.DefaultEntityManager;

import com.weather.db.entity.DataBaseInfo;

@Service
public class DataBaseService extends DefaultEntityManager<DataBaseInfo> {

	public DataBaseInfo getById(Integer id){
		return entityDao.findUniqueByProperty("id", id);
	}
}
