package com.dsky.baas.configservice.util;

import java.util.HashMap;
import java.util.Map;

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
	public static Map<String, String> parseHeaderCookie(HttpServletRequest req){
		String cookieString = req.getHeader("cookie");
		if(cookieString == null || cookieString.isEmpty()){
			cookieString = req.getHeader("cookied");
		}
		log.info("cookie names string:"+JSON.toJSONString(req.getHeaderNames()));
		
		if(cookieString!=null && !cookieString.isEmpty()){
			String[] cookieArray = cookieString.split(";");
			Map<String, String> cookieRes = new HashMap<String,String>();
			for (int i = 0; i < cookieArray.length; i++) {
				String cookieItem = cookieArray[i].trim();
				String[] cookieItemKV = cookieItem.split("=");
				if(cookieItemKV.length==2){;
					cookieRes.put(cookieItemKV[0], cookieItemKV[1]);
				}
			}
			return cookieRes;
		}else{
			return null;
		}
		
	}
	
	public static int getNowTimeStamp(){
		return (int)(System.currentTimeMillis()/1000);
	}
}
