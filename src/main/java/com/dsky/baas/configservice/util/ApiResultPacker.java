package com.dsky.baas.configservice.util;


import java.util.HashMap;
/**
 * @ClassName: ApiResultPacker
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.dsky.baas.configservice.model.ApiResultBean;
import com.dsky.baas.configservice.model.ExchApiResultBean;
import com.dsky.baas.configservice.model.ExchangeBean;
import com.dsky.baas.configservice.model.GameConfig;
import com.dsky.baas.configservice.model.HaveExchConfig;
import com.dsky.baas.configservice.model.HaveGameConfig;
import com.dsky.baas.configservice.model.IntegralSchemeBean;
import com.dsky.baas.configservice.model.PromoterBean;
import com.dsky.baas.configservice.model.RedeemCodeBean;
/**
 * 返回数据对象装成统一格式的json对象
 * @author chris.li
 */
public class ApiResultPacker {
	private static final Logger logger = Logger.getLogger(ApiResultPacker.class);
	
	public static ApiResultObject packToApiResultObject(Integer code,String msg,Object data,Object ext){
		ApiResultObject resultObj = new ApiResultObject();
		
		if(code==null){
			code = 0;
		}
		resultObj.setCode(code);
		
		if(msg==null){
			msg = "";
		}
		resultObj.setMsg(msg);
		
		if(data!=null){
			resultObj.setData(data);
		}
		
		if(ext!=null){
			resultObj.setExt(ext);
		}
		return resultObj;
	}
	public static ApiResultObject packToApiResultObject(Integer code,String msg,Object data){
		return packToApiResultObject(code,msg,data,null);
	}
	public static ApiResultObject packToApiResultObject(Integer code,String msg){
		return packToApiResultObject(code,msg,null,null);
	}	
	public static ApiResultObject packToApiResultObject(Object data){
		
		return packToApiResultObject(null,null,data,null);
	}
	public static ApiResultObject packToApiResultObject(Object data,Object ext){
		
		return packToApiResultObject(null,null,data,ext);
	}
	
	public static ApiResultErrorObject packToApiResultErrorObject(Integer code,String msg){
		ApiResultErrorObject resultErrObj = new ApiResultErrorObject();
		
		if(code==null){
			code = 0;
		}
		resultErrObj.setCode(code);
		
		if(msg==null){
			msg = "";
		}
		resultErrObj.setMsg(msg);
		return resultErrObj;
	}
	
	
	public static String packToJSON(Integer code,String msg,Object data,Object ext){
		
		return JSON.toJSONString(packToApiResultObject(code,msg,data,ext));
	}
	public static String packToJSON(Integer code,String msg,Object data){
		return packToJSON(code,msg,data,null);
	}
	public static String packToJSON(Integer code,String msg){
		return packToJSON(code,msg,null,null);
	}	
	public static String packToJSON(Object data){
		
		return packToJSON(null,null,data,null);
	}
	public static String packToJSON(Object data,Object ext){
		
		return packToJSON(null,null,data,ext);
	}
	
	public static GameConfig parsePromoterBean2GameConfig(PromoterBean promoterBean) {
		GameConfig gameConfig = new GameConfig();
		gameConfig.setId(promoterBean.getId());
		gameConfig.setGameId(promoterBean.getGameId());
		gameConfig.setGameName(promoterBean.getGameName());
		gameConfig.setBeginTime(promoterBean.getBeginTime());
		gameConfig.setEndTime(promoterBean.getEndTime());
		String[] str = promoterBean.getGameType().split(",");
		Map<String,String> map = new HashMap<String,String>();
		for(String s:str) {
			String[] option = s.split(":");
			if(option[0].equals("等级"))
				option[0]="level";//将汉字 :“等级” 替换为英文 : “level”
			if(option[0].equals("时间"))
				if(option[1].contains("(")) {
					option[0]="time";//将汉字:“时间” 替换为英文:“time”
			        option[1]=option[1].substring(0, option[1].indexOf("("));
			    }
			map.put(option[0], option[1]);
		}
		gameConfig.setConfig_option(map);
		return gameConfig;
	}
	
