package com.dsky.baas.configservice.model;

public class DetailBean {
    //被推广员账号
    private Integer fromPlayerId;
    //推广员获得的积分
    private Integer addNum;
    //邀请码
    private String invitedCode;
    //推广员账号
    private Integer playerId;
    //时间
    private String createTime;
    //游戏Id
    private int gameId;
    //备注
    private String memo;

	public Integer getFromPlayerId() {
		return fromPlayerId;
	}
	public void setFromPlayerId(Integer fromPlayerId) {
		this.fromPlayerId = fromPlayerId;
	}
	public Integer getAddNum() {
		return addNum;
	}
	public void setAddNum(Integer addNum) {
		this.addNum = addNum;
	}
	public String getInvitedCode() {
		return invitedCode;
	}
	public void setInvitedCode(String invitedCode) {
		this.invitedCode = invitedCode;
	}
	public Integer getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
