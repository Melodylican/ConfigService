/**   
 * @文件名称: StatisticsSeriesBean.java
 * @类路径: com.dsky.baas.configservice.model
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2016年12月13日 下午4:05:25
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.model;

import java.util.Arrays;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年12月13日 下午4:05:25
 * @版本：V1.0
 */
public class StatisticsSeriesBean {
	private String name;
	private Object[] data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object[] getData() {
		return data;
	}
	public void setData(Object[] data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "StatisticsSeriesBean [name=" + name + ", data="
				+ Arrays.toString(data) + "]";
	}
}
