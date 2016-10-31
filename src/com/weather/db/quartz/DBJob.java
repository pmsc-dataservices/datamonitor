package com.weather.db.quartz;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import oracle.jdbc.driver.OraclePreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weather.db.entity.Assignment;
import com.weather.db.service.AssignmentService;

public class DBJob implements Job {
	
	@Resource
	AssignmentService assignmentService;

	static Log logger = LogFactory.getLog(DBJob.class);
	public static String sqlException = "SQL语句执行失败!";
	public static String ioException = "文件生成失败!";
	public static String classNotFoundException = "初始化数据库驱动失败!";

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		JobDetail jobDetail = context.getJobDetail();

		// The name is defined in the job definition

		String jobName = jobDetail.getName();// 任务名称

		// Log the time the job started

		logger.info(jobName + " fired at " + new Date());// 记录任务开始执行的时间

		// The directory to scan is stored in the job map

		JobDataMap dataMap = jobDetail.getJobDataMap();// 任务所配置的数据映射表

		Assignment task = (Assignment) dataMap.get("task");

		if(assignmentService == null){
			assignmentService = getAssignmentService();
		}
		
		assignmentService.saveOrUpdate(doJob(task));
	}

	public static Assignment doJob(Assignment task) {
		try {
			String url = "";
			if ("oracle".equals(task.getType())) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				url = "jdbc:oracle:thin:@" + task.getIp() + ":"
						+ task.getPort() + ":" + task.getDatabase();
			}
			Connection conn = DriverManager.getConnection(url,
					task.getUsername(), task.getPwd());

			Date d = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
			String dateStr = dateFormat.format(d);

			OraclePreparedStatement stmt = (OraclePreparedStatement) conn
					.prepareStatement(task.getSql());
			boolean bool = stmt.execute();

			if (bool) {
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				FileWriter fw = null;

				while (rs.next()) {
					StringBuffer content = new StringBuffer();
					if (fw == null) {
						fw = new FileWriter(task.getPath() + "/"
								+ task.getFileName() + "_" + dateStr
								+ task.getSuffix() + ".txt");
					}
					for (int i = 1; i < columnCount; i++) {
						content.append(rs.getString(i) + ",");
					}
					content.append(rs.getString(columnCount) + "\r\n");
					fw.write(content.toString());
				}
				if (fw != null) {
					fw.close();
				}
			}

			conn.close();
			task.setStatus("executing");
			return task;
		} catch (SQLException e) {
			task.setStatus("failure");
			logger.error(sqlException, e);
			return task;
		} catch (IOException e) {
			task.setStatus("failure");
			logger.error(ioException, e);
			return task;
		} catch (ClassNotFoundException e) {
			task.setStatus("failure");
			logger.error(classNotFoundException, e);
			return task;
		}
	}
	
	protected AssignmentService getAssignmentService(){
		ApplicationContext context = new ClassPathXmlApplicationContext("hibernateDataAccessContext.xml");
		AssignmentService assiService = (AssignmentService) context.getBean("assService");
		return assiService;
	}

}