	public static HaveGameConfig parsePromoterBean2HaveGameConfig(PromoterBean promoterBean,ExchangeBean exch) {
		HaveGameConfig haveGameConfig = new HaveGameConfig();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("beginTime", DateUtil.parseDateFromString(promoterBean.getBeginTime(),"yyyy-MM-dd HH:mm:ss",0));
		data.put("endTime", DateUtil.parseDateFromString(promoterBean.getEndTime(),"yyyy-MM-dd HH:mm:ss",0));
		data.put("id", promoterBean.getId());
		data.put("gameName", promoterBean.getGameName());
		
		data.put("state", promoterBean.getState());
		data.put("exchStandard1",exch.getExchStandard1());
		data.put("exchStandard2",exch.getExchStandard2());
		Map<String,Object> ext = new HashMap<String,Object>();
		haveGameConfig.setData(data);
		haveGameConfig.setExt(ext);
		return haveGameConfig;
	}
	
	public static HaveExchConfig parsePromoterBean2HaveExchConfig(ExchangeBean exchBean) {
		HaveExchConfig haveExchConfig = new HaveExchConfig();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("period1", exchBean.getPeriod1());
		data.put("period2", exchBean.getPeriod2());
		Map<String,Object> ext = new HashMap<String,Object>();
		haveExchConfig.setData(data);
		haveExchConfig.setExt(ext);
		return haveExchConfig;
	}
	
	public static GameConfig parsePromoterBean2GameConfig(PromoterBean promoterBean, IntegralSchemeBean integralSchemeBean) {
		GameConfig gameConfig = new GameConfig();
		gameConfig.setId(promoterBean.getId());
		gameConfig.setGameId(promoterBean.getGameId());
		gameConfig.setGameName(promoterBean.getGameName());
		gameConfig.setBeginTime(promoterBean.getBeginTime());
		gameConfig.setEndTime(promoterBean.getEndTime());
		String[] str = promoterBean.getGameType().split(",");
		Map<String,String> map = new HashMap<String,String>();
		for(String s:str) {
			String[] option = s.split(":");
			if(option[0].equals("等级"))
				option[0]="level";//将汉字 :“等级” 替换为英文 : “level”
			if(option[0].equals("时间"))
				if(option[1].contains("(")) {
					option[0]="time";//将汉字:“时间” 替换为英文:“time”
			        option[1]=option[1].substring(0, option[1].indexOf("("));
			    }
			map.put(option[0], option[1]);
		}
		map.put("register", integralSchemeBean.getRegister()+"");
		map.put("promoterA", integralSchemeBean.getPromoterA()+"");
		map.put("promoterB", integralSchemeBean.getPromoterB()+"");
		gameConfig.setConfig_option(map);
		return gameConfig;
	}
	
	public static GameConfig parsePromoterBean2GameConfig(PromoterBean promoterBean, RedeemCodeBean redeemCodeBean) {
		GameConfig gameConfig = new GameConfig();
		gameConfig.setId(promoterBean.getId());
		gameConfig.setGameId(promoterBean.getGameId());
		gameConfig.setGameName(promoterBean.getGameName());
		gameConfig.setBeginTime(promoterBean.getBeginTime());
		gameConfig.setEndTime(promoterBean.getEndTime());
		String[] str = promoterBean.getGameType().split(",");
		Map<String,String> map = new HashMap<String,String>();
		for(String s:str) {
			String[] option = s.split(":");
			if(option[0].equals("等级"))
			    option[0]="level";//将汉字 :“等级” 替换为英文 : “level”
			if(option[0].equals("时间"))
				if(option[1].contains("(")) {
					option[0]="time";//将汉字:“时间” 替换为英文:“time”
			        option[1]=option[1].substring(0, option[1].indexOf("("));
			    }
			map.put(option[0], option[1]);
		}
		map.put("deviceCount", redeemCodeBean.getDeviceCount()+"");
		map.put("recommandCount", redeemCodeBean.getRecommandCount()+"");
		gameConfig.setConfig_option(map);
		return gameConfig;
	}
	
