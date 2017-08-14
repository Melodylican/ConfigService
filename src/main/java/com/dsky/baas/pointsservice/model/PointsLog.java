package com.dsky.baas.pointsservice.model;

import java.io.Serializable;

public class PointsLog  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4551034869633552117L;
	

	//表后缀
	private String tablePostfix;
    
	//log表id
    private Integer id;

    //推广员账号
    private Long playerId;

   //游戏id
    private Integer gameId;

    //活动id
    private Integer actId;

    //被推广员账号
    private Long fromPlayerId;

 //事件名称
    private String eventName;

    //活动参数
    private String eventArgs;

    //加之前多少分
    private Integer afterNum;

    //实际加多少分
    private Integer addNum;

    //应该加多少分
    private Integer shouldNum;

    //邀请码
    private String invitedCode;

    //备注
    private String memo;

    //log创建时间
    private Integer createTime;

    //分页起始行
    private Integer startRow ;
    //分页结束行
    private Integer   endRow;
    
    private Integer channelType ;

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.id
     *
     * @param id the value for points_log_tpl.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.player_id
     *
     * @return the value of points_log_tpl.player_id
     *
     * @mbggenerated
     */
    public Long getPlayerId() {
        return playerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.player_id
     *
     * @param playerId the value for points_log_tpl.player_id
     *
     * @mbggenerated
     */
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.game_id
     *
     * @return the value of points_log_tpl.game_id
     *
     * @mbggenerated
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.game_id
     *
     * @param gameId the value for points_log_tpl.game_id
     *
     * @mbggenerated
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.act_id
     *
     * @return the value of points_log_tpl.act_id
     *
     * @mbggenerated
     */
    public Integer getActId() {
        return actId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.act_id
     *
     * @param actId the value for points_log_tpl.act_id
     *
     * @mbggenerated
     */
    public void setActId(Integer actId) {
        this.actId = actId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.from_player_id
     *
     * @return the value of points_log_tpl.from_player_id
     *
     * @mbggenerated
     */
    public Long getFromPlayerId() {
        return fromPlayerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.from_player_id
     *
     * @param fromPlayerId the value for points_log_tpl.from_player_id
     *
     * @mbggenerated
     */
    public void setFromPlayerId(Long fromPlayerId) {
        this.fromPlayerId = fromPlayerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.event_name
     *
     * @return the value of points_log_tpl.event_name
     *
     * @mbggenerated
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.event_name
     *
     * @param eventName the value for points_log_tpl.event_name
     *
     * @mbggenerated
     */
    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.event_args
     *
     * @return the value of points_log_tpl.event_args
     *
     * @mbggenerated
     */
    public String getEventArgs() {
        return eventArgs;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.event_args
     *
     * @param eventArgs the value for points_log_tpl.event_args
     *
     * @mbggenerated
     */
    public void setEventArgs(String eventArgs) {
        this.eventArgs = eventArgs == null ? null : eventArgs.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.after_num
     *
     * @return the value of points_log_tpl.after_num
     *
     * @mbggenerated
     */
    public Integer getAfterNum() {
        return afterNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.after_num
     *
     * @param afterNum the value for points_log_tpl.after_num
     *
     * @mbggenerated
     */
    public void setAfterNum(Integer afterNum) {
        this.afterNum = afterNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.add_num
     *
     * @return the value of points_log_tpl.add_num
     *
     * @mbggenerated
     */
    public Integer getAddNum() {
        return addNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.add_num
     *
     * @param addNum the value for points_log_tpl.add_num
     *
     * @mbggenerated
     */
    public void setAddNum(Integer addNum) {
        this.addNum = addNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.should_num
     *
     * @return the value of points_log_tpl.should_num
     *
     * @mbggenerated
     */
    public Integer getShouldNum() {
        return shouldNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.should_num
     *
     * @param shouldNum the value for points_log_tpl.should_num
     *
     * @mbggenerated
     */
    public void setShouldNum(Integer shouldNum) {
        this.shouldNum = shouldNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.invited_code
     *
     * @return the value of points_log_tpl.invited_code
     *
     * @mbggenerated
     */
    public String getInvitedCode() {
        return invitedCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.invited_code
     *
     * @param invitedCode the value for points_log_tpl.invited_code
     *
     * @mbggenerated
     */
    public void setInvitedCode(String invitedCode) {
        this.invitedCode = invitedCode == null ? null : invitedCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.memo
     *
     * @return the value of points_log_tpl.memo
     *
     * @mbggenerated
     */
    public String getMemo() {
        return memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.memo
     *
     * @param memo the value for points_log_tpl.memo
     *
     * @mbggenerated
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column points_log_tpl.create_time
     *
     * @return the value of points_log_tpl.create_time
     *
     * @mbggenerated
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column points_log_tpl.create_time
     *
     * @param createTime the value for points_log_tpl.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
    public String getTablePostfix() {
		return tablePostfix;
	}

	public void setTablePostfix(String tablePostfix) {
		this.tablePostfix = tablePostfix;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}
	
}