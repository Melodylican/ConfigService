package com.dsky.baas.configservice.util;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;
/**
 * 支持不同日志级别输出到不同文件中
 * @author chris.li
 *
 */
public class LogAppender extends DailyRollingFileAppender {

	public boolean isAsSevereAsThreshold(Priority priority) {
		//只判断是否相等，而不判断优先级  
		return this.getThreshold().equals(priority); 
	}
}
