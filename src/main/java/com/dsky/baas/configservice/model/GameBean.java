package com.dsky.baas.configservice.model;

public class GameBean {
	//数据库中该条记录对应的id号
	private int id;
	//游戏名
	private String gameName;
	//游戏id
	private String gameId;
	//游戏所属部门
	private String department;
	//记录创建时间
	private String createdTime;
	//游戏简述
	private String description;
    //游戏是否为开启状态
	private int state;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
