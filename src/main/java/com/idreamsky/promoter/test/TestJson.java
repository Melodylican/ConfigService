package com.idreamsky.promoter.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestJson {
	public static void main(String[] args) {
		String text="[{\"port\":3317,\"ip\":\"192.168.2.44\",\"weight\":80,\"userName\":\"root\",\"password\":\"123456\"},{\"port\":3317,\"ip\":\"192.168.2.44\",\"weight\":20,\"userName\":\"root\",\"password\":\"123456\"}]";
		JSONArray jArray = JSON.parseArray(text);
		String ip="";
		String port="";
		String userName="";
		String password="";
		String weight="";
		for(int i=0;i<jArray.size();i++) {
			JSONObject jb = jArray.getJSONObject(i);
			ip +=jb.getString("ip")+"#";
			port +=jb.getString("port")+"#";
			userName += jb.getString("userName")+"#";
			password += jb.getString("password")+"#";
			if(jb.containsKey("weight"))
				weight += jb.getString("weight")+"#";
		}
		ip = ip.substring(0,ip.length()-1).replace("#", "<br>");
		port = port.substring(0,port.length()-1).replace("#", "<br>");
		userName = userName.substring(0,userName.length()-1).replace("#", "<br>");
		password = password.substring(0,password.length()-1).replace("#", "<br>");
		if(weight.length()>1)
			weight = weight.substring(0,weight.length()-1).replace("#", "<br>");
		
		System.out.println("ip = "+ip);
		System.out.println("port = "+port);
		System.out.println("userName = "+userName);
		System.out.println("password = "+password);
		System.out.println("weight = "+weight);
	}

}
