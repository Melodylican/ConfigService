package com.dsky.baas.configservice.model;
/**
 * @ClassName: GameConfigJSON
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class GameConfigJSON {
	private int code;
	private String msg;
	private GameConfig data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public GameConfig getData() {
		return data;
	}
	public void setData(GameConfig data) {
		this.data = data;
	}
}
