package com.dsky.baas.configservice.model;

import java.io.Serializable;

public class ExchangeBean implements Serializable{
	//兑换配置id号
	private int id;
	//游戏名称
	private String gameName;
	//游戏Id
	private String gameId;
	//兑换开始时间
	private String exchBeginTime;
	//兑换结束时间
	private String exchEndTime;
	//兑换积分上限
	private int exchLimit;
	//兑换周期（每周、每天）
	private int period1;
	//兑换周期(星期一,星期二,...)
	private int period2;
	//兑换标准(配置多少积分兑换多少元,这个参数用于配置积分)
	private int exchStandard1;
	//兑换标准(配置多少积分兑换多少元,这个参数用于配置元)
	private int exchStandard2;
	//创建者
	private String createdBy;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public int getPeriod1() {
		return period1;
	}
	public void setPeriod1(int period1) {
		this.period1 = period1;
	}
	public int getPeriod2() {
		return period2;
	}
	public void setPeriod2(int period2) {
		this.period2 = period2;
	}
	public int getExchStandard1() {
		return exchStandard1;
	}
	public void setExchStandard1(int exchStandard1) {
		this.exchStandard1 = exchStandard1;
	}
	public int getExchStandard2() {
		return exchStandard2;
	}
	public void setExchStandard2(int exchStandard2) {
		this.exchStandard2 = exchStandard2;
	}
	public String getExchBeginTime() {
		return exchBeginTime;
	}
	public void setExchBeginTime(String exchBeginTime) {
		this.exchBeginTime = exchBeginTime;
	}
	public String getExchEndTime() {
		return exchEndTime;
	}
	public void setExchEndTime(String exchEndTime) {
		this.exchEndTime = exchEndTime;
	}
	public int getExchLimit() {
		return exchLimit;
	}
	public void setExchLimit(int exchLimit) {
		this.exchLimit = exchLimit;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public String toString() {
		return "ExchangeBean [id=" + id + ", gameName=" + gameName
				+ ", gameId=" + gameId + ", exchBeginTime=" + exchBeginTime
				+ ", exchEndTime=" + exchEndTime + ", exchLimit=" + exchLimit
				+ ", period1=" + period1 + ", period2=" + period2
				+ ", exchStandard1=" + exchStandard1 + ", exchStandard2="
				+ exchStandard2 + ", createdBy=" + createdBy + "]";
	}
	
}
