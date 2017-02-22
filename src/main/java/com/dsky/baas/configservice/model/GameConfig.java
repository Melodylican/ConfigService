package com.dsky.baas.configservice.model;

import java.util.Map;
/**
 * @ClassName: GameConfig
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class GameConfig {
	private String id;
	private String gameName;
	private String gameId;
	private String beginTime;
	private String endTime;
	private Map<String,String> config_option;
	
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
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Map<String, String> getConfig_option() {
		return config_option;
	}
	public void setConfig_option(Map<String, String> config_option) {
		this.config_option = config_option;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
