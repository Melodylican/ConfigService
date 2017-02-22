package com.dsky.baas.configservice.statistics.dao;

import java.util.List;
import java.util.Map;

import com.dsky.baas.configservice.model.ActInfoBean;

/**
 * 
 * @类功能说明：act_info 数据统计表功能接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年12月12日 下午4:27:31
 * @版本：V1.0
 */
public interface ActInfoMapper {
	List<ActInfoBean> selectActInfo(Map<String,Object> map);

}