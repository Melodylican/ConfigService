/**   
 * @文件名称: IPWitheListBean.java
 * @类路径: com.dsky.baas.configservice.model
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2016年12月14日 下午7:03:33
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.model;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年12月14日 下午7:03:33
 * @版本：V1.0
 */
public class IPWitheListBean {
	private String ipWhiteList;

	public String getIpWhiteList() {
		return ipWhiteList;
	}
	@Autowired
	public void setIpWhiteList(String ipWhiteList) {
		this.ipWhiteList = ipWhiteList;
	}

	@Override
	public String toString() {
		return "IPWitheListBean [ipWhiteList=" + ipWhiteList + "]";
	}
}
