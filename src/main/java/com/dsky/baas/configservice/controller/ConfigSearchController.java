package com.dsky.baas.configservice.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dsky.baas.configservice.dao.RedisMethonds;
//import com.dsky.baas.configservice.dao.RedisMethonds;
import com.dsky.baas.configservice.model.ExchangeBean;
import com.dsky.baas.configservice.model.GameConfig;
import com.dsky.baas.configservice.model.HaveExchConfig;
import com.dsky.baas.configservice.model.HaveGameConfig;
import com.dsky.baas.configservice.model.IntegralSchemeBean;
import com.dsky.baas.configservice.model.PromoterBean;
import com.dsky.baas.configservice.model.RedeemCodeBean;
import com.dsky.baas.configservice.service.IGameConfigService;
import com.dsky.baas.configservice.util.ApiResultPacker;
import com.dsky.baas.configservice.util.CommonUtil;

/**
 * @ClassName: ConfigSearchController
 * @Description: TODO(这个类用于提供给客户端调用的接口)
 * @author Chris.li
 */
@Controller
public class ConfigSearchController {
	private static final Logger logger = Logger
			.getLogger(ConfigSearchController.class);
	@Resource
	private IGameConfigService gameConfigService;
	
	//@Autowired
	//private IPWitheListBean ipWhiteListBean;//ip白名单类

	@Autowired
	public void setGameConfigService(IGameConfigService gameConfigService) {
		this.gameConfigService = gameConfigService;
	}

