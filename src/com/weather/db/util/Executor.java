package com.weather.db.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.weather.db.service.LogService;

public class Executor {
	public static void redo_jingxihua() throws Exception{
    	ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
    	//第一步，执行数据入库
		String ip = bundle.getString("import.ip");
		String username = bundle.getString("import.user");
		String pwd = bundle.getString("import.pwd");
		String file = bundle.getString("import.file_08");
		int hour = new Date().getHours();
		if(hour >= 20){
			file = bundle.getString("import.file_20");
		}
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    	
    	//第二步，67推送
    	ip = bundle.getString("export_67.ip");
		username = bundle.getString("export_67.user");
		pwd = bundle.getString("export_67.pwd");
		file = bundle.getString("export_67.file");
		
    	exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    	
    	//第三步，171推送
    	ip = bundle.getString("export_171.ip");
		username = bundle.getString("export_171.user");
		pwd = bundle.getString("export_171.pwd");
		file = bundle.getString("export_171.file");
		
    	exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    }
	
	public static void redo_worldcup() throws Exception{
    	ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("78.ip");
		String username = bundle.getString("78.user");
		String pwd = bundle.getString("78.pwd");
		String file = bundle.getString("worldcup.file");
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    	
    }
	
	public static void redo_f_15d(String type) throws Exception{
    	ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("78.ip");
		String username = bundle.getString("78.user");
		String pwd = bundle.getString("78.pwd");
		String file = bundle.getString("forecast_pmsc_15day_" + type);
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    	
    }
	
	public static void redo_7d(String type) throws Exception{
    	ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("78.ip");
		String username = bundle.getString("78.user");
		String pwd = bundle.getString("78.pwd");
		String file = bundle.getString("forecast_pmsc_7day_" + type);
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    	
    }
	
	public static void redo05() throws Exception{
    	ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("city_forecast_7d.ip");
		String username = bundle.getString("city_forecast_7d.user");
		String pwd = bundle.getString("city_forecast_7d.pwd");
		String file1 = bundle.getString("city_forecast_7d.file_05");
		String file2 = bundle.getString("city_forecast_7d.file_20y");
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file1);
    	exe.exec("sh " + file2);
    }
	
	public static void update_f_7d() throws Exception{
    	ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("city_forecast_7d.ip");
		String username = bundle.getString("city_forecast_7d.user");
		String pwd = bundle.getString("city_forecast_7d.pwd");
		String file = bundle.getString("update_forecast_7d.file");
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    }
	
	public static void redo_f_7d() throws Exception{
    	ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("city_forecast_7d.ip");
		String username = bundle.getString("city_forecast_7d.user");
		String pwd = bundle.getString("city_forecast_7d.pwd");
		String file = bundle.getString("city_forecast_7d.file_08");
		SimpleDateFormat fmt = new SimpleDateFormat("HHmm");
		String timeStr = fmt.format(new Date());
		if("1020".compareTo(timeStr) < 0 && "1625".compareTo(timeStr) >= 0){
			file = bundle.getString("city_forecast_7d.file_12");
		}
		else if("1625".compareTo(timeStr) < 0 && "2359".compareTo(timeStr) >= 0){
			file = bundle.getString("city_forecast_7d.file_20");
		}
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    }
	
	public static void handout_fine_15d() throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("78.ip");
		String username = bundle.getString("78.user");
		String pwd = bundle.getString("78.pwd");
		String file = bundle.getString("forecasefine_15d.file");
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    }
	
	public static void realtime_add(String supplytime) throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("rt_add.ip");
		String username = bundle.getString("rt_add.user");
		String pwd = bundle.getString("rt_add.pwd");
		String file = bundle.getString("rt_add.file");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");
		Date d = df.parse(supplytime);
		d.setHours(d.getHours()-8);
		supplytime = df.format(d);
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    	LogService.insertLog(time, supplytime);
    	exe.exec("sh " + file + " " + supplytime.substring(0,8) + " " + supplytime.substring(8));
    	LogService.updateLog(time, supplytime);
    }
	
	public static void pmsc_fine_3h_15day() throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("78.ip");
		String username = bundle.getString("78.user");
		String pwd = bundle.getString("78.pwd");
		String file = bundle.getString("pmsc_fine_3h_15day.file");
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    }
	
	public static void pmsc_fine_12h_15day() throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("78.ip");
		String username = bundle.getString("78.user");
		String pwd = bundle.getString("78.pwd");
		String file = bundle.getString("pmsc_fine_12h_15day.file");
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    }
	
	public static void pmsc_fine_24h_15day() throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("78.ip");
		String username = bundle.getString("78.user");
		String pwd = bundle.getString("78.pwd");
		String file = bundle.getString("pmsc_fine_24h_15day.file");
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    }
	
	public static void pmsc_fine_autoup(String type) throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("78.ip");
		String username = bundle.getString("78.user");
		String pwd = bundle.getString("78.pwd");
		String file = bundle.getString("pmsc_fine_autoup.filePath") + "pmsc_fine_"+type+"_autoup.sh";
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    }
	
	public static void dataintegration(String type) throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("file.ip");
		String username = bundle.getString("file.user");
		String pwd = bundle.getString("file.pwd");
		String file = bundle.getString(type+"_file_path");
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    }
	
	public static void dzbd(List<String> params) throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("rt_add.ip");
		String username = bundle.getString("rt_add.user");
		String pwd = bundle.getString("rt_add.pwd");
		String cfg = bundle.getString("rt_dzbd.cfg");
		String file = bundle.getString("rt_dzbd.file");
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	
    	exe.exec("echo '' >> " + cfg);
    	for(String p : params){
    		exe.exec("echo '" + p + "' > " + cfg);
    	}
    	
    	exe.exec("sh " + file);
    	
    	String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    	for(String p : params){
    		LogService.insertLog(time, p.substring(0,p.indexOf(" ")), "单站自动");
    	}
    }
	
	public static void redofs(String fname) throws Exception{
    	ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("78.ip");
		String username = bundle.getString("78.user");
		String pwd = bundle.getString("78.pwd");
		String file = bundle.getString("forecast_service") + fname + ".sh";
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    	
    }
	
	public static void redo_index3d(String type) throws Exception{
    	ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");  
    	
		String ip = bundle.getString("78.ip");
		String username = bundle.getString("78.user");
		String pwd = bundle.getString("78.pwd");
		String file = bundle.getString("index_3d_redo") + type + ".sh";
		
    	RmtShellExecutor exe = new RmtShellExecutor(ip, username, pwd);
    	exe.exec("sh " + file);
    	
    }
}
