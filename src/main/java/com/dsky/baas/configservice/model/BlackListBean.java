/**   
 * @文件名称: BlackListBean.java
 * @类路径: com.dsky.baas.configservice.model
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2017年1月6日 上午11:27:54
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.model;

import java.io.Serializable;

import com.dsky.baas.configservice.util.DateUtil;

/**
 * @类功能说明：用于黑名单用户
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2017年1月6日 上午11:27:54
 * @版本：V1.0
 */
public class BlackListBean implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO
	 * 2017年1月6日
	 */
	private static final long serialVersionUID = 1L;
	//黑名单对应的活动id
	private int id;
	//黑名单对应的游戏id
	private int gameId;
	//黑名单对应的游戏名称
	private String gameName;
	//黑名单对应的用户id
	private int playerId;
	//加入黑名单时间
	private int insertTime;
	//从黑名单移除的时间
	private int delTime;
	//过期时间 单位为s
	private int expireTime;
	
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
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getInsertTime() {
		return insertTime;
	}
	public String getInsertTime(int tag) {
		return DateUtil.parseDate(insertTime*1000L,"yyyy/MM/dd HH:mm:ss");
	}
	public void setInsertTime(int insertTime) {
		this.insertTime = insertTime;
	}
	public int getDelTime() {
		return delTime;
	}
	public void setDelTime(int delTime) {
		this.delTime = delTime;
	}
	public int getExpireTime() {
		return expireTime;
	}
	public String getExpireTime(int tag) {
		if(expireTime == 0)
			return "未设置过期时间";
		return DateUtil.parseDate(expireTime*1000L,"yyyy/MM/dd HH:mm:ss");
	}
	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}
	@Override
	public String toString() {
		return "BlackListBean [id=" + id + ", gameId=" + gameId + ", gameName="
				+ gameName + ", playerId=" + playerId + ", insertTime="
				+ insertTime + ", delTime=" + delTime + ", expireTime="
				+ expireTime + "]";
	}
}
