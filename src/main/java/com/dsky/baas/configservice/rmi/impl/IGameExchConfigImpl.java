package com.dsky.baas.configservice.rmi.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.dsky.baas.configservice.model.ExchApiResultBean;
import com.dsky.baas.configservice.model.ExchangeBean;
import com.dsky.baas.configservice.rmi.IGameExchConfig;
import com.dsky.baas.configservice.service.IGameConfigService;
import com.dsky.baas.configservice.util.ApiResultPacker;

/**
 * @ClassName: IGameConfigImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
@Component
public class IGameExchConfigImpl implements IGameExchConfig {
	private static final Logger logger = Logger
			.getLogger(IGameExchConfigImpl.class);
	private IGameConfigService gameConfigService;

	public void setGameConfigService(IGameConfigService gameConfigService) {
		this.gameConfigService = gameConfigService;
	}

	@Override
	public ExchApiResultBean getExchangeByGameId(String gameId) {
		ExchApiResultBean exchApiResultBean = null;
		try {
			logger.info("==gameId="+gameId);
			String gameName = gameConfigService.selectGameNameByGameId(gameId);
			if(gameName == null ) {
				logger.info("RMI IGameExchConfigImpl -> [getExchangeByGameId(String gameId)] 没有对应于gameId="+gameId+"的游戏");
				exchApiResultBean = new ExchApiResultBean();
				exchApiResultBean.setCode(100);
				exchApiResultBean.setMsg("没有对应于gameId="+gameId+"的游戏");
				return exchApiResultBean;
			}
			logger.info("==gameName="+gameName);
			//应该判断过期时无数据的情况 bug
			ExchangeBean exchBean = gameConfigService.selectExchBean(gameName);
			if(exchBean == null ) {
				logger.info("RMI IGameExchConfigImpl -> [getExchangeByGameId(String gameId)] 没有对应于gameId="+gameId+"的游戏或者当前时段没有兑换活动");
				exchApiResultBean = new ExchApiResultBean();
				exchApiResultBean.setCode(100);
				exchApiResultBean.setMsg("没有对应于gameId="+gameId+"的游戏或者当前时段没有兑换活动");
				return exchApiResultBean;
			}
			logger.info("==查询了exchBean ==="+exchBean.getGameName());
			exchApiResultBean = ApiResultPacker
					.parseExchBean2ExchApiResultBean(exchBean);
			logger.info("===============转换完成=============");
			exchApiResultBean.setCode(0);
			exchApiResultBean.setMsg("success");
		} catch (Exception e) {
			logger.info("出现了异常\n"+e.getMessage());
			exchApiResultBean = new ExchApiResultBean();
			exchApiResultBean.setCode(100);
			exchApiResultBean.setMsg("false");
			return exchApiResultBean;
		}
		logger.info("返回 exchApiResultBean");
		return exchApiResultBean;
	}

}
