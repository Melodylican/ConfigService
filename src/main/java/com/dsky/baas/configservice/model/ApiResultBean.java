package com.dsky.baas.configservice.model;

import java.io.Serializable;
/**
 * @ClassName: ApiResultBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class ApiResultBean implements Serializable{

	    private static final long serialVersionUID = 7259584042890356969L;
		//访问状态码
		private Integer code ;
		//访问附加信息
		private String msg ;
		//活动id
		private String id;
		//游戏id
		private String gameId;
		//游戏名称
		private String gameName;
		//活动地区
		private String location;
	    //活动开始时间
		private String beginTime;
		//活动结束时间
		private String endTime;
		//活动预热时间
		private String preheatingTime;
		//兑换码h5 url
		private String exchH5Url;
		//当前活动是开启还是暂停 0暂停 1开启
		private int state;
		//单台设备上可生成邀请码个数
		private int deviceCount;
		//推广员B获得成长积分时间要求
		private int time;
		//推官员B 获得成长积分等级要求 第一阶段
		private int level;
		//级数要求(推广员B 奖励条件 第二阶段)  //TODO 新增属性
		private int levelSecond;
		//级数要求(推广员B 奖励条件 第三阶段) //TODO  新增属性
		private int levelThird;
		//一个邀请码使用次数限制
		private int recommandCount;
		//h5 分享连接地址
		private String h5Url;
		//分享小图标地址
		private String imgUrl;
		//分享标题
		private String title;
		//分享描述
		private String redeemDesc;
		//推广员首次次获得积分(B注册时奖励积分)
		private int promoterA;
		//推广员第一次获得积分 (B达到第一阶段等级时奖励积分) //TODO 新增属性
		private int promoterAFirst;
		//推广员第二次获得积分 (B达到第二阶段等级时奖励积分) //TODO 新增属性
		private int promoterASecond;
		//推广员第三次获得积分 (B达到第三阶段等级时奖励积分) //TODO 新增属性
		private int promoterAThird;
		
		//被推广员成长可获得积分 (B达到第一阶段等级后奖励积分)   
		private int promoterB;
		//被推广员成长可获得积分 (B达到第二阶段等级后奖励积分) 
		private int promoterBSecond;
		//被推广员成长可获得积分 (B达到第三阶段等级后奖励积分) //TODO 新增属性
		private int promoterBThird;
		//新注册用户可获得积分
		private int register;
		//成为推广员的在线时间要求
		private int promoterATime;
		//成为推广员的等级要求
		private int promoterALevel;
		//可分享的方式1：代表微信,2:代表朋友圈,4:代表QQ,8:代表微博
		private String shareMethod;
		//活动预热的h5 url
		private String preheatingUrl;
		//活动结束时的说明
		private String endDesc;
		//使用邀请码后是否可以获得兑换码奖励 1：有兑换码奖励  0：无兑换码奖励
		private int redeemCode;
		//是否开启现金兑换功能 1：为开启 0：为关闭    //TODO 新增属性
		private int exchCash;
		//单台设备可参与活动的次数限制    //TODO 新增属性
		private int deviceLimit;
		//B充值后A的奖励积分                       //TODO 新增属性
		private int rechargeA; 
		//B充值后B的奖励积分                     //TODO 新增属性
		private int rechargeB;
		//给予充值奖励的充值金额下限                    //TODO 新增属性
		private int recharge;
		//新增游戏回调通知好友的配置
		private String callbackUrl; //TODO 新增属性
		//充值奖励第二个下限，超过第二个下限按充值金额按比例奖励积分
		private int rechargeSecond; 
		//充值奖励第二个下限，超过第二个下限按充值金额按比例奖励积分 百分比
		private double rechargeSecondPercent;//TODO 新增属性2017-07-06
		//充值奖励第二下限是否对所有玩家开放 （1：对所有玩家开放，0：只对邀请过玩家的用户开放）
		private int rechargeSecondOpen;//TODO 新增属性 2017-07-07
		
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getGameId() {
			return gameId;
		}
		public void setGameId(String gameId) {
			this.gameId = gameId;
		}
		public String getGameName() {
			return gameName;
		}
		public void setGameName(String gameName) {
			this.gameName = gameName;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}

		public String getBeginTime() {
			return beginTime;
		}
		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public String getPreheatingTime() {
			return preheatingTime;
		}
		public void setPreheatingTime(String preheatingTime) {
			this.preheatingTime = preheatingTime;
		}
		public String getExchH5Url() {
			return exchH5Url;
		}
		public void setExchH5Url(String exchH5Url) {
			this.exchH5Url = exchH5Url;
		}
		public int getDeviceCount() {
			return deviceCount;
		}
		public void setDeviceCount(int deviceCount) {
			this.deviceCount = deviceCount;
		}
		public int getTime() {
			return time;
		}
		public void setTime(int time) {
			this.time = time;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		public int getRecommandCount() {
			return recommandCount;
		}
		public void setRecommandCount(int recommandCount) {
			this.recommandCount = recommandCount;
		}
		public int getPromoterA() {
			return promoterA;
		}
		public void setPromoterA(int promoterA) {
			this.promoterA = promoterA;
		}
		public int getPromoterB() {
			return promoterB;
		}
		public void setPromoterB(int promoterB) {
			this.promoterB = promoterB;
		}
		public int getRegister() {
			return register;
		}
		public void setRegister(int register) {
			this.register = register;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getH5Url() {
			return h5Url;
		}
		public void setH5Url(String h5Url) {
			this.h5Url = h5Url;
		}
		public String getImgUrl() {
			return imgUrl;
		}
		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getRedeemDesc() {
			return redeemDesc;
		}
		public void setRedeemDesc(String redeemDesc) {
			this.redeemDesc = redeemDesc;
		}
		public int getPromoterASecond() {
			return promoterASecond;
		}
		public void setPromoterASecond(int promoterASecond) {
			this.promoterASecond = promoterASecond;
		}
		public int getPromoterATime() {
			return promoterATime;
		}
		public void setPromoterATime(int promoterATime) {
			this.promoterATime = promoterATime;
		}
		public int getPromoterALevel() {
			return promoterALevel;
		}
		public void setPromoterALevel(int promoterALevel) {
			this.promoterALevel = promoterALevel;
		}
		public String getShareMethod() {
			return shareMethod;
		}
		public void setShareMethod(String shareMethod) {
			this.shareMethod = shareMethod;
		}
		public String getPreheatingUrl() {
			return preheatingUrl;
		}
		public void setPreheatingUrl(String preheatingUrl) {
			this.preheatingUrl = preheatingUrl;
		}
		public String getEndDesc() {
			return endDesc;
		}
		public void setEndDesc(String endDesc) {
			this.endDesc = endDesc;
		}
		public int getRedeemCode() {
			return redeemCode;
		}
		public void setRedeemCode(int redeemCode) {
			this.redeemCode = redeemCode;
		}
		public int getExchCash() {
			return exchCash;
		}
		public void setExchCash(int exchCash) {
			this.exchCash = exchCash;
		}
		public int getDeviceLimit() {
			return deviceLimit;
		}
		public void setDeviceLimit(int deviceLimit) {
			this.deviceLimit = deviceLimit;
		}
		public int getLevelSecond() {
			return levelSecond;
		}
		public void setLevelSecond(int levelSecond) {
			this.levelSecond = levelSecond;
		}
		public int getLevelThird() {
			return levelThird;
		}
		public void setLevelThird(int levelThird) {
			this.levelThird = levelThird;
		}
		public int getPromoterAFirst() {
			return promoterAFirst;
		}
		public void setPromoterAFirst(int promoterAFirst) {
			this.promoterAFirst = promoterAFirst;
		}
		public int getPromoterAThird() {
			return promoterAThird;
		}
		public void setPromoterAThird(int promoterAThird) {
			this.promoterAThird = promoterAThird;
		}
		public int getPromoterBSecond() {
			return promoterBSecond;
		}
		public void setPromoterBSecond(int promoterBSecond) {
			this.promoterBSecond = promoterBSecond;
		}
		public int getPromoterBThird() {
			return promoterBThird;
		}
		public void setPromoterBThird(int promoterBThird) {
			this.promoterBThird = promoterBThird;
		}
		public int getRechargeA() {
			return rechargeA;
		}
		public void setRechargeA(int rechargeA) {
			this.rechargeA = rechargeA;
		}
		public int getRechargeB() {
			return rechargeB;
		}
		public void setRechargeB(int rechargeB) {
			this.rechargeB = rechargeB;
		}
		public int getRecharge() {
			return recharge;
		}
		public void setRecharge(int recharge) {
			this.recharge = recharge;
		}
		public String getCallbackUrl() {
			return callbackUrl;
		}
		public void setCallbackUrl(String callbackUrl) {
			this.callbackUrl = callbackUrl;
		}
		public int getRechargeSecond() {
			return rechargeSecond;
		}
		public void setRechargeSecond(int rechargeSecond) {
			this.rechargeSecond = rechargeSecond;
		}
		public double getRechargeSecondPercent() {
			return rechargeSecondPercent;
		}
		public void setRechargeSecondPercent(double rechargeSecondPercent) {
			this.rechargeSecondPercent = rechargeSecondPercent;
		}
		public int getRechargeSecondOpen() {
			return rechargeSecondOpen;
		}
		public void setRechargeSecondOpen(int rechargeSecondOpen) {
			this.rechargeSecondOpen = rechargeSecondOpen;
		}
		
}
