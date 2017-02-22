package com.dsky.baas.configservice.rmi;

import com.dsky.baas.configservice.model.ApiResultBean;

/**
 * @ClassName: IGameConfig
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public interface IGameConfig {
	
	public boolean test();
	public ApiResultBean getOption(String gameId,String location);
	public ApiResultBean getOptionById(String id);
	
}

