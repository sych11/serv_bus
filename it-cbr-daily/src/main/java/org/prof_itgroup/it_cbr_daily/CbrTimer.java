package org.prof_itgroup.it_cbr_daily;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.it.utils.exception.ExceptionUtil;
import org.it.utils.http.HTTPLogListener;
import org.it.utils.http.HTTPService;
import org.it.utils.jms.JmsSender;
import org.it.utils.properties.PropertiesService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

public class CbrTimer implements Runnable{
	
	private static Logger log = Logger.getLogger(CbrTimer.class.getName());

	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	private PropertiesService propertiesService;
	private HTTPService httpService;

	private JmsSender jmsSender;

	public CbrTimer(PropertiesService propertiesService, HTTPService httpService, JmsSender jmsSender) {
		
		this.propertiesService = propertiesService;
		this.httpService = httpService;
		this.jmsSender = jmsSender;
		
		threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(5);
		threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		threadPoolTaskScheduler.initialize();
		
		threadPoolTaskScheduler.schedule(this, new CronTrigger(propertiesService.get("cron")));
	}
	
	public void close(){
		if (threadPoolTaskScheduler!=null){
			threadPoolTaskScheduler.shutdown();
		}
	}

	@Override
	public void run() {
		try{
		// TODO Auto-generated method stub
		String url = propertiesService.get("url");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		url = url+"?date_req="+dateFormat.format(new Date());
				
		Map<String, String> httpHeaders = new HashMap<String, String>();
		httpHeaders.put("Content-Type", "text/xml;charset=UTF-8");
		String response = httpService.goGet(url, httpHeaders, 60000, "Cp1251", new HTTPLogListener() {
			
			@Override
			public void response(String url, Map<String, String> headers, String request, String response) {
				log.info("response:"+response);				
			}
			
			@Override
			public void request(String url, Map<String, String> headers, String request) {
				log.info("request:"+request);	
			}
			
			@Override
			public void exception(String url, Map<String, String> headers, String request, Throwable ex) {
 				log.info("error:"+ExceptionUtil.getPrintStackTraceAsString(ex));
				
			}
		});
		
		jmsSender.send("cbr-daily-topic", response);
		
		}catch(Exception e){
			log.info("CbrTimer error:"+ExceptionUtil.getPrintStackTraceAsString(e));
		}
	}

}
