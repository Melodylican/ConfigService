package com.dsky.baas.configservice.model;

import java.io.Serializable;

/**
 * @ClassName: PromoterBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class PromoterBean implements Serializable{

	private static final long serialVersionUID = 1L;
	//活动在数据库中的id
	private String id;
	//游戏id
	private String gameId;
	//游戏名
	private String gameName;
	//活动对应的区域
	private String location;
	//活动对应的游戏的等级时间要求
	private String gameType;
	//活动开始的时间
	private String beginTime;
	//活动结束时间
	private String endTime;
	//活动预热时间
	private String preheatingTime;
	//兑换码h5 url
	private String exchH5Url;
	//活动的状态 1开启 0关闭
	private int state;
	//活动的创建者
	private String createdBy;
	//活动预热的h5 url
	private String preheatingUrl;
	//活动结束时的说明
	private String endDesc;
	
	//紧急测试按钮状态 1为未开启 0为开启紧急测试按钮
	private String urgentState;
	//是否开启现金兑换功能 1：为开启 0：为关闭
	private int exchCash;
	
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
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public String getExchH5Url() {
		return exchH5Url;
	}
	public void setExchH5Url(String exchH5Url) {
		this.exchH5Url = exchH5Url;
	}
	public String getUrgentState() {
		return urgentState;
	}
	public void setUrgentState(String urgentState) {
		this.urgentState = urgentState;
	}
	public int getExchCash() {
		return exchCash;
	}
	public void setExchCash(int exchCash) {
		this.exchCash = exchCash;
	}
	@Override
	public String toString() {
		return "PromoterBean [id=" + id + ", gameId=" + gameId + ", gameName="
				+ gameName + ", location=" + location + ", gameType="
				+ gameType + ", beginTime=" + beginTime + ", endTime="
				+ endTime + ", preheatingTime=" + preheatingTime
				+ ", exchH5Url=" + exchH5Url + ", state=" + state
				+ ", createdBy=" + createdBy + ", preheatingUrl="
				+ preheatingUrl + ", endDesc=" + endDesc + ", urgentState="
				+ urgentState + ", exchCash=" + exchCash + "]";
	}
}
