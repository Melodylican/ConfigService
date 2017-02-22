package com.dsky.baas.configservice.util;

import java.util.HashMap;
/**
 * @ClassName: ApiResultObject
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class ApiResultObject {
	private Integer code = 0;
	private String msg = "";
	private Object data;
	private Object ext;
	public ApiResultObject(){
		data = new HashMap<String,Object>();
		ext = new HashMap<String,Object>();
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getExt() {
		return ext;
	}
	public void setExt(Object ext) {
		this.ext = ext;
	}

}
