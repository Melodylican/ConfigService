/**   
 * @文件名称: PayInfoBean.java
 * @类路径: com.dsky.baas.configservice.model
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2016年12月13日 上午10:27:43
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.model;

/**
 * @类功能说明：与数据统计表pay_info对应
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年12月13日 上午10:27:43
 * @版本：V1.0
 */
public class PayInfoBean {
	private int id;
	private int gameId;
	private int actId;
	private String date;
	//活动开始到现在已邀请的人数
	private int invitedPeople;
	//活动开始到现在已付款的人数
	private int payingPeople;
	//活动开始到现在已付款人数
	private float payingAmount;
	//数据写入时间
	private String insertTime;
	//数据更新时间
	private String updateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getActId() {
		return actId;
	}
	public void setActId(int actId) {
		this.actId = actId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getInvitedPeople() {
		return invitedPeople;
	}
	public void setInvitedPeople(int invitedPeople) {
		this.invitedPeople = invitedPeople;
	}
	public int getPayingPeople() {
		return payingPeople;
	}
	public void setPayingPeople(int payingPeople) {
		this.payingPeople = payingPeople;
	}
	public float getPayingAmount() {
		return payingAmount;
	}
	public void setPayingAmount(float payingAmount) {
		this.payingAmount = payingAmount;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "PayInfoBean [id=" + id + ", gameId=" + gameId + ", actId="
				+ actId + ", date=" + date + ", invitedPeople=" + invitedPeople
				+ ", payingPeople=" + payingPeople + ", payingAmount="
				+ payingAmount + ", insertTime=" + insertTime + ", updateTime="
				+ updateTime + "]";
	}
	
}
