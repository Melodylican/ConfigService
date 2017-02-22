/**   
 * @文件名称: StatisticsSeries.java
 * @类路径: com.dsky.baas.configservice.model
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2016年12月13日 下午4:02:18
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.model;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年12月13日 下午4:02:18
 * @版本：V1.0
 */
public class Statistics {
	private String categories;
	private String series;
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	@Override
	public String toString() {
		return "Statistics [categories=" + categories + ", series=" + series
				+ "]";
	}
}
