package com.idreamsky.promoter.test.rmi;

import com.dsky.baas.configservice.model.ExchApiResultBean;

/**
 * @ClassName: IGameConfig
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public interface IGameExchConfig {

	public ExchApiResultBean getExchangeByGameId(String gameId);
	
}

