package com.dsky.baas.configservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsky.baas.configservice.dao.RedisMethonds;
import com.dsky.baas.configservice.logservice.IWarningReporterService;
import com.dsky.baas.configservice.model.BlackListBean;
import com.dsky.baas.configservice.model.IntegralSchemeBean;
import com.dsky.baas.configservice.model.PromoterBean;
import com.dsky.baas.configservice.model.RedeemCodeBean;
import com.dsky.baas.configservice.service.IGameConfigService;

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
	 * 
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
		return "create";
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
		RedeemCodeBean redeemCodeBean = gameConfigService
				.selectRedeemCodeById(Integer.parseInt(promoterBean.getId()));
		IntegralSchemeBean integralSchemeBean = gameConfigService
				.selectIntegralSchemeById(Integer.parseInt(promoterBean.getId()));
		
		logger.info("==promoterBean== "+promoterBean.toString());
		logger.info("==redeemCodeBean== "+redeemCodeBean.toString());
		logger.info("==integralSchemeBean== "+integralSchemeBean.toString());

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

		return "update";
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
			Model model) throws InterruptedException {
		logger.info("GameConfigController  -->   【/promoter/saveupdate】");
		// 获得总行数
		// model.addAttribute("promoterBean", promoterBean);
		logger.info("==promoterBean== "+promoterBean.toString());
		logger.info("==redeemCodeBean== "+redeemCodeBean.toString());
		logger.info("==integralSchemeBean== "+integralSchemeBean.toString());

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
			model.addAttribute("updateMsg", "与已存在活动存在时间冲突，请重新规划活动时间！");
			return "update";
		} else {
			int i = gameConfigService.updatePromoterById(promoterBean);
			int j = gameConfigService.updateRedeemCodeById(redeemCodeBean);
			int k = gameConfigService.updateIntegralSchemeById(integralSchemeBean);

			logger.info("promoterBean更新结果：" + i + "   redeemCodeBean更新结果：" + j + "  integralSchemeBean更新结果：" + k);
			if (i != 0 && j != 0 && k != 0) {
				//更新成功后删除redis缓存
				model.addAttribute("updateMsg", "保存成功！");
				RedisMethonds.delActivitieKey("gameinvite:RMI:configServiceApiResultBean:gameid="+promoterBean.getGameId()+"&location="+promoterBean.getLocation());
				logger.info("删除Redis Key = "+"gameinvite:RMI:configServiceApiResultBean:gameid="+promoterBean.getGameId()+"&location="+promoterBean.getLocation());
				RedisMethonds.delActivitieKey("gameinvite:RMI:configServiceApiResultBean:actid="+promoterBean.getId());
				logger.info("删除Redis Key = "+"gameinvite:RMI:configServiceApiResultBean:actid="+promoterBean.getId());
				RedisMethonds.delActivitieKey("act_promoter_"+promoterBean.getGameId());
				logger.info("删除Redis Key = "+"act_promoter_"+promoterBean.getId());
			} else {
				model.addAttribute("updateMsg", "保存失败,请重新保存！");
			}
			logger.info("==修改后的活动信息=="+ promoterBean.toString());
			logger.info("==修改后的活动信息=="+ redeemCodeBean.toString());
			logger.info("==修改后的活动信息=="+ integralSchemeBean.toString());
		}
		return "update";
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
			model.addAttribute("insertMsgTime", "与已存在活动存在时间冲突，请重新规划活动时间！");
			return "create";
		} else {
			// logger.info("判断为可以插入promoter "+result);
			// 需要判断当前配置是否已经存在
			//TODO 需要修改成数据库事务类型
			int i = gameConfigService.insertPromoter(promoterBean);
			int id = gameConfigService.getLastInsertId();//获取新创建的数据库行号
			redeemCodeBean.setId(id);
			integralSchemeBean.setId(id);
			int j = gameConfigService.insertRedeemCode(redeemCodeBean);
			int k = gameConfigService.insertIntegralScheme(integralSchemeBean);
			if (i != 0 && j != 0 && k != 0) {
				model.addAttribute("insertMsg", "新建成功！");
			} else {
				model.addAttribute("insertMsg", "新建失败,请重新创建！");
			}
			return "create";
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
		logger.info("==删除活动的数据库行号==" + id);

		//删除数据库中创建的活动
		//TODO需要修改成数据库事务
		int i = gameConfigService.deletePromoterById(id);
		int j = gameConfigService.deleteRedeemCodeById(id);
		int k = gameConfigService.deleteIntegralSchemeById(id);
		if (i != 0 && j != 0 && k != 0) {
			model.addAttribute("deleteMsg", "删除成功！");
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
		} else {
			msg = "状态更新失败，请重新尝试！";
		}
		model.addAttribute("updateStateMsg", msg);
		return "forward:/promoter/index?page=" + page + "&pageSize="+ pageSize;
	}
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
			}
		}catch(Exception e) {
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
		/*接收连接地址传递的参数*/
		try{
			actId = Integer.parseInt(request.getParameter("actId").toString());
			gameId = Integer.parseInt(request.getParameter("gameId"));
			expireTime = Integer.parseInt(request.getParameter("expireTime"));
			logger.info("获取的过期时间是： "+expireTime);
			gameName = request.getParameter("gameName");
			int playerId = Integer.parseInt(request.getParameter("playerId").toString());
			//对象数据封装
			blb = new BlackListBean();
			blb.setId(actId);
			blb.setGameId(gameId);
			blb.setGameName(gameName);
			blb.setPlayerId(playerId);
			long nowTime = System.currentTimeMillis();
			blb.setInsertTime((int)(nowTime/1000L));
			if(expireTime != 0) {
				blb.setExpireTime((int)((nowTime)/1000L)+expireTime);
			}else {
				blb.setExpireTime(expireTime);
			}
		}catch(Exception e) {
			logger.error("获取参数过程中出现异常\n"+e.getMessage());
		}
		
		List<BlackListBean> listorg = null;
		try{
			listorg = RedisMethonds.getBlackListBean("blacklist:"+actId+":"+gameId);
			if(listorg != null)
				listorg.add(blb);
			else {
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
		model.addAttribute("list", listorg);
		model.addAttribute("rowCount", rowCount);
		model.addAttribute("actId", actId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		return "blacklist";
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
				for(BlackListBean blb:listorg) {
					if((blb.getPlayerId()+"").equals(playerId)){
						listorg.remove(blb);
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
	
	/**
	 * 开启或者关闭紧急测试状态
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/promoter/urgent", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
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
