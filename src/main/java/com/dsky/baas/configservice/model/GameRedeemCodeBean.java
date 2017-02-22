package com.dsky.baas.configservice.model;

import com.dsky.baas.configservice.util.DateUtil;

public class GameRedeemCodeBean {
	//id
	private int id;
	//活动id
	private int actId;
	//游戏id
	private int gameId;
	//兑换码
	private String code;
	//积分
	private int score;
	//创建时间
	private int createTime;
	//删除时间
	private int delTime;
	//更新时间
	private int updateTime;
	//状态
	private int status;
	//使用者id
	private int playerId;
	public int getActId() {
		return actId;
	}
	public void setActId(int actId) {
		this.actId = actId;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getCreateTime() {
		return createTime;
	}
	public String getCreateTime(int i) {
		return DateUtil.parseDate(createTime*1000L,"yyyy/MM/dd HH:mm");
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	public int getDelTime() {
		return delTime;
	}
	public void setDelTime(int delTime) {
		this.delTime = delTime;
	}
	public int getUpdateTime() {
		return updateTime;
	}
	public String getUpdateTime(int i) {
		return DateUtil.parseDate(updateTime*1000L,"yyyy/MM/dd HH:mm");
	}
	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	@Override
	public String toString() {
		return "GameRedeemCodeBean [id=" + id + ", actId=" + actId
				+ ", gameId=" + gameId + ", code=" + code + ", score=" + score
				+ ", createTime=" + createTime + ", delTime=" + delTime
				+ ", updateTime=" + updateTime + ", status=" + status
				+ ", playerId=" + playerId + "]";
	}
}
