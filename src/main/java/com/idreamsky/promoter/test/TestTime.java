package com.idreamsky.promoter.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dsky.baas.configservice.util.DateUtil;

public class TestTime {
	public static void main(String[] args) {
		String time ="2016-08-17 19:25:00";
		long time1 = DateUtil.parseDateFromString(time, "yyyy-MM-dd HH:mm:ss", 0);
		long now = System.currentTimeMillis();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(DateUtil.parseDate(now)+"   **"+sf.format(now));
		System.out.println(time1 + " ===== "+now +"  "+(now - time1));
		
		long t1=1471433100000L;
		int   t=1478852656;
		Date d = new Date(t*1000L);
		SimpleDateFormat  sf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		System.out.println(sf1.format(d));
	}

}
