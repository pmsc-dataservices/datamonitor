package com.weather.db.filter;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.weather.db.entity.Assignment;
import com.weather.db.quartz.DBJob;
import com.weather.db.quartz.DBScheduler;
import com.weather.db.service.AssignmentService;


public class ScheduleListener implements ServletContextListener{
	private ContextLoader contextLoader;
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		  
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		contextLoader = createContextLoader();  
	    contextLoader.initWebApplicationContext(event.getServletContext());  
	      
	    //获取在web.xml中配置的路径  
	    String path = event.getServletContext().getInitParameter( "contextConfigLocation");  
	    ApplicationContext context = new ClassPathXmlApplicationContext(path);  
	    
	    //初始化调度任务
//	    initQuartzManager(context);
	}

	protected ContextLoader createContextLoader() {  
        return new ContextLoader();  
    } 
	
	public void initQuartzManager(ApplicationContext context){  
		AssignmentService assiService = (AssignmentService) context.getBean("assService");  
        
		List<Assignment> asslist = assiService.loadAll();
		for(Assignment ass : asslist){
			if(!ass.getStatus().equals("success")){
				if("immediately".equals(ass.getTime()) && "once".equals(ass.getExecutivemode())){
					assiService.saveOrUpdate(DBJob.doJob(ass));
				}
				else {
					DBScheduler.createTask(ass);
				}
			}
		}
    }  
}
