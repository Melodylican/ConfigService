/**   
 * @文件名称: ActInfoBean.java
 * @类路径: com.dsky.baas.configservice.model
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2016年12月13日 上午10:00:02
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.model;

/**
 * @类功能说明：对应于数据统计表act_info
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年12月13日 上午10:00:02
 * @版本：V1.0
 */
public class ActInfoBean {
	private int id;
	private int gameId;
	private int actId;
	private String date;
	//活动入口点击数
	private int clickActivity;
	//分享次数
	private int shareCode;
	//下载链接点击数
	private int clickCode;
	//微信好友渠道分享次数
	private int weiXinFriends;
	//微信朋友圈分享次数
	private int weiXinMoments;
	//qq分享次数
	private int qq;
	//微博分享次数
	private int weibo;
	//活动每天邀请的人数
	private int invitedPeople;
	//数据写入时间
	private String insertTime;
	//数据更新时间
	private String updateTime;
	//分享成功次数
	private int shareSuccess;
	
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
	public int getClickActivity() {
		return clickActivity;
	}
	public void setClickActivity(int clickActivity) {
		this.clickActivity = clickActivity;
	}
	public int getShareCode() {
		return shareCode;
	}
	public void setShareCode(int shareCode) {
		this.shareCode = shareCode;
	}
	public int getClickCode() {
		return clickCode;
	}
	public void setClickCode(int clickCode) {
		this.clickCode = clickCode;
	}
	public int getWeiXinFriends() {
		return weiXinFriends;
	}
	public void setWeiXinFriends(int weiXinFriends) {
		this.weiXinFriends = weiXinFriends;
	}
	public int getWeiXinMoments() {
		return weiXinMoments;
	}
	public void setWeiXinMoments(int weiXinMoments) {
		this.weiXinMoments = weiXinMoments;
	}
	public int getQq() {
		return qq;
	}
	public void setQq(int qq) {
		this.qq = qq;
	}
	public int getWeibo() {
		return weibo;
	}
	public void setWeibo(int weibo) {
		this.weibo = weibo;
	}
	public int getInvitedPeople() {
		return invitedPeople;
	}
	public void setInvitedPeople(int invitedPeople) {
		this.invitedPeople = invitedPeople;
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
	
	public int getShareSuccess() {
		return shareSuccess;
	}
	public void setShareSuccess(int shareSuccess) {
		this.shareSuccess = shareSuccess;
	}
	@Override
	public String toString() {
		return "ActInfoBean [id=" + id + ", gameId=" + gameId + ", actId="
				+ actId + ", date=" + date + ", clickActivity=" + clickActivity
				+ ", shareCode=" + shareCode + ", clickCode=" + clickCode
				+ ", weiXinFriends=" + weiXinFriends + ", weiXinMoments="
				+ weiXinMoments + ", qq=" + qq + ", weibo=" + weibo
				+ ", invitedPeople=" + invitedPeople + ", insertTime="
				+ insertTime + ", updateTime=" + updateTime + ", shareSuccess="
				+ shareSuccess + "]";
	}
	
}