	/**
	 * 方法功能说明：暂未使用
	 * 创建：2016年11月17日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param req
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/get_option", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getGameConfig(HttpServletRequest req) {
		logger.info("ConfigSearchController  -->   【/get_option】");
		JSONObject jsonObject = null;
		if (req.getParameter("parameter") != null)
			jsonObject = JSONObject.parseObject(req.getParameter("parameter"));
		else
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "parameter json格式参数必须传递"));

		String gameId = "";
		String location = "";

		if (!jsonObject.containsKey("gameId")) {
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "gameId字段必须传递 "));
		} else {
			gameId = jsonObject.get("gameId").toString();// 获取游戏ID
			if (gameId.equals(""))
				return JSON.toJSONString(ApiResultPacker
						.packToApiResultObject(100, "gameId字段不能为空 "));
		}
		if (!jsonObject.containsKey("location")) {
			// 若果没有传递地区参数，则默认为 "中国大陆"
			location = "中国大陆";
		} else {
			location = jsonObject.get("location").toString();// 获取地区
		}

		PromoterBean promoterBean = gameConfigService
				.selectPromoterByGameIdAndLocation(gameId, location);
		
		if (promoterBean == null)
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "没有当前时段的对应活动"));
		GameConfig gameConfig = ApiResultPacker
				.parsePromoterBean2GameConfig(promoterBean);
		return JSON.toJSONString(ApiResultPacker
				.packToApiResultObject(gameConfig));
	}

	/**
	 * 
	 * 方法功能说明：该方法暂时未实际使用
	 * 创建：2016年11月18日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param req
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/get_optionbyid", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getGameConfigById(HttpServletRequest req) {
		logger.info("ConfigSearchController  -->   【/get_optionbyid】");
		logger.info("==" + req.getParameter("parameter"));
		JSONObject jsonObject = JSONObject.parseObject(req
				.getParameter("parameter"));
		String id = "";

		if (!jsonObject.containsKey("id")) {
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "id字段必须传递 "));
		} else {
			id = jsonObject.get("id").toString();// 获取游戏ID
			if ("".equals(id))
				return JSON.toJSONString(ApiResultPacker
						.packToApiResultObject(100, "id字段不能为空 "));
		}

		PromoterBean promoterBean = gameConfigService
				.selectPromoterBeanById(id);
		if (promoterBean == null)
			return com.alibaba.fastjson.JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "没有当前时段的对应活动"));
		GameConfig gameConfig = ApiResultPacker
				.parsePromoterBean2GameConfig(promoterBean);
		return com.alibaba.fastjson.JSON.toJSONString(ApiResultPacker
				.packToApiResultObject(gameConfig));
	}

	/** 
	 * 用于cp查询是否具有分享权限   该方法暂停使用
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/share", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getShareRights(HttpServletRequest req) {

		logger.info("ConfigSearchController  -->   【/share】");
		JSONObject jsonObject = JSONObject.parseObject(req
				.getParameter("parameter"));

		String gameId = "";
		String location = "";
		String level = "";
		String time = "";

		if (!jsonObject.containsKey("gameId")) {
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "gameId字段必须传递 "));
		} else {
			gameId = jsonObject.get("gameId").toString();// 获取游戏ID
			if (gameId.equals(""))
				return JSON.toJSONString(ApiResultPacker
						.packToApiResultObject(100, "gameId字段不能为空 "));
		}
		if (!jsonObject.containsKey("location")) {
			// 若果没有传递地区参数，则默认为 "中国大陆"
			location = "中国大陆";
		} else {
			location = jsonObject.get("location").toString();// 获取地区
		}
		if (!jsonObject.containsKey("level") && !jsonObject.containsKey("time")) {
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "游戏等级level和游戏在线时间time不能都为空 "));
		}
		if (jsonObject.containsKey("level")) {
			level = jsonObject.get("level").toString();// 获取等级;
		}
		if (jsonObject.containsKey("time")) {
			time = jsonObject.get("time").toString();// 获取游戏时间
		}

		PromoterBean promoterBean = gameConfigService
				.selectPromoterByGameIdAndLocation(gameId, location);
		if (promoterBean == null)
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "没有当前时段的对应活动"));
		GameConfig gameConfig = ApiResultPacker
				.parsePromoterBean2GameConfig(promoterBean);
		int flag_level = 1;
		int flag_time = 1;
		if (gameConfig.getConfig_option().containsKey("等级")) {
			int configLevel = Integer.parseInt(gameConfig.getConfig_option()
					.get("等级"));
			if (configLevel > Integer.parseInt(level))
				flag_level = 0;
		}
		if (gameConfig.getConfig_option().containsKey("时间")) {
			int configTime = Integer.parseInt(gameConfig.getConfig_option()
					.get("时间"));
			if (configTime > Integer.parseInt(time))
				flag_time = 0;
		}

		if (flag_level == 1 && flag_time == 1)
			// 等级和时间都满足要求
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(0, "true"));
		else {
			// 等级和时间中有一项不满足
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(0, "false"));
		}

	}

	/**
	 * 供客户端查询当前是否具有活动
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/act", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getAct(HttpServletRequest req) {
		logger.info("ConfigSearchController  -->   【/act】");
		Map<String, String> cookieMap = CommonUtil.parseHeaderCookie(req);
		if (cookieMap == null) {
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "鉴权失败"));
		}
		logger.info("==/act==" + cookieMap.size());
		String gameId = "";
		String location = "";
		String _cip = cookieMap.get("_cip");
		String uid = cookieMap.get("uid");
		logger.info("获取到的客户端ip="+_cip);
		if (!cookieMap.containsKey("gid")) {
			logger.info("ConfigSearchController /act接口  鉴权中没有gid字段" + gameId);
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "鉴权中gid字段必须传递 "));
		} else {
			gameId = cookieMap.get("gid").toString();// 获取游戏ID
			logger.info("ConfigSearchController /act接口  gid ====" + gameId);
			if ("".equals(gameId))
				return JSON.toJSONString(ApiResultPacker
						.packToApiResultObject(100, "鉴权中gid字段不能为空 "));
		}
		if (!cookieMap.containsKey("location")) {
			// 若果没有传递地区参数，则默认为 "中国大陆"
			location = "中国大陆";
		} else {
			location = cookieMap.get("location").toString();// 获取地区
		}
		PromoterBean promoterBean = null;
		//此处待改成从redis中获取
		promoterBean = RedisMethonds.getPromoterBean("act_promoter_"+gameId);
		if(promoterBean == null) {
			promoterBean = gameConfigService.selectPromoterByGameIdAndLocation(gameId, location);
			if (promoterBean == null) {
				logger.info("ConfigSearchController /act接口 没有对应于：gameId="+gameId+" 的推广活动，或者当前不在活动日期内");
				return JSON.toJSONString(ApiResultPacker
						.packToApiResultObject(100, "没有对应于：gameId="+gameId+" 的推广活动，或者当前不在活动日期内"));
			} else {
				RedisMethonds.set("act_promoter_"+gameId, promoterBean);
				logger.info("redis中没有对应于【"+gameId+"】的活动配置信息，现将其放入redis中key【act_exchage_"+gameId+"】");
			}
		}
		//用于查询黑名单的键值
		String searchBlaskListKey = "blackList:"+promoterBean.getId()+":"+promoterBean.getGameId()+":"+uid;
		String isInBlaskList = RedisMethonds.getBlackList(searchBlaskListKey);
		logger.info("查询黑名单的键值： "+searchBlaskListKey +"   查询结果： "+isInBlaskList);
		if(isInBlaskList != null && isInBlaskList.length() > 0)
			return JSON.toJSONString(ApiResultPacker.packToApiResultObject(100, "没有对应于：gameId="+gameId+" 的推广活动，或者当前不在活动日期内"));
		
		logger.info("ConfigSearchController /act接口  gameId="+gameId+"  "+promoterBean.toString());

		/*
		 * 本次暂时不提供这个功能
		//只有在活动处于暂停状态才进行
		//进行ip白名单验证
		//判断是否开启了紧急测试按钮
		String urgentState = RedisMethonds.get("urgentstate_"+promoterBean.getGameId()+"_"+promoterBean.getId());
		logger.info("获取当前活动是否开启紧急测试："+urgentState+"  urgentstate_"+promoterBean.getGameId()+"_"+promoterBean.getId());
		if("0".equals(urgentState)) {
			//如果开启了紧急测试按钮，则将当前活动的状态修改为暂停状态
			String iplist = ipWhiteListBean.getIpWhiteList();
			logger.info("配置的ip白名单为："+iplist);
			if(iplist != null) {
				if(!iplist.trim().contains(_cip)) {
					//如果ip白名单中不包含该客服端ip则将活动状态修改为暂停状态
					promoterBean.setState(0);
					logger.info("ConfigSearchController /act接口  开启了紧急测试按钮，则将该活动【"+gameId+"】 【"+promoterBean.getId()+"】 状态修改为暂停状态 ip白名单【"+iplist+"】 客户端ip【"+_cip+"】");
				}
				logger.info("【"+_cip+"】 在白名单  【"+iplist+"】 中活动状态按照原值返回");
			}
		}
        */
		ExchangeBean exch = null;
		exch = RedisMethonds.getExchangeBean("act_exchange_"+gameId);
		if(exch == null) {
			exch = gameConfigService.selectExchBean(promoterBean.getGameName());
			if (exch == null) {
				logger.info("ConfigSearchController /act接口 没有对应于：gameId="+gameId+" 的兑换活动，或者当前不在兑换日期内");
				return JSON.toJSONString(ApiResultPacker.packToApiResultObject(100, "没有对应于：gameId="+gameId+" 的兑换活动，或者当前不在兑换日期内"));
			} else {
				RedisMethonds.set("act_exchange_"+gameId, exch);
				logger.info("redis中没有对应于【"+gameId+"】的兑换配置信息，现将其放入redis中key【act_exchage_"+gameId+"】");
			}
		}
		logger.info("ConfigSearchController /act接口  "+exch.toString());
		HaveGameConfig havaGameConfig = ApiResultPacker.parsePromoterBean2HaveGameConfig(promoterBean,exch);
		havaGameConfig.setCode(0);
		havaGameConfig.setMsg("success");
		logger.info("ConfigSearchController /act接口  客户端访问返回结果为："+havaGameConfig.toString());
		return JSON.toJSONString(havaGameConfig);
	}

	/**
	 * 供客户端查询可兑换的周期
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/exch", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getExchange(HttpServletRequest req) {
		logger.info("ConfigSearchController  -->   【/exch】");
		Map<String, String> cookieMap = CommonUtil.parseHeaderCookie(req);


		if (cookieMap == null) {
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "鉴权失败"));
		}
		logger.info("==/exch==" + cookieMap.size());
		String gameId = "";

		if (!cookieMap.containsKey("gid")) {
			return JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "鉴权中gid字段必须传递 "));
		} else {
			gameId = cookieMap.get("gid").toString();// 获取游戏ID
			logger.info("gid =" + gameId);
			if ("".equals(gameId))
				return JSON.toJSONString(ApiResultPacker
						.packToApiResultObject(100, "鉴权中gid字段不能为空 "));
		}
		String uid = cookieMap.get("uid");//获取用户的uid
		logger.info("客户端查询的游戏ID是：" + gameId);
		ExchangeBean exchBean = null;
		exchBean = RedisMethonds.getExchangeBean("act_exchange_"+gameId);
		if(exchBean == null) {
			PromoterBean pb = RedisMethonds.getPromoterBean("act_promoter_"+gameId);
			String gameName = pb.getGameName();
			if(gameName == null || "".equals(gameName))
				gameName = gameConfigService.selectGameNameByGameId(gameId);
			logger.info("客户端查询的游戏名称是：" + gameName);
			if(gameName == null) {
				return JSON.toJSONString(ApiResultPacker
						.packToApiResultObject(100, "ConfigSearchController /exch接口  没有对应于：gameId="+gameId+" 的兑换活动，或者当前不在兑换日期内"));				
			} else {
				//用于查询黑名单的键值
				String searchBlaskListKey = "blackList:"+pb.getId()+":"+pb.getGameId()+":"+uid;
				String isInBlaskList = RedisMethonds.getBlackList(searchBlaskListKey);
				logger.info("查询黑名单的键值： "+searchBlaskListKey +"   查询结果： "+isInBlaskList);
				if(isInBlaskList != null && isInBlaskList.length() > 0)
					return JSON.toJSONString(ApiResultPacker.packToApiResultObject(100, "没有对应于：gameId="+gameId+" 的推广活动，或者当前不在活动日期内"));
				
				exchBean = gameConfigService.selectExchBean(gameName);
				if (exchBean == null) {
					return JSON.toJSONString(ApiResultPacker.packToApiResultObject(100, "ConfigSearchController /exch接口  没有对应于：gameId="+gameId+" 的兑换活动，或者当前不在兑换日期内"));
				} else {
					RedisMethonds.set("act_exchange_"+gameId, exchBean);
				}
			}
		}
		
		HaveExchConfig havaExchConfig = ApiResultPacker.parsePromoterBean2HaveExchConfig(exchBean);
		havaExchConfig.setCode(0);
		havaExchConfig.setMsg("success");
		logger.info("ConfigSearchController /exch接口  客户端访问返回结果为："+havaExchConfig.toString());
		return com.alibaba.fastjson.JSON.toJSONString(havaExchConfig);
	}

	/**
	 * 暂时不对外提供服务 用于查询积分配置信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/score", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getScore(HttpServletRequest req) {
		logger.info("ConfigSearchController  -->   【/score】");
		// 返回积分兑换规则
		JSONObject jsonObject = null;
		if(req.getParameter("parameter") != null)
		     jsonObject = JSONObject.parseObject(req.getParameter("parameter"));
		else
			return 	 com.alibaba.fastjson.JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "parameter json格式字符串必须传递 "));
		String id = "";
		if (!jsonObject.containsKey("id")) {
			return com.alibaba.fastjson.JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "id字段必须传递 "));
		} else {
			id = jsonObject.get("id").toString();// 获取游戏ID
			if ("".equals(id))
				return com.alibaba.fastjson.JSON.toJSONString(ApiResultPacker
						.packToApiResultObject(100, "id字段不能为空 "));
		}

		PromoterBean promoterBean = gameConfigService
				.selectPromoterBeanById(id);
		// .selectPromoterByGameIdAndLocation(gameId, location);
		if (promoterBean == null)
			return com.alibaba.fastjson.JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "没有当前时段的对应活动"));
		IntegralSchemeBean integralSchemeBean = gameConfigService
				.selectIntegralSchemeById(Integer.parseInt(promoterBean.getId()));
		GameConfig gameConfig = ApiResultPacker.parsePromoterBean2GameConfig(
				promoterBean, integralSchemeBean);
		return com.alibaba.fastjson.JSON.toJSONString(gameConfig);
	}

	/**
	 * 暂时不对外提供服务 用于查询兑换码信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/redeemcode", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getRedeemCode(HttpServletRequest req) {
		logger.info("ConfigSearchController  -->   【/redeemcode】");
		// 返回积分兑换规则
		JSONObject jsonObject = null;
		if(req.getParameter("parameter") != null)
		     jsonObject = JSONObject.parseObject(req.getParameter("parameter"));
		else
			return 	 com.alibaba.fastjson.JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "parameter json格式字符串必须传递 "));
		String id = "";

		if (!jsonObject.containsKey("id")) {
			return com.alibaba.fastjson.JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "id字段必须传递 "));
		} else {
			id = jsonObject.get("id").toString();// 获取游戏ID
			if ("".equals(id))
				return com.alibaba.fastjson.JSON.toJSONString(ApiResultPacker
						.packToApiResultObject(100, "id字段不能为空 "));
		}
		PromoterBean promoterBean = gameConfigService
				.selectPromoterBeanById(id);
		if (promoterBean == null)
			return com.alibaba.fastjson.JSON.toJSONString(ApiResultPacker
					.packToApiResultObject(100, "没有当前时段的对应活动"));
		RedeemCodeBean redeemCodeBean = gameConfigService
				.selectRedeemCodeById(Integer.parseInt(promoterBean.getId()));
		GameConfig gameConfig = ApiResultPacker.parsePromoterBean2GameConfig(
				promoterBean, redeemCodeBean);
		return com.alibaba.fastjson.JSON.toJSONString(gameConfig);

	}
}
