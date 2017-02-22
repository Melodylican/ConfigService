package com.dsky.baas.configservice.util;
/**
 * @ClassName: ApiResultErrorObject
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class ApiResultErrorObject {
	private Integer code = 0;
	private String msg = "";


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

}
