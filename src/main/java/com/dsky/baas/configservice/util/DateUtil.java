package com.dsky.baas.configservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * @ClassName: DateUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class DateUtil {
	
	public static long parseDateFromString(String sDate,String patten,int day){
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(patten);
		java.util.Calendar c = java.util.Calendar.getInstance();
		try {
			Date date = sdf.parse(sDate);
			c.setTime(date);
			c.add(Calendar.DAY_OF_YEAR, day);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c.getTime().getTime();
	}

	public static String parseDateDefaultYesterday(String patten, String[] args) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -1);
		String date = DateFormatUtils.format(c.getTime(), patten);
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length - 1; i++) {
				if ("-date".equals(args[i]) || "--date".equals(args[i])) {
					date = args[i + 1].trim();
				}
			}
		}

		return date;
	}

	public static long getDayMinTime(String date, String patten) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(patten);
		try {
			Date d = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			return c.getTimeInMillis() / 1000;

		} catch (ParseException e) {
			throw new RuntimeException("解析时间出错", e);
		}

	}

	public static long getDayMaxTime(String date, String patten) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(patten);
		try {
			Date d = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			return c.getTimeInMillis() / 1000;

		} catch (ParseException e) {
			throw new RuntimeException("解析时间出错", e);
		}

	}
	
	public static long getTodayMinTime(){
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		long l = c.getTimeInMillis() / 1000;
		return l;
	}
	
	public static String parseDate(long time){
		java.util.Calendar c = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		c.setTimeInMillis(time);
		return sdf.format(c.getTime());
		
	}
	
	public static String parseDateWithHour(long time){
		java.util.Calendar c = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
		c.setTimeInMillis(time);
		return sdf.format(c.getTime());
		
	}
	
    public static String parseDate(long time,String patten){
        java.util.Calendar c = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(patten);
        c.setTimeInMillis(time);
        return sdf.format(c.getTime());
    }
	
	public static String getToday(){
		java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		java.util.Calendar c = java.util.Calendar.getInstance();
		return sdf.format(c.getTime());
	}

}
