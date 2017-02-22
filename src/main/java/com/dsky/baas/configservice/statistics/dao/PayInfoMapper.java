package com.dsky.baas.configservice.statistics.dao;

import java.util.List;
import java.util.Map;

import com.dsky.baas.configservice.model.PayInfoBean;

/**
 * 
 * @类功能说明：用于操作与pay_info数据统计表相关的呃方法
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年12月12日 下午4:27:16
 * @版本：V1.0
 */
public interface PayInfoMapper {
	List<PayInfoBean> selectPayInfo(Map<String,Object> map);

}