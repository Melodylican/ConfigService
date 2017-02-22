package com.dsky.baas.configservice.logservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;


public class CommonUtil {

	private final static Logger log = Logger.getLogger(CommonUtil.class);
	/**
	 * 解析http header 中的cookie
	 * @param req
	 * @return
	 */
	public static Map<String, String> parseHeaderCookie(HttpServletRequest req) {
		String cookieString = req.getHeader("cookie");
		if (cookieString == null || cookieString.isEmpty()) {
			cookieString = req.getHeader("cookied");
		}
		log.debug("cookie names string:"
				+ JSON.toJSONString(req.getHeaderNames()));

		if (cookieString != null && !cookieString.isEmpty()) {
			String[] cookieArray = cookieString.split(";");
			Map<String, String> cookieRes = new HashMap<String, String>();
			for (int i = 0; i < cookieArray.length; i++) {
				String cookieItem = cookieArray[i].trim();
				String[] cookieItemKV = cookieItem.split("=");
				if (cookieItemKV.length == 2) {
					cookieRes.put(cookieItemKV[0].trim(),
							cookieItemKV[1].trim());
				}
			}
			return cookieRes;
		} else {
			return null;
		}

	}

	public static int getNowTimeStamp() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public static Object pickValue(Object... args){
		Object now = null;
		for(int i=0;i<args.length;i++){
			if(args[i]!=null){
				if(args[i] instanceof String){
					now = args[i];
					String nowString = (String)now;
					if(!nowString.isEmpty()){
						return nowString;
					}
				}else if(args[i] instanceof Integer){
					now = args[i];
					int nowInt = parseInt(now);
					if(nowInt!=0){
						return nowInt;
					}
				}
			}
		}
		return now;
	}
	
	public static int parseInt(Object param){
		return parseInt(param,0);
	}

	public static int parseInt(Object param,int defaultNumber){
		if(param==null){
			return defaultNumber;
		}
		try {
			return Integer.parseInt(param.toString());	
		} catch (Exception e) {
			return defaultNumber;
		}
	}	
	
	/**
	 * 返回今天是星期几
	 * @return 0 周日，1周一，....，6周六
	 */
	public static int getTodayOfWeek(){
		Date today = new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(today);
        return c.get(Calendar.DAY_OF_WEEK)-1;
	}
	
	public static String getConfigItem(String itemName,String filename){
		InputStream inputStream = CommonUtil.class.getClassLoader().getResourceAsStream(filename);
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e) {
			log.debug("getConfigItem error: "+e.getMessage());			
			return null;
		}
		
		String result = p.getProperty(itemName);
		log.debug("getConfigItem name: "+itemName);
		log.debug("getConfigItem filename: "+filename);
		log.debug("getConfigItem result: "+result);
		try {
			inputStream.close();
		} catch (IOException e) {
			log.debug("getConfigItem input stream close error: "+e.getMessage());
		}
		return result;
	}
	public static String getConfigItem(String itemName){
		return getConfigItem(itemName, "config.properties");
	}
	
	
	public static boolean isAControllerClassName(String className){
//		com.dsky.baas.gameinvite.controller.InvitedCodeController
		return Pattern.compile("^com\\.dsky\\.baas\\.(.*)Controller$").matcher(className).find();

	}
}
