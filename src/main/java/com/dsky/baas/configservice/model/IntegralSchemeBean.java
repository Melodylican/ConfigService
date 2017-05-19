package com.dsky.baas.configservice.model;
/**
 * @ClassName: IntegralSchemeBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class IntegralSchemeBean {
	private int id;
	private int register; //新注册用户可获得积分
	private int level;//级数要求(被推广员B 奖励条件 第一阶段)
	private int levelSecond;//级数要求(被推广员B 奖励条件 第二阶段)
	private int levelThird;//级数要求(被推广员B 奖励条件 第三阶段)
	private int time;//在线时长要求(奖励条件 单位是秒)
	
	private int promoterA;//推广员首次次获得积分(B注册时奖励积分)
	
	private int promoterAFirst;//推广员第一次获得积分 (B达到第一阶段等级时奖励积分)
	private int promoterASecond;//推广员第二次获得积分 (B达到第二阶段等级时奖励积分)
	private int promoterAThird;//推广员第三次获得积分 (B达到第三阶段等级时奖励积分)
	
	private int promoterB;//被推广员成长可获得积分 (B达到第一阶段等级后奖励积分)
	private int promoterBSecond;//被推广员成长可获得积分 (B达到第二阶段等级后奖励积分)
	private int promoterBThird;//被推广员成长可获得积分 (B达到第三阶段等级后奖励积分)

	
	private int promoterATime;//成为推广员的在线时间要求
	private int promoterALevel;//成为推广员的等级要求
	private int redeemCode;//使用邀请码后是否可以获得兑换码奖励
	
	private int rechargeA; //B充值后A的奖励积分
	private int rechargeB;//B充值后B的奖励积分
	private int recharge;//进行充值奖励的充值金额下限
	
	private int weixinClickShare;//微信分享奖励积分
	private int weixinClickShareSuccess;//微信分享成功奖励积分
	
	private int qqClickShare;//qq点击分享奖励积分
	private int qqClickShareSuccess;//qq点击分享成功奖励积分
	
	private int weiboClickShare;//微博点击分享奖励积分
	private int weiboClickShareSuccess;//微博点击分享成功奖励积分
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRegister() {
		return register;
	}
	public void setRegister(int register) {
		this.register = register;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
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
	public int getRedeemCode() {
		return redeemCode;
	}
	public void setRedeemCode(int redeemCode) {
		this.redeemCode = redeemCode;
	}
	
	public int getLevelSecond() {
		return levelSecond;
	}
	public void setLevelSecond(int levelSecond) {
		this.levelSecond = levelSecond;
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
	public int getLevelThird() {
		return levelThird;
	}
	public void setLevelThird(int levelThird) {
		this.levelThird = levelThird;
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
	public int getWeixinClickShare() {
		return weixinClickShare;
	}
	public void setWeixinClickShare(int weixinClickShare) {
		this.weixinClickShare = weixinClickShare;
	}
	public int getWeixinClickShareSuccess() {
		return weixinClickShareSuccess;
	}
	public void setWeixinClickShareSuccess(int weixinClickShareSuccess) {
		this.weixinClickShareSuccess = weixinClickShareSuccess;
	}
	public int getQqClickShare() {
		return qqClickShare;
	}
	public void setQqClickShare(int qqClickShare) {
		this.qqClickShare = qqClickShare;
	}
	public int getQqClickShareSuccess() {
		return qqClickShareSuccess;
	}
	public void setQqClickShareSuccess(int qqClickShareSuccess) {
		this.qqClickShareSuccess = qqClickShareSuccess;
	}
	public int getWeiboClickShare() {
		return weiboClickShare;
	}
	public void setWeiboClickShare(int weiboClickShare) {
		this.weiboClickShare = weiboClickShare;
	}
	public int getWeiboClickShareSuccess() {
		return weiboClickShareSuccess;
	}
	public void setWeiboClickShareSuccess(int weiboClickShareSuccess) {
		this.weiboClickShareSuccess = weiboClickShareSuccess;
	}
	@Override
	public String toString() {
		return "IntegralSchemeBean [id=" + id + ", register=" + register
				+ ", level=" + level + ", levelSecond=" + levelSecond
				+ ", levelThird=" + levelThird + ", time=" + time
				+ ", promoterA=" + promoterA + ", promoterAFirst="
				+ promoterAFirst + ", promoterASecond=" + promoterASecond
				+ ", promoterAThird=" + promoterAThird + ", promoterB="
				+ promoterB + ", promoterBSecond=" + promoterBSecond
				+ ", promoterBThird=" + promoterBThird + ", promoterATime="
				+ promoterATime + ", promoterALevel=" + promoterALevel
				+ ", redeemCode=" + redeemCode + ", rechargeA=" + rechargeA
				+ ", rechargeB=" + rechargeB + ", recharge=" + recharge
				+ ", weixinClickShare=" + weixinClickShare
				+ ", weixinClickShareSuccess=" + weixinClickShareSuccess
				+ ", qqClickShare=" + qqClickShare + ", qqClickShareSuccess="
				+ qqClickShareSuccess + ", weiboClickShare=" + weiboClickShare
				+ ", weiboClickShareSuccess=" + weiboClickShareSuccess + "]";
	}
}
