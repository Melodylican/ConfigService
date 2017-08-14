package com.dsky.baas.configservice.controller;

import java.io.InputStream;
import java.io.PrintWriter;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.dsky.baas.configservice.dao.RedisMethonds;
import com.dsky.baas.configservice.logservice.IWarningReporterService;
import com.dsky.baas.configservice.model.BlackListBean;
import com.dsky.baas.configservice.model.ExchangeBean;
import com.dsky.baas.configservice.model.IntegralSchemeBean;
import com.dsky.baas.configservice.model.OrderBean;
import com.dsky.baas.configservice.model.PromoterBean;
import com.dsky.baas.configservice.model.RedeemCodeBean;
import com.dsky.baas.configservice.service.IGameConfigService;
import com.dsky.baas.configservice.service.excel.ImportExcelUtil;
import com.dsky.baas.configservice.util.DateUtil;
import com.dsky.baas.pointsservice.model.ExchangeOrder;

/**
 * @ClassName: GameConfigController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
@Controller
public class GameConfigController {
	private static final Logger logger = Logger
			.getLogger(GameConfigController.class);
	@Resource
	private IGameConfigService gameConfigService;
	
	private IWarningReporterService warningReporterService;
	
	@Autowired
	public void setWarningReporterService(
			IWarningReporterService warningReporterService) {
		this.warningReporterService = warningReporterService;
	}
	@Autowired
	public void setGameConfigService(IGameConfigService gameConfigService) {
		this.gameConfigService = gameConfigService;
	}

	/**
	 * 方法功能说明：进入配置信息主页面
	 * 创建： by chris.li 
	 * 修改：日期 2016年11月25日 by chris.li
	 * 修改内容：本次只是修改了部分格式
	 * @参数： @param page
	 * @参数： @param pageSize
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/index", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String index(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			Model model) {
		logger.info("GameConfigController  -->   【/promoter/index】");
		// List<PromoterBean> list = dataDao.getByPaging(page, pageSize);
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		// 获取拥有权限的游戏名列表
		String gameNames = gameConfigService.selectUserGameType(userName);
		String[] gameNameArr = null;
		if (gameNames != null) {
			gameNameArr = gameNames.split(",");
		} else
			gameNameArr = new String[] {""};
		
		try {
			String userRole = gameConfigService.selectUserRole(userName);
			logger.info("/promoter/index ====== userRole="
					+ userRole);
			List<PromoterBean> list = gameConfigService.selectByPaging(
					((page - 1) * pageSize), pageSize, gameNameArr[0],userRole);
			/*
			 * 用于紧急测试功能 暂时关闭
			//获取活动是否开启紧急测试按钮 重新封装
			List<PromoterBean> list = new ArrayList<PromoterBean>();
			for(PromoterBean pb:listorg) {
				//logger.info("测试点：..."+RedisMethonds.get("urgentstate_"+pb.getGameId()+"_"+pb.getId()).toString());
				pb.setUrgentState(RedisMethonds.get("urgentstate_"+pb.getGameId()+"_"+pb.getId()).toString());
				list.add(pb);
			}
			*/
			// 获得总行数
			int rowCount = gameConfigService.getPromoterTotalRows(gameNameArr[0]);
			model.addAttribute("list", list);
			int pages = 0;
			if (rowCount % pageSize == 0)
				pages = rowCount / pageSize;
			else
				pages = rowCount / pageSize + 1;

			List<String> gameNameList = Arrays.asList(gameNameArr);
			model.addAttribute("gameNameList", gameNameList);
			model.addAttribute("pages", pages);
			model.addAttribute("page", page);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error("/promoter/index中出现了异常\n" + e.getMessage());
			warningReporterService.reportWarnString("/promoter/index中出现了异常\n" + e.getMessage());
		}
		return "index";
	}
	
	/**
	 * 方法功能说明：进入创建配置信息的页面
	 * 创建： by chris.li 
	 * 修改：日期  2016年11月25日  by chris.li
	 * 修改内容：本次只是修改了部分格式
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/create", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String createPromoter(Model model) {
		logger.info("GameConfigController  -->   【/promoter/create】");
		logger.info("==create_change==");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String gameName = gameConfigService.selectUserGameType(userName);
		String[] gameNameArr = null;
		if (gameName != null) {
			gameNameArr = gameName.split(",");
		} else
			gameNameArr = new String[] {""};
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		return "createwithtab";
	}

	/**
	 * 
	 * 方法功能说明：处理查询推广活动
	 * 创建： by chris.li 
	 * 修改：日期 2016年11月25日 by Chris.li
	 * 修改内容：本次只是修改了部分格式
	 * @参数： @param page
	 * @参数： @param pageSize
	 * @参数： @param gameName
	 * @参数： @param location
	 * @参数： @param beginTime
	 * @参数： @param endTime
	 * @参数： @param model
	 * @参数： @return
	 * @参数： @throws InterruptedException    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/search", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String search(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
			String gameName, String location, String beginTime, String endTime,
			Model model) throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/search】");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();

		try {
			String userRole = gameConfigService.selectUserRole(userName);
			logger.info("/promoter/search == userRole = "+ userRole);
			List<PromoterBean> list = gameConfigService.selectByOptions(
					gameName, location, beginTime.replace('/', '-'),
					endTime.replace('/', '-'), ((page - 1) * pageSize),
					pageSize, userName, userRole);
			// 获得总行数
			int rowCount = gameConfigService.selectBySearchingPage(gameName,
					location, beginTime, endTime, userName, userRole);
			/*用于紧急测试功能 暂时关闭
			//获取活动是否开启紧急测试按钮 重新封装
			List<PromoterBean> list = new ArrayList<PromoterBean>();
			for(PromoterBean pb:listorg) {
				pb.setUrgentState(RedisMethonds.get("urgentstate_"+pb.getGameId()+"_"+pb.getId()));
				list.add(pb);
			}
			*/
			model.addAttribute("list", list);
			int pages = 0;
			if (rowCount % pageSize == 0)
				pages = rowCount / pageSize;
			else
				pages = rowCount / pageSize + 1;

			// 获取拥有权限的游戏名列表
			String gameNames = gameConfigService.selectUserGameType(userName);
			String[] gameNameArr = null;
			if (gameNames != null) {
				gameNameArr = gameNames.split(",");
			} else
				gameNameArr = new String[] { "" };
			List<String> gameNameList = Arrays.asList(gameNameArr);
			model.addAttribute("gameNameList", gameNameList);

			logger.info("search method 一共有几条  " + rowCount);
			model.addAttribute("pages", pages);
			model.addAttribute("page", page);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("search_gameName", gameName);
			model.addAttribute("search_game_location", location);
			model.addAttribute("search_beginTime", beginTime);
			model.addAttribute("search_endTime", endTime);
		} catch (Exception e) {
			logger.error("/promoter/search出现异常\n" + e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/search出现异常\n" + e.getMessage());
		}
		return "index";
	}

	/**
	 * 
	 * 方法功能说明： 进入更新推广活动配置的页面
	 * 创建： by chris.li 
	 * 修改：日期 2016年11月25日  by chris.li
	 * 修改内容：修改格式
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @参数： @throws InterruptedException    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/update", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String update(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/update】");
		// 获得总行数
		//PromoterBean promoterBean = JSONObject.parseObject((request.getParameter("promoterBean")), PromoterBean.class);//暂时取消通过连接传递的方式
		PromoterBean promoterBean = gameConfigService.selectPromoterBeanById(request.getParameter("id"));//替换成只传递id 
		RedeemCodeBean redeemCodeBean = gameConfigService.selectRedeemCodeById(Integer.parseInt(promoterBean.getId()));
		IntegralSchemeBean integralSchemeBean = gameConfigService.selectIntegralSchemeById(Integer.parseInt(promoterBean.getId()));
		ExchangeBean exchBean = gameConfigService.selectExchangeById(Integer.parseInt(promoterBean.getId()));
		if(promoterBean != null)
			logger.info("==promoterBean== "+promoterBean.toString());
		if(redeemCodeBean != null)
			logger.info("==redeemCodeBean== "+redeemCodeBean.toString());
		if(integralSchemeBean != null)
			logger.info("==integralSchemeBean== "+integralSchemeBean.toString());
		if(exchBean != null)
			logger.info("==exchBean== "+exchBean.toString());

		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String gameName = gameConfigService.selectUserGameType(userName);
		String[] gameNameArr = null;
		if (gameName != null) {
			gameNameArr = gameName.split(",");
		} else
			gameNameArr = new String[] { "" };
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		model.addAttribute("promoterBean", promoterBean);
		model.addAttribute("redeemCodeBean", redeemCodeBean);
		model.addAttribute("integralSchemeBean", integralSchemeBean);
		model.addAttribute("exchBean", exchBean);

		return "updatewithtab";
	}

	/**
	 * 
	 * 方法功能说明：保存推广活动更新信息
	 * 创建： by chris.li 
	 * 修改：日期  2016年11月25日 by chris.li
	 * 修改内容：修改格式
	 * @参数： @param promoterBean
	 * @参数： @param redeemCodeBean
	 * @参数： @param integralSchemeBean
	 * @参数： @param model
	 * @参数： @return
	 * @参数： @throws InterruptedException    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/saveupdate", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveUpdate(
			@ModelAttribute("promoterBean") PromoterBean promoterBean,
			@ModelAttribute("redeemCodeBean") RedeemCodeBean redeemCodeBean,
			@ModelAttribute("integralSchemeBean") IntegralSchemeBean integralSchemeBean,
			@ModelAttribute("exchBean") ExchangeBean exchBean,
			Model model) throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/saveupdate】");
		// 获得总行数
		// model.addAttribute("promoterBean", promoterBean);
		logger.info("==promoterBean== "+promoterBean.toString());
		logger.info("==redeemCodeBean== "+redeemCodeBean.toString());
		logger.info("==integralSchemeBean== "+integralSchemeBean.toString());
		logger.info("==exchBean== "+exchBean.toString());

		StringBuilder str = new StringBuilder();
		if (integralSchemeBean.getLevel() != 0)
			str.append("等级:"+integralSchemeBean.getLevel());
		if (integralSchemeBean.getTime() != 0)
			str.append(",时间:" + integralSchemeBean.getTime() + "(s)");
		//str.subSequence(0, str.length()-1);
		promoterBean.setGameType(str.toString());

		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		String gameName = gameConfigService.selectUserGameType(userName);
		String[] gameNameArr = null;
		if (gameName != null) {
			gameNameArr = gameName.split(",");
		} else
			gameNameArr = new String[] { "" };
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		String result = gameConfigService.updatePromoter(promoterBean);
		if (result != null) {
			logger.info("判断不能更新promoter " + result);
			model.addAttribute("promoterBean", promoterBean);
			model.addAttribute("redeemCodeBean", redeemCodeBean);
			model.addAttribute("integralSchemeBean", integralSchemeBean);
			model.addAttribute("exchBean", exchBean);
			model.addAttribute("updateMsg", "与已存在活动存在时间冲突，请重新规划活动时间！");
			return "updatewithtab";
		} else {
			int i = gameConfigService.updatePromoterById(promoterBean);
			int j = gameConfigService.updateRedeemCodeById(redeemCodeBean);
			int k = gameConfigService.updateIntegralSchemeById(integralSchemeBean);
			int l = gameConfigService.updateExchangeById(exchBean);

			logger.info("promoterBean更新结果：" + i + "   redeemCodeBean更新结果：" + j + "  integralSchemeBean更新结果：" + k+"  exchBean更新结果："+l);
			String gameId = promoterBean.getGameId();
			if (i != 0 && j != 0 && k != 0 & l != 0) {
				//更新成功后删除redis缓存
				model.addAttribute("updateMsg", "保存成功！");
				RedisMethonds.delActivitieKey("gameinvite:RMI:configServiceApiResultBean:gameid="+gameId+"&location="+promoterBean.getLocation());
				logger.info("删除Redis Key = "+"gameinvite:RMI:configServiceApiResultBean:gameid="+gameId+"&location="+promoterBean.getLocation());
				RedisMethonds.delActivitieKey("gameinvite:RMI:configServiceApiResultBean:actid="+promoterBean.getId());
				logger.info("删除Redis Key = "+"gameinvite:RMI:configServiceApiResultBean:actid="+promoterBean.getId());
				RedisMethonds.delActivitieKey("act_promoter_"+promoterBean.getGameId());
				logger.info("删除Redis Key = "+"act_promoter_"+promoterBean.getId());
				
				RedisMethonds.delActivitieKey("pointservice:RMI:exchApiResultBean:gameid="+gameId);
				logger.info("删除Redis Key :"+"pointservice:RMI:exchApiResultBean:gameid="+gameId);
				RedisMethonds.delActivitieKey("act_exchange_"+gameId);
				logger.info("删除Redis Key = "+"act_exchange_"+gameId);
				
				JSONObject jsonOb = new JSONObject();
				jsonOb.put("click_share_to_Weixin", integralSchemeBean.getWeixinClickShare());
				jsonOb.put("succ_share_to_Weixin", integralSchemeBean.getWeixinClickShareSuccess());
				jsonOb.put("click_share_to_QQ", integralSchemeBean.getQqClickShare());
				jsonOb.put("succ_share_to_QQ", integralSchemeBean.getQqClickShareSuccess());
				jsonOb.put("click_share_to_Weibo", integralSchemeBean.getWeiboClickShare());
				jsonOb.put("succ_share_to_Weibo", integralSchemeBean.getWeiboClickShareSuccess());
				RedisMethonds.hashSet("tgy_share_reward_conf", gameId, jsonOb.toJSONString());
				logger.info("存储到redis的 key : tgy_share_reward_conf  gameId : "+gameId +"   value : "+jsonOb.toJSONString());
				
			} else {
				model.addAttribute("updateMsg", "保存失败,请重新保存！");
			}
			logger.info("==修改后的活动信息=="+ promoterBean.toString());
			logger.info("==修改后的活动信息=="+ redeemCodeBean.toString());
			logger.info("==修改后的活动信息=="+ integralSchemeBean.toString());
			logger.info("==修改后的兑换信息=="+ exchBean.toString());
		}
		
		return "updatewithtab";
	}

	/**
	 * 
	 * 方法功能说明：保存创建的推广活动入库
	 * 创建： by chris.li 
	 * 修改：日期 2016年11月25日  by chris.li
	 * 修改内容：修改部分格式
	 * @参数： @param promoterBean
	 * @参数： @param redeemCodeBean
	 * @参数： @param integralSchemeBean
	 * @参数： @param model
	 * @参数： @return
	 * @参数： @throws InterruptedException    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/savecreate", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveCreate(
			@ModelAttribute("promoterBean") PromoterBean promoterBean,
			@ModelAttribute("redeemCodeBean") RedeemCodeBean redeemCodeBean,
			@ModelAttribute("integralSchemeBean") IntegralSchemeBean integralSchemeBean,
			@ModelAttribute("exchBean") ExchangeBean exchBean,
			Model model) throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/savecreate】");
		logger.info("==promoterBean== "+promoterBean.toString());
		logger.info("==redeemCodeBean== "+redeemCodeBean.toString());
		logger.info("==integralSchemeBean== "+integralSchemeBean.toString());
		String str = "";
		if (integralSchemeBean.getLevel() != 0)
			str += "等级:" + integralSchemeBean.getLevel() ;
		if (integralSchemeBean.getTime() != 0)
			str += ",时间:" + integralSchemeBean.getTime() + "(s)";

		promoterBean.setGameType(str);
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		promoterBean.setCreatedBy(userName);
		exchBean.setCreatedBy(userName);
		String gameName = gameConfigService.selectUserGameType(userName);
		logger.info("可分享的方式有："+redeemCodeBean.getShareMethod());
		String[] gameNameArr = null;
		if (gameName != null) {
			gameNameArr = gameName.split(",");
		} else
			gameNameArr = new String[] { "" };
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		String result = gameConfigService.createPromoter(promoterBean);
		if (result != null) {
			logger.info("判断不能插入promoter " + result);
			model.addAttribute("promoterBean", promoterBean);
			model.addAttribute("redeemCodeBean", redeemCodeBean);
			model.addAttribute("integralSchemeBean", integralSchemeBean);
			model.addAttribute("exchBean", exchBean);
			model.addAttribute("insertMsgTime", "与已存在活动存在时间冲突，请重新规划活动时间！");
			return "createwithtab";
		} else {
			// logger.info("判断为可以插入promoter "+result);
			// 需要判断当前配置是否已经存在
			//TODO 需要修改成数据库事务类型
			int i = gameConfigService.insertPromoter(promoterBean);
			int id = gameConfigService.getLastInsertId();//获取新创建的数据库行号
			redeemCodeBean.setId(id);
			integralSchemeBean.setId(id);
			exchBean.setId(id);
			logger.info("创建测试： "+exchBean.getGameName()+"  "+exchBean.getGameId()+"  "+exchBean.toString() );
			int j = gameConfigService.insertRedeemCode(redeemCodeBean);
			int k = gameConfigService.insertIntegralScheme(integralSchemeBean);
			int l = gameConfigService.insertExchange(exchBean);
			if (i != 0 ) {
				model.addAttribute("insertMsg", "新建成功！");
			}else {
				model.addAttribute("insertMsg", "新建失败,请重新创建！");
			}
			if (i != 0 && j != 0 && k != 0 && l != 0) {
				model.addAttribute("insertMsg", "新建成功！");
				//保存分享奖励到redis
				JSONObject jsonOb = new JSONObject();
				jsonOb.put("click_share_to_Weixin", integralSchemeBean.getWeixinClickShare());
				jsonOb.put("succ_share_to_Weixin", integralSchemeBean.getWeixinClickShareSuccess());
				jsonOb.put("click_share_to_QQ", integralSchemeBean.getQqClickShare());
				jsonOb.put("succ_share_to_QQ", integralSchemeBean.getQqClickShareSuccess());
				jsonOb.put("click_share_to_Weibo", integralSchemeBean.getWeiboClickShare());
				jsonOb.put("succ_share_to_Weibo", integralSchemeBean.getWeiboClickShareSuccess());
				RedisMethonds.hashSet("tgy_share_reward_conf", promoterBean.getGameId(), jsonOb.toJSONString());
				logger.info("存储到redis的 key : tgy_share_reward_conf  gameId : "+promoterBean.getGameId() +"   value : "+jsonOb.toJSONString());
			} else {
				model.addAttribute("insertMsg", "新建失败,请重新创建！");
			}
			return "createwithtab";
		}
	}

	/**
	 * 
	 * 方法功能说明：删除推广活动
	 * 创建： by chris.li 
	 * 修改：日期 2016年11月25日 by chris.li
	 * 修改内容：修改部分格式
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @参数： @throws InterruptedException    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String delete(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/delete】");
		int id = Integer.parseInt(request.getParameter("id").toString());
		String gameId = request.getParameter("gameId");
		logger.info("==删除活动的数据库行号==" + id);

		//删除数据库中创建的活动
		//TODO需要修改成数据库事务
		int i = gameConfigService.deletePromoterById(id);
		int j = gameConfigService.deleteRedeemCodeById(id);
		int k = gameConfigService.deleteIntegralSchemeById(id);
		int l = gameConfigService.deleteExchangeById(id);
		
		//更新状态后删除redis缓存
		RedisMethonds.delActivitieKey("gameinvite:RMI:configServiceApiResultBean:gameid="+gameId+"&location=中国大陆");
		logger.info("删除Redis Key = "+"gameinvite:RMI:configServiceApiResultBean:gameid="+gameId+"&location=中国大陆");
		RedisMethonds.delActivitieKey("gameinvite:RMI:configServiceApiResultBean:actid="+id);
		logger.info("删除Redis Key = "+"gameinvite:RMI:configServiceApiResultBean:actid="+id);
		RedisMethonds.delActivitieKey("act_promoter_"+gameId);
		logger.info("删除Redis Key = "+"act_promoter_"+gameId);
		
		RedisMethonds.delActivitieKey("pointservice:RMI:exchApiResultBean:gameid="+gameId);
		logger.info("删除Redis Key :"+"pointservice:RMI:exchApiResultBean:gameid="+gameId);
		RedisMethonds.delActivitieKey("act_exchange_"+gameId);
		logger.info("删除Redis Key = "+"act_exchange_"+gameId);
	
		if (i != 0 && j != 0 && k != 0) {
			model.addAttribute("deleteMsg", "删除成功！");
			logger.info("删除的gameId : "+gameId);
			RedisMethonds.delHashField("tgy_share_reward_conf", gameId);
		} else {
			model.addAttribute("deleteMsg", "删除失败,请重新删除！");
		}
		

		return "forward:/promoter/index";
	}

	/**
	 * 更新推广活动状态 暂停/启用
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/promoter/state", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String state(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/state】");
		/*接收连接地址传递的参数*/
		int id = Integer.parseInt(request.getParameter("id").toString());
		String gameId = request.getParameter("gameId");
		String location = request.getParameter("location");
		int state = Integer.parseInt(request.getParameter("state").toString());
		int page = Integer.parseInt(request.getParameter("page").toString());
		int pageSize = Integer.parseInt(request.getParameter("pageSize").toString());
		
		//设置修改状态信息
		String msg = "";
		if (state == 1)
			msg = "关闭成功！";
		else
			msg = "开启成功！";
		//设置更新状态入库
		int updateStateMsg = gameConfigService.updateState(id, state ^ 1);
		if (updateStateMsg > 0) {
			//更新状态后删除redis缓存
			RedisMethonds.delActivitieKey("gameinvite:RMI:configServiceApiResultBean:gameid="+gameId+"&location="+location);
			logger.info("删除Redis Key = "+"gameinvite:RMI:configServiceApiResultBean:gameid="+gameId+"&location="+location);
			RedisMethonds.delActivitieKey("gameinvite:RMI:configServiceApiResultBean:actid="+id);
			logger.info("删除Redis Key = "+"gameinvite:RMI:configServiceApiResultBean:actid="+id);
			RedisMethonds.delActivitieKey("act_promoter_"+gameId);
			logger.info("删除Redis Key = "+"act_promoter_"+gameId);
			
			RedisMethonds.delActivitieKey("pointservice:RMI:exchApiResultBean:gameid="+gameId);
			logger.info("删除Redis Key :"+"pointservice:RMI:exchApiResultBean:gameid="+gameId);
			RedisMethonds.delActivitieKey("act_exchange_"+gameId);
			logger.info("删除Redis Key = "+"act_exchange_"+gameId);

		} else {
			msg = "状态更新失败，请重新尝试！";
		}
		model.addAttribute("updateStateMsg", msg);
		return "forward:/promoter/index?page=" + page + "&pageSize="+ pageSize;
	}

	/*以下部分为黑名单相关功能的处理方法*/	
	/**
	 * 
	 * 方法功能说明：用于显示黑名单
	 * 创建：2017年1月6日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @参数： @throws InterruptedException    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/blacklist", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String blacklist(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/blacklist】");
		/*接收连接地址传递的参数*/
		int actId = Integer.parseInt(request.getParameter("actId").toString());
		String gameId = request.getParameter("gameId");
		String gameName = request.getParameter("gameName");
		String playerId = request.getParameter("playerId");
		logger.info("查询的playerId="+playerId);
		List<BlackListBean> listorg = null;
		int rowCount =0;
		try{
			listorg = RedisMethonds.getBlackListBean("blacklist:"+actId+":"+gameId);
			if(listorg != null) {
				rowCount = listorg.size();
				Collections.reverse(listorg);
				logger.info(listorg.toString());
			} else {
				logger.info("该游戏暂时没有黑名单");
			}
		}catch(Exception e) {
			e.printStackTrace();//TODO 测试用
			logger.error("从Redis中获取黑名单出现异常\n"+e.getMessage());
		}
		//处理根据playerId查询
		if(playerId != null && !"".equals(playerId) && null != listorg) {
			List<BlackListBean> list = new ArrayList<BlackListBean>();
			for(BlackListBean blb:listorg) {
				if((blb.getPlayerId()+"").equals(playerId)) {
					list.add(blb);
					listorg = list;
					rowCount = 1;
					break;
				}
			}
			
			if(list.size()<1) {
				listorg = null;
				rowCount = 0;
			}
		}
		
		model.addAttribute("playerId", playerId);
		model.addAttribute("list", listorg);
		model.addAttribute("rowCount", rowCount);
		model.addAttribute("actId", actId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		
		return "blacklist";
	}
	
	/**
	 * 
	 * 方法功能说明：用于增加黑名单
	 * 创建：2017年1月6日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @参数： @throws InterruptedException    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/addblacklist", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String addBlacklist(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/addblacklist】");
		BlackListBean blb = null;
		int actId = 0;
		int gameId = 0;
		int expireTime=0;
		String gameName = null;
		int rowCount = 0;
		List<BlackListBean> listorg = null;
		/*接收连接地址传递的参数*/
		try{
			actId = Integer.parseInt(request.getParameter("actId").toString());
			gameId = Integer.parseInt(request.getParameter("gameId"));
			expireTime = Integer.parseInt(request.getParameter("expireTime"));
			logger.info("获取的过期时间是： "+expireTime);
			gameName = request.getParameter("gameName");
			String strPlayerId = request.getParameter("playerId").toString().trim();
			listorg = RedisMethonds.getBlackListBean("blacklist:"+actId+":"+gameId);
			if(NumberUtils.isNumber(strPlayerId)) {
				logger.info("playerId = "+strPlayerId);
				
				//对象数据封装
				blb = new BlackListBean();
				blb.setId(actId);
				blb.setGameId(gameId);
				blb.setGameName(gameName);
				blb.setPlayerId(strPlayerId);
				long nowTime = System.currentTimeMillis();
				blb.setInsertTime((int)(nowTime/1000L));
				if(expireTime != 0) {
					blb.setExpireTime((int)((nowTime)/1000L)+expireTime);
				}else {
					blb.setExpireTime(expireTime);
				}
				try{
					int flag =0;
					if(listorg != null) {
						//判断用户是否已在黑名单中了
						for(BlackListBean bb:listorg) {
							if(bb.getPlayerId() == blb.getPlayerId()) {
								flag =1;
							 	break;
							}
						}
						if(flag == 0) {
							listorg.add(blb);
						}
					} else {
						listorg = new ArrayList<BlackListBean> ();
						listorg.add(blb);
					}
					rowCount = listorg.size();
					RedisMethonds.setBlackList("blacklist:"+actId+":"+gameId, listorg);
					if(expireTime == 0) {
						//如果没有设置过期时间则直接存储
						RedisMethonds.set("blacklist:"+actId+":"+gameId+":"+blb.getPlayerId(), blb.getPlayerId()+"");//供兑换码和积分功能查询
					}else {
						//设置了过期时间则存储时设定过期时间 单位为 秒
						RedisMethonds.setBlackList("blacklist:"+actId+":"+gameId+":"+blb.getPlayerId(), blb.getPlayerId()+"", expireTime);
					}
				}catch(Exception e) {
					logger.error("从Redis中获取黑名单出现异常\n"+e.getMessage());
				}
				Collections.reverse(listorg);
				
		   }
		}catch(Exception e) {
			logger.error("获取参数过程中出现异常\n"+e.getMessage());
		}
		
		model.addAttribute("list", listorg);
		model.addAttribute("rowCount", rowCount);
		model.addAttribute("actId", actId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		return "blacklist";
	}
	
	/**
	 * 
	 * 方法功能说明：处理通过积分详情页面加入黑名单的请求
	 * 创建：2017年4月26日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @参数： @throws InterruptedException    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/addblacklistajax", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String addBlacklistAjax(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/addblacklistajax】");
		BlackListBean blb = null;
		int actId = 0;
		int gameId = 0;
		int expireTime=0;
		String gameName = null;

		/*接收连接地址传递的参数*/
		try{
			actId = Integer.parseInt(request.getParameter("actId").toString());
			gameId = Integer.parseInt(request.getParameter("gameId"));
			expireTime = Integer.parseInt(request.getParameter("expireTime"));
			logger.info("获取的过期时间是： "+expireTime);
			gameName = request.getParameter("gameName");
			String strPlayerId = request.getParameter("playerId").toString().trim();
			if(NumberUtils.isNumber(strPlayerId)) {
				logger.info("进入加入黑名单逻辑");
				//int playerId = Integer.parseInt(strPlayerId);
				//对象数据封装
				blb = new BlackListBean();
				blb.setId(actId);
				blb.setGameId(gameId);
				blb.setGameName(gameName);
				blb.setPlayerId(strPlayerId);
				long nowTime = System.currentTimeMillis();
				blb.setInsertTime((int)(nowTime/1000L));
				if(expireTime != 0) {
					blb.setExpireTime((int)((nowTime)/1000L)+expireTime);
				} else {
					blb.setExpireTime(expireTime);
				}
				
				List<BlackListBean> listorg = null;
				try{
					listorg = RedisMethonds.getBlackListBean("blacklist:"+actId+":"+gameId);
					int flag =0;
					if(listorg != null) {
						//判断用户是否已在黑名单中了
						for(BlackListBean bb:listorg) {
							if(bb.getPlayerId() == blb.getPlayerId()) {
								flag =1;
							 	break;
							}
						}
						if(flag == 0) {
							logger.info("用户不在黑名单中");
							listorg.add(blb);
						} else {
							logger.info("用户已在黑名单中 不再重复加入");
							return "{\"msg\":\"the playerId has already in the blacklist\"}";
						}
				   } else {
					   logger.info("当前游戏当前活动还没有黑名单。。。");
						listorg = new ArrayList<BlackListBean> ();
						listorg.add(blb);
					}
					RedisMethonds.setBlackList("blacklist:"+actId+":"+gameId, listorg);
					if(expireTime == 0) {
						logger.info("blacklist:"+actId+":"+gameId);
						logger.info(blb.getPlayerId());
						//如果没有设置过期时间则直接存储
						RedisMethonds.set("blacklist:"+actId+":"+gameId+":"+blb.getPlayerId(), blb.getPlayerId()+"");//供兑换码和积分功能查询
					}else {
						//设置了过期时间则存储时设定过期时间 单位为 秒
						RedisMethonds.setBlackList("blacklist:"+actId+":"+gameId+":"+blb.getPlayerId(), blb.getPlayerId()+"", expireTime);
					}
				}catch(Exception e) {
					//TODO 异常测试
					e.printStackTrace();
					logger.error("从Redis中获取黑名单出现异常\n"+e.getMessage());
					return "{\"msg\":\"add to blacklist failure\"}";
				}

				return "{\"msg\":\"add to blacklist success!\"}";
			
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("获取参数过程中出现异常\n"+e.getMessage());
		}
		return "{\"msg\":\"add to blacklist failure\"}";

	}	
	
	
	@RequestMapping(value = "/promoter/delblacklist", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String delBlacklist(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/delblacklist】");
		/*接收连接地址传递的参数*/
		int actId = Integer.parseInt(request.getParameter("actId").toString());
		String gameId = request.getParameter("gameId");
		String gameName = request.getParameter("gameName");
		String playerId = request.getParameter("playerId");
		logger.info("playerId : "+playerId);
		List<BlackListBean> listorg = null;
		int rowCount =0;
		try{
			listorg = RedisMethonds.getBlackListBean("blacklist:"+actId+":"+gameId);
			RedisMethonds.delActivitieKey("blacklist:"+actId+":"+gameId+":"+playerId);
			if(listorg != null) {
				for(Iterator<BlackListBean> it = listorg.iterator();it.hasNext();) {
					BlackListBean blb = it.next();
					if((blb.getPlayerId()+"").equals(playerId)){
						it.remove();
						break;
					}
				}
				rowCount = listorg.size();
			}
			RedisMethonds.setBlackList("blacklist:"+actId+":"+gameId, listorg);
		}catch(Exception e) {
			logger.error("从Redis中获取黑名单出现异常\n"+e.getMessage());
		}
		Collections.reverse(listorg);
		model.addAttribute("list", listorg);
		model.addAttribute("rowCount", rowCount);
		model.addAttribute("actId", actId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		
		return "blacklist";
	}
	
    @RequestMapping(value="/promoter/gotoblacklistimport",method={RequestMethod.GET,RequestMethod.POST})  
    public  String  gotoblacklistimport(HttpServletRequest request, Model model) throws Exception {  
        logger.info("GameConfigController  -->   【/promoter/gotoblacklistimport】");
        String gameId = request.getParameter("gameId");
        String actId = request.getParameter("actId");
        String gameName = request.getParameter("gameName");
        logger.info("传递的参数： gameId = "+gameId+"  actId = "+actId);
        model.addAttribute("gameId", gameId);
        model.addAttribute("actId", actId);
        model.addAttribute("gameName", gameName);
        
        return "blacklistimport";
    }	
	
    /** 
     * 用于黑名单的批量导入
     * 描述：通过 jquery.form.js 插件提供的ajax方式上传文件 
     * @param request 
     * @param response 
     * @throws Exception 
     */  
    @ResponseBody  
    @RequestMapping(value="/promoter/blacklistAjaxUpload",method={RequestMethod.GET,RequestMethod.POST})  
    public  void  ajaxUploadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        logger.info("通过 jquery.form.js 提供的ajax方式上传黑名单文件文件！"); 
        PrintWriter out = null;  
        response.setCharacterEncoding("utf-8");  //防止ajax接受到的中文信息乱码  
        out = response.getWriter();
        
        String gameId = request.getParameter("gameId");
        String actId = request.getParameter("actId"); 
        String gameName=  request.getParameter("gameName");
        InputStream in =null;  
        List<List<Object>> listob = null;  
        MultipartFile file = multipartRequest.getFile("upfile"); 
        logger.info("获取文件完毕！"+file.getSize()+"  "+file.getName());
        if(file.isEmpty()){  
        	logger.info("抛出异常！");
            throw new Exception("文件不存在！");  
        }  
          
        in = file.getInputStream();  
        listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename()); 
        logger.info("listob size "+listob.size());
        if(!NumberUtils.isNumber(gameId) || !NumberUtils.isNumber(actId)) {
        	logger.info("数据格式不正确： "+gameId+"  "+actId);
	        out.print("文件导入失败，请重新提交！");  
	        out.flush();  
	        out.close(); 
	        return;
        }
        
        List<BlackListBean> blackList = new ArrayList<BlackListBean>();  
        //该处可调用service相应方法进行数据保存到数据库中
        long nowtime = System.currentTimeMillis();
        for (int i = 0; i < listob.size(); i++) {  
        	logger.info("将数据转化成对象集合的形式！");
            List<Object> lo = listob.get(i); 
            if(!NumberUtils.isNumber(String.valueOf(lo.get(0))) || !NumberUtils.isNumber(String.valueOf(lo.get(1)))) {
            	logger.info(String.valueOf(lo.get(0)) +"  :  "+String.valueOf(lo.get(1)));
    	        continue;
            }
            BlackListBean blb = new BlackListBean();
            logger.info(String.valueOf(lo.get(0))+"   ::   "+String.valueOf(lo.get(1)));
            blb.setPlayerId(String.valueOf(lo.get(0)));//TODO playerId int -> String
            int expireTime = Integer.parseInt(String.valueOf(lo.get(1)));//(int)((nowTime)/1000L) 
            if(expireTime == 0)
            	blb.setExpireTime(0);
            else
            	blb.setExpireTime((int)(nowtime/1000L)+expireTime);
            blb.setGameName(gameName);
            blb.setGameId(Integer.parseInt(gameId));
            blb.setId(Integer.parseInt(actId));
            logger.info("打印信息 --> "+blb.toString()); 
            blackList.add(blb);
        }

        try {
        	List<BlackListBean> listorg = null;
        	listorg = RedisMethonds.getBlackListBean("blacklist:"+actId+":"+gameId);
			for(BlackListBean blb:blackList) {
				long nowTime = System.currentTimeMillis();
				blb.setInsertTime((int)(nowTime/1000L));
				int flag =0;
				if(listorg != null) {
					//判断用户是否已在黑名单中了
					for(BlackListBean bb:listorg) {
						if(bb.getPlayerId() == blb.getPlayerId()) {
							flag =1;
						 	break;
						}
					}
					if(flag == 0) {
						listorg.add(blb);
						if(blb.getExpireTime() == 0) {
							//如果没有设置过期时间则直接存储
							RedisMethonds.set("blacklist:"+actId+":"+gameId+":"+blb.getPlayerId(), blb.getPlayerId()+"");
						}else {
							//设置了过期时间则存储时设定过期时间 单位为 秒
							RedisMethonds.setBlackList("blacklist:"+actId+":"+gameId+":"+blb.getPlayerId(), blb.getPlayerId()+"", blb.getExpireTime());
						}
					}
				} else {
					listorg = new ArrayList<BlackListBean> ();
					listorg.add(blb);
				}
			}
			RedisMethonds.setBlackList("blacklist:"+actId+":"+gameId, listorg);
			logger.info("调用方法结束");
		} catch(Exception e) {
			logger.error("/promoter/blacklistAjaxUpload 通过 jquery.form.js 提供的ajax方式上传黑名单文件文件！\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/blacklistAjaxUpload 通过 jquery.form.js 提供的ajax方式上传黑名单文件文件！\n"
					+ e.getMessage());			
	        out.print("文件导入失败，请重新提交！");  
	        out.flush();  
	        out.close(); 
		}
        logger.info("文件导入成功");
        out.print("文件导入成功！");  
        out.flush();  
        out.close();  
    }
    
    /**
     * 
     * 方法功能说明：导出黑名单
     * 创建：2017年5月2日 by chris.li 
     * 修改：日期 by 修改者
     * 修改内容：
     * @参数： @param request
     * @参数： @return    
     * @return ModelAndView   
     * @throws
     */
	@RequestMapping(value = "/promoter/exportblacklist", method = RequestMethod.GET)
	public ModelAndView getBlacklistExcel(HttpServletRequest request) {
		//List<Animal> animalList = animalService.getAnimalList();
		
		logger.info("GameConfigController  -->   【/promoter/exportblacklist】");
		String gameId = request.getParameter("gameId");
		String actId = request.getParameter("actId");
		
    	List<BlackListBean> listorg = RedisMethonds.getBlackListBean("blacklist:"+actId+":"+gameId);
    	
		
		logger.info("BlacklistViewExcel");
		return new ModelAndView("BlacklistViewExcel", "blacklist", listorg);
	}
	
	
	/*黑名单功能结束标记*/	
	/**
	 * 开启或者关闭紧急测试状态
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/promoter/urgent", method = { RequestMethod.GET,RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String urgent(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/urgent】");
		/*接收连接地址传递的参数*/
		int id = Integer.parseInt(request.getParameter("id").toString());
		String gameId = request.getParameter("gameId");
		//String location = request.getParameter("location");
		int urgentstate = Integer.parseInt(request.getParameter("urgentState").toString());
		int page = Integer.parseInt(request.getParameter("page").toString());
		int pageSize = Integer.parseInt(request.getParameter("pageSize").toString());
		
		//设置修改状态信息
		String msg = "";
		if (urgentstate == 1)
			msg = "关闭成功！";
		else
			msg = "开启成功！";
		//设置更新状态入库
		int updateStateMsg = RedisMethonds.set("urgentstate_"+gameId+"_"+id, (urgentstate ^ 1)+"");
		if (updateStateMsg == 0) {
			//更新状态后删除redis缓存
			msg="状态更新失败，请重新尝试！";
		}
		
		model.addAttribute("updateStateMsg", msg);
		return "forward:/promoter/index?page=" + page + "&pageSize="+ pageSize;
	}
	


}
