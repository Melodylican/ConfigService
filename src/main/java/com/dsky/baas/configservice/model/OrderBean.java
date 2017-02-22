package com.dsky.baas.configservice.model;
/**
 * @ClassName: OrderBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class OrderBean {
	//表自增id
    private Integer id;
    //订单编号
    private String orderId;
    //用户id
    private Integer playerId;
    //游戏id
    private Integer gameId;
    //游戏名称
    private String gameName;
    //配置兑换计划
    private Integer planId;
    //游戏等级
    private Integer level;
    //在线时长
    private Integer onlineTime;
    //已有积分
    private Integer hasPoints;
    //可兑换积分
    private Integer requestExchangePoints;
    //支付账号
    private Integer amount;
    //支付方式
    private Integer payMethod;
    //支付相关信息
    private String payInfo;
    //用户备注
    private String userMemo;
    //审核状态
    private String status;
    //订单创建的时间
    private String orderCreatedDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(Integer onlineTime) {
		this.onlineTime = onlineTime;
	}
	public Integer getHasPoints() {
		return hasPoints;
	}
	public void setHasPoints(Integer hasPoints) {
		this.hasPoints = hasPoints;
	}
	public Integer getRequestExchangePoints() {
		return requestExchangePoints;
	}
	public void setRequestExchangePoints(Integer requestExchangePoints) {
		this.requestExchangePoints = requestExchangePoints;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	public String getPayInfo() {
		return payInfo;
	}
	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
	public String getUserMemo() {
		return userMemo;
	}
	public void setUserMemo(String userMemo) {
		this.userMemo = userMemo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderCreatedDate() {
		return orderCreatedDate;
	}
	public void setOrderCreatedDate(String orderCreatedDate) {
		this.orderCreatedDate = orderCreatedDate;
	}
	
}
