package com.weather.db.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

import com.weather.db.entity.Assignment;

public class DBScheduler {

	static Log logger = LogFactory.getLog(DBScheduler.class);
	
	public static void createTask(Assignment task) {
		DBScheduler dbs = new DBScheduler();

		try{

			// Create a Scheduler and schedule the Job

			Scheduler scheduler = dbs.createScheduler();

			// Jobs can be scheduled after Scheduler is running

			scheduler.start();

			logger.info("Scheduler started at " + new Date());

			// Schedule the first Job

			dbs.scheduleJob(scheduler, task.getName(), DBJob.class,
					task, task.getPeriod());

		} catch (SchedulerException ex) {
			logger.error("任务执行时失败!",ex);
			task.setStatus("failure");
		}

	}

	public Scheduler createScheduler() throws SchedulerException

	{// 创建调度器

		return StdSchedulerFactory.getDefaultScheduler();

	}

	@SuppressWarnings({ "deprecation", "static-access", "rawtypes" })
	private void scheduleJob(Scheduler scheduler, String jobName,
			Class jobClass, Assignment task, String scanInterval)
			throws SchedulerException

	{

		// Create a JobDetail for the Job

		JobDetail jobDetail = new JobDetail(jobName, Scheduler.DEFAULT_GROUP,
				jobClass);

		// Configure the directory to scan

		jobDetail.getJobDataMap().put("task", task);

		// Trigger that repeats every "scanInterval" secs forever
		
		
		Trigger trigger = TriggerUtils.makeSecondlyTrigger();
		
		if(scanInterval != null){
			
			trigger = TriggerUtils.makeSecondlyTrigger((int)(60*60*Float.parseFloat(scanInterval)));
		}

		trigger.setName(jobName + "-Trigger");

		Date date = new Date();
		if(!task.getTime().equals("immediately")){
			Calendar calendar = new GregorianCalendar();  
	        calendar.setTime(date);  
	        
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
			int hour = date.getHours();
			if(hour > Integer.parseInt(task.getTime())){
				calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动  
			}
			date = calendar.getTime();
			String dateString = dateFormat.format(date);
			try {
				date = dateFormat.parse(dateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
			
		// Start the trigger firing from now
		trigger.setStartTime(date);

		// Associate the trigger with the job in the scheduler

		scheduler.scheduleJob(jobDetail, trigger);

	}
}