	public static ApiResultBean parsePromoterBean2ApiResultBean(PromoterBean pBean, RedeemCodeBean rBean,IntegralSchemeBean iBean) {
		ApiResultBean apiResult = new ApiResultBean();
		apiResult.setId(pBean.getId());
		apiResult.setGameId(pBean.getGameId());
		apiResult.setGameName(pBean.getGameName());
		apiResult.setBeginTime(pBean.getBeginTime());
		apiResult.setEndTime(pBean.getEndTime());
		apiResult.setPreheatingTime(pBean.getPreheatingTime());
		apiResult.setExchH5Url(pBean.getExchH5Url());
		apiResult.setState(pBean.getState());
		apiResult.setDeviceCount(rBean.getDeviceCount());
		apiResult.setRecommandCount(rBean.getRecommandCount());
		apiResult.setH5Url(rBean.getH5Url());
		apiResult.setImgUrl(rBean.getImgUrl());
		apiResult.setTitle(rBean.getTitle());
		apiResult.setRedeemDesc(rBean.getRedeemDesc());
		apiResult.setTime(iBean.getTime());
		apiResult.setLevel(iBean.getLevel());
		apiResult.setPromoterA(iBean.getPromoterA());
		apiResult.setPromoterATime(iBean.getPromoterATime());
		apiResult.setPromoterALevel(iBean.getPromoterALevel());
		apiResult.setPromoterASecond(iBean.getPromoterASecond());
		apiResult.setPromoterB(iBean.getPromoterB());
		apiResult.setRegister(iBean.getRegister());
		apiResult.setShareMethod(rBean.getShareMethod());
		apiResult.setPreheatingUrl(pBean.getPreheatingUrl());
		apiResult.setEndDesc(pBean.getEndDesc());
		apiResult.setRedeemCode(iBean.getRedeemCode());
		apiResult.setExchCash(pBean.getExchCash());
		apiResult.setDeviceLimit(rBean.getDeviceLimit());
		apiResult.setPromoterAFirst(iBean.getPromoterAFirst());
		apiResult.setPromoterAThird(iBean.getPromoterAThird());
		apiResult.setLevelSecond(iBean.getLevelSecond());
		apiResult.setLevelThird(iBean.getLevelThird());
		apiResult.setPromoterBSecond(iBean.getPromoterBSecond());
		apiResult.setPromoterBThird(iBean.getPromoterBThird());
		apiResult.setRechargeA(iBean.getRechargeA());
		apiResult.setRechargeB(iBean.getRechargeB());
		apiResult.setRecharge(iBean.getRecharge());
		apiResult.setCallbackUrl(rBean.getCallbackUrl());
		apiResult.setRechargeSecond(iBean.getRechargeSecond());
		apiResult.setRechargeSecondPercent(iBean.getRechargeSecondPercent());
		apiResult.setRechargeSecondOpen(iBean.getRechargeSecondOpen());
		return apiResult;
	}
	
	public static ExchApiResultBean parseExchBean2ExchApiResultBean(ExchangeBean exchBean) {
		logger.info("开始进行转换[ExchangeBean] -> [ExchApiResultBean]");
		ExchApiResultBean exchApiResultBean = new ExchApiResultBean();
		exchApiResultBean.setExchBeginTime(DateUtil.parseDateFromString(exchBean.getExchBeginTime(),"yyyy/MM/dd HH:mm",0));
		exchApiResultBean.setExchEndTime(DateUtil.parseDateFromString(exchBean.getExchEndTime(),"yyyy/MM/dd HH:mm",0));
		exchApiResultBean.setExchLimit(exchBean.getExchLimit());
		exchApiResultBean.setExchStandard1(exchBean.getExchStandard1());
		exchApiResultBean.setExchStandard2(exchBean.getExchStandard2());
		exchApiResultBean.setId(exchBean.getId()+"");
		exchApiResultBean.setGameName(exchBean.getGameName());
		exchApiResultBean.setGameId(exchBean.getGameId());
		exchApiResultBean.setPeriod1(exchBean.getPeriod1());
		exchApiResultBean.setPeriod2(exchBean.getPeriod2());
		logger.info("period1 "+exchApiResultBean.getPeriod1() +"   period2 "+exchApiResultBean.getPeriod2());
		logger.info("转换[ExchangeBean] -> [ExchApiResultBean]完成");
		return exchApiResultBean;
	}
	
}

