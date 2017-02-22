/**   
 * @文件名称: PlayerSearchBean.java
 * @类路径: com.dsky.baas.configservice.model
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2017年1月12日 下午2:39:11
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.model;

/**
 * @类功能说明：用于针对用户的查询
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2017年1月12日 下午2:39:11
 * @版本：V1.0
 */
public class PlayerSearchBean {
	//对应的游戏
	private String gameId;
	//用户id
	private String playerId;
	//用户在线时长
	private String onlineTime;
	//用户充值金额
	private String rechargeAmount;
	//游戏名称
	private String gameName;
	//用户等级
	private String level;
	
	public PlayerSearchBean(){};
	/**
	 *@类名：PlayerSearchBean.java
	 *@描述：{todo}
	 * @param gameId
	 * @param playerId
	 * @param onlineTime
	 * @param rechargeAmount
	 * @param gameName
	 * @param level
	 */
	public PlayerSearchBean(String gameId, String playerId, String onlineTime,
			String rechargeAmount, String gameName, String level) {
		super();
		this.gameId = gameId;
		this.playerId = playerId;
		this.onlineTime = onlineTime;
		this.rechargeAmount = rechargeAmount;
		this.gameName = gameName;
		this.level = level;
	}


	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public String getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public String getGameName() {
		return gameName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	@Override
	public String toString() {
		return "PlayerSearchBean [gameId=" + gameId + ", playerId=" + playerId
				+ ", onlineTime=" + onlineTime + ", rechargeAmount="
				+ rechargeAmount + ", gameName=" + gameName + ", level="
				+ level + "]";
	}
}
