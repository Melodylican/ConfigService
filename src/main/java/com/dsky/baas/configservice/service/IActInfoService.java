/**   
 * @文件名称: IActInfoService.java
 * @类路径: com.dsky.baas.configservice.service
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2016年12月13日 下午2:31:05
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.dsky.baas.configservice.model.ActInfoBean;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年12月13日 下午2:31:05
 * @版本：V1.0
 */
@Component
public interface IActInfoService {
	public List<ActInfoBean> selectActInfo(String gameId,String actId,String beginTime,String endTime);
}
