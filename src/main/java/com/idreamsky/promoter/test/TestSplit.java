package com.idreamsky.promoter.test;

import java.util.HashMap;
import java.util.Map;

public class TestSplit {
	public static void main(String[] args) {
		String sourceStr = "等级:10,时间:5";
		String[] str = sourceStr.split(",");
		Map<String,String> map = new HashMap<String,String>();
		for(String s:str) {
			String[] option = s.split(":");
			if(option[0].equals("时间"))
				if(option[1].contains("("))
			        option[1]=option[1].substring(0, option[1].indexOf("("));
			map.put(option[0], option[1]);
		}
	}

}
