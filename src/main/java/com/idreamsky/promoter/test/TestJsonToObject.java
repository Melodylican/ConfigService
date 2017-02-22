package com.idreamsky.promoter.test;

import com.alibaba.fastjson.JSONObject;

public class TestJsonToObject {
	public static void main(String[] args) {
		//String str="{id:'1',gameName:'name',gameId:'gameId',location:'中国',gameVersion:'V3.3.8',gameLevel:'10',beginTime:'2016-04-01',
	//endTime:'2016-04-05',preheatingTime:'2016-03-01',description:'haha....'}";
		//PromoterBean promoterBean =JSONObject.parseObject("",PromoterBean.class);
		String str = "{gameId:'12346678996N',location:'台湾'}";
		JSONObject json = JSONObject.parseObject(str);
		String s =  json.get("gameId").toString();
		System.out.println(s);
	}

}
