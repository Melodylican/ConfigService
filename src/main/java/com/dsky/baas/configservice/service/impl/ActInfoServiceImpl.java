/**   
 * @文件名称: ActInfoServiceImpl.java
 * @类路径: com.dsky.baas.configservice.service.impl
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2016年12月13日 下午2:32:48
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.baas.configservice.model.ActInfoBean;
import com.dsky.baas.configservice.service.IActInfoService;
import com.dsky.baas.configservice.statistics.dao.ActInfoMapper;

/**
 * @类功能说明：用于操作act_info数据统计表相关的方法
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年12月13日 下午2:32:48
 * @版本：V1.0
 */
@Service("actInfoService")
public class ActInfoServiceImpl implements IActInfoService {
	@Autowired
	private ActInfoMapper actInfoMapper;
	private static final Logger logger = Logger.getLogger(ActInfoServiceImpl.class);

	@Override
	public List<ActInfoBean> selectActInfo(String gameId, String actId,String beginTime,String endTime) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("gameId", gameId);
		map.put("actId", actId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return actInfoMapper.selectActInfo(map);
	}

}
