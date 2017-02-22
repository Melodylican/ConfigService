package com.dsky.baas.configservice.model;
/**
 * @ClassName: GameTypeBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class GameTypeBean {
	private int id ;
    private String gameName;
    private String department;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
}
