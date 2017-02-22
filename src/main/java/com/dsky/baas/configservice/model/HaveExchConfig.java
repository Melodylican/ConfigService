package com.dsky.baas.configservice.model;

import java.util.Map;
/**
 * @ClassName: GameConfig
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class HaveExchConfig {
	private Map<String,Object> data;
	private Map<String,Object> ext;
	private int code;
	private String msg;
	
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public Map<String, Object> getExt() {
		return ext;
	}
	public void setExt(Map<String, Object> ext) {
		this.ext = ext;
	}
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
	
}
