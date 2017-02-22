package com.dsky.baas.pointsservice.model;

import java.io.Serializable;

public class PointsLog implements Serializable{
	private static final long serialVersionUID = -4551034869633552117L;

	//log的表时间后缀
	private String tablePostfix;
    
	//log表id
    private Integer id;

    //推广员账号
    private Integer playerId;

    //游戏id
    private Integer gameId;

    //活动id
    private Integer actId;

    //被推广员账号
    private Integer fromPlayerId;

    //事件名称
    private String eventName;

    //活动参数
    private String eventArgs;

    //加之前多少分
    private Integer afterNum;

    //推广员获得的积分
    private Integer addNum;

    //应该加多少分
    private Integer shouldNum;

    //邀请码
    private String invitedCode;

    //备注
    private String memo;

    //log创建时间
    private Integer createTime;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
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


    public Integer getActId() {
        return actId;
    }


    public void setActId(Integer actId) {
        this.actId = actId;
    }


    public Integer getFromPlayerId() {
        return fromPlayerId;
    }


    public void setFromPlayerId(Integer fromPlayerId) {
        this.fromPlayerId = fromPlayerId;
    }


    public String getEventName() {
        return eventName;
    }


    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }


    public String getEventArgs() {
        return eventArgs;
    }


    public void setEventArgs(String eventArgs) {
        this.eventArgs = eventArgs == null ? null : eventArgs.trim();
    }


    public Integer getAfterNum() {
        return afterNum;
    }


    public void setAfterNum(Integer afterNum) {
        this.afterNum = afterNum;
    }


    public Integer getAddNum() {
        return addNum;
    }


    public void setAddNum(Integer addNum) {
        this.addNum = addNum;
    }


    public Integer getShouldNum() {
        return shouldNum;
    }

    public void setShouldNum(Integer shouldNum) {
        this.shouldNum = shouldNum;
    }

    public String getInvitedCode() {
        return invitedCode;
    }

    public void setInvitedCode(String invitedCode) {
        this.invitedCode = invitedCode == null ? null : invitedCode.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
    public String getTablePostfix() {
		return tablePostfix;
	}

	public void setTablePostfix(String tablePostfix) {
		this.tablePostfix = tablePostfix;
	}

}