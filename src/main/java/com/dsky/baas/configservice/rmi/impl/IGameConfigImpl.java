package com.dsky.baas.configservice.rmi.impl;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.dsky.baas.configservice.model.ApiResultBean;
import com.dsky.baas.configservice.model.IntegralSchemeBean;
import com.dsky.baas.configservice.model.PromoterBean;
import com.dsky.baas.configservice.model.RedeemCodeBean;
import com.dsky.baas.configservice.rmi.IGameConfig;
import com.dsky.baas.configservice.service.IGameConfigService;
import com.dsky.baas.configservice.util.ApiResultPacker;

/**
 * @ClassName: IGameConfigImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
@Component
public class IGameConfigImpl implements IGameConfig {
	private static final Logger logger = Logger
			.getLogger(IGameConfigImpl.class);
	private IGameConfigService gameConfigService;

	public void setGameConfigService(IGameConfigService gameConfigService) {
		this.gameConfigService = gameConfigService;
	}

	@Override
	public boolean test() {
		System.out.println("调用了我--服务端 O(∩_∩)O哈！");
		return true;
	}

	@Override
	public ApiResultBean getOption(String gameId, String location) {
		//TODO 需要修改 即使活动结束也将配置信息返回
		ApiResultBean apiResult = null;
		logger.info("远程调用了RMI接口 传递的参数是 gameId="+gameId +"  location="+location);

		PromoterBean promoterBean = gameConfigService
				.selectPromoterByGameIdAndLocation(gameId, location);
		if (promoterBean == null) {
			apiResult = new ApiResultBean();
			apiResult.setCode(100);
			apiResult.setMsg("没有当前时段的对应活动");
			logger.info("RMI IGameConfigImpl -> [getOption("+gameId+", "+location+")] 没有当前时段的对应活动");
			return apiResult;
		}
		RedeemCodeBean redeemCodeBean = gameConfigService
				.selectRedeemCodeById(Integer.parseInt(promoterBean.getId()));
		IntegralSchemeBean integralSchemeBean = gameConfigService
				.selectIntegralSchemeById(Integer.parseInt(promoterBean.getId()));
		apiResult = ApiResultPacker.parsePromoterBean2ApiResultBean(
				promoterBean, redeemCodeBean, integralSchemeBean);
		if (apiResult.getState() == 0) {
			apiResult = new ApiResultBean();
			apiResult.setCode(100);
			apiResult.setMsg("当前活动被暂停");
			logger.info("RMI IGameConfigImpl -> [getOption("+gameId+", "+location+")] 当前活动被暂停");
			return apiResult;
		}
		apiResult.setCode(0);
		apiResult.setMsg("success");
		logger.info("访问getOption返回的结果为"+apiResult.toString());
		return apiResult;
	}

	@Override
	public ApiResultBean getOptionById(String id) {
		//TODO 需要修改 即使活动结束也将配置信息返回
		ApiResultBean apiResult = null;
		logger.info("传递的参数是 id="+id);
		PromoterBean promoterBean = gameConfigService
				.selectPromoterBeanById(id);
		if (promoterBean == null) {
			apiResult = new ApiResultBean();
			apiResult.setCode(100);
			apiResult.setMsg("没有当前时段的对应活动");
			logger.info("RMI IGameConfigImpl -> [getOptionById("+id+")] 没有当前时段的对应活动");
			return apiResult;
		}
		RedeemCodeBean redeemCodeBean = gameConfigService
				.selectRedeemCodeById(Integer.parseInt(promoterBean.getId()));
		IntegralSchemeBean integralSchemeBean = gameConfigService
				.selectIntegralSchemeById(Integer.parseInt(promoterBean.getId()));
		apiResult = ApiResultPacker.parsePromoterBean2ApiResultBean(
				promoterBean, redeemCodeBean, integralSchemeBean);
		if (apiResult.getState() == 0) {
			apiResult = new ApiResultBean();
			apiResult.setCode(100);
			apiResult.setMsg("当前活动被暂停");
			logger.info("RMI IGameConfigImpl -> [getOptionById] 当前活动被暂停");
			return apiResult;
		}
		apiResult.setCode(0);
		apiResult.setMsg("success");
		return apiResult;
	}

}
