package com.dsky.baas.configservice.controller;

import java.util.Arrays;
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

import com.alibaba.fastjson.JSONObject;
import com.dsky.baas.configservice.dao.RedisMethonds;
import com.dsky.baas.configservice.logservice.IWarningReporterService;
import com.dsky.baas.configservice.model.ExchangeBean;
import com.dsky.baas.configservice.service.IGameConfigService;

/**
 * @ClassName: GameConfigController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
@Controller
public class GameExchangeController {
	private static final Logger logger = Logger
			.getLogger(GameExchangeController.class);
	@Resource
	private IGameConfigService gameConfigService;
	@Resource
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
     * 进入兑换配置信息总页
     * @param page
     * @param pageSize
     * @param model
     * @return
     */
	@RequestMapping(value = "/exchange/exchange", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String index(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			Model model,HttpServletRequest request) {
		logger.info("GameExchangeController  -->   【/exchange/exchange】");
		String searchGameName = request.getParameter("searchGameName");
		String exchBeginTime = request.getParameter("exchBeginTime");
		String exchEndTime = request.getParameter("exchEndTime");
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		try {
			String userRole = gameConfigService.selectUserRole(userName);
			logger.info("/exchange/exchange ==userName="+userName+"== userRole = "
					+ userRole);
			String gameName = gameConfigService.selectUserGameType(userName);
			
			String[] gameNameArr = null;
			if (gameName != null) {
				gameNameArr = gameName.split(",");
			} else
				gameNameArr = new String[] { "" };
			List<String> gameNameList = Arrays.asList(gameNameArr);
			model.addAttribute("gameNameList", gameNameList);
			
			String gName = gameNameArr[0];
			if(searchGameName!= null && !"".equals(searchGameName))
				gName = searchGameName;
			List<ExchangeBean> list = null;
			int rowCount = 0;
			
			if(exchBeginTime != null && !"".equals(exchBeginTime)&& exchEndTime != null && !"".equals(exchEndTime) ) {
				list = gameConfigService.selectExchangeBygameName(gName, exchBeginTime, exchEndTime,
						userName, userRole, ((page - 1) * pageSize),pageSize);
				rowCount = gameConfigService.selectExchangeCount(gName, exchBeginTime, exchEndTime, userName, userRole);
			}else {
				list = gameConfigService.selectExchByPaging(((page - 1) * pageSize), pageSize, gName, userRole);
				rowCount =  gameConfigService.getExchangeTotalRows(gName,userRole);
				//logger.info("++++++查询的条数为："+rowCount+" 查询的游戏名字是："+gName);
			}
			model.addAttribute("list", list);
			int pages = 0;
			if (rowCount % pageSize == 0)
				pages = rowCount / pageSize;
			else
				pages = rowCount / pageSize + 1;
			model.addAttribute("pages", pages);
			model.addAttribute("page", page);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("searchGameName", gName);
			model.addAttribute("exchBeginTime", exchBeginTime);
			model.addAttribute("exchEndTime", exchEndTime);
		} catch (Exception e) {
			logger.error("/exchange/exchange中出现了异常\n" + e.getMessage(),e);
			warningReporterService.reportWarnString("/exchange/exchange中出现了异常\n" + e.getMessage());
		}
		return "exchange";
	}

	/**
	 * 进入创建兑换配置页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/exchange/create", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String createPromoter(Model model) {
		logger.info("GameExchangeController  -->   【/exchange/create】");
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
		return "createexchange";
	}

	/**
	 * 查询兑换配置 暂定使用
	 * @param page
	 * @param pageSize
	 * @param gameName
	 * @param exchBeginTime
	 * @param exchEndTime
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/exchange/search", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String search(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			String gameName,  String exchBeginTime, String exchEndTime,
			Model model) throws InterruptedException {
		logger.info("GameExchangeController  -->   【/exchange/search】");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		try {
			String userRole = gameConfigService.selectUserRole(userName);
			List<ExchangeBean> list = gameConfigService.selectExchangeBygameName(gameName, exchBeginTime, exchEndTime,
					userName, userRole, ((page - 1) * pageSize),pageSize);
			// 获得总行数
			int rowCount = gameConfigService.selectExchangeCount(gameName, exchBeginTime, exchEndTime, userName, userRole);
			model.addAttribute("list", list);
			int pages = 0;
			if (rowCount % pageSize == 0)
				pages = rowCount / pageSize;
			else
				pages = rowCount / pageSize + 1;
			String gameNames = gameConfigService.selectUserGameType(userName);
			String[] gameNameArr = null;
			if (gameNames == null || gameNames.equals("")) {
				gameNameArr = new String[] {""};
				return "exchange";
			} else
				gameNameArr = gameNames.split(",");
				
			List<String> gameNameList = Arrays.asList(gameNameArr);
			model.addAttribute("gameNameList", gameNameList);
			
			logger.info("search method 一共有几条  " + rowCount);
			model.addAttribute("exchBeginTime", exchBeginTime);
			model.addAttribute("exchEndTime", exchEndTime);
			model.addAttribute("gameName", gameName);
			model.addAttribute("pages", pages);
			model.addAttribute("page", page);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("search", "search");//当查询为空时避免提示信息有误
		} catch (Exception e) {
			logger.error("/promoter/search出现异常\n" + e.getMessage(),e);
			warningReporterService.reportWarnString("/exchange/exchange中出现了异常\n" + e.getMessage());
		}
		return "exchange";
	}

	/**
	 * 进入更新兑换配置页
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/exchange/update", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String update(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("GameExchangeController  -->   【/exchange/update】");
		// 获得总行数
		ExchangeBean exchBean = JSONObject.parseObject(
				(request.getParameter("exchangeBean")), ExchangeBean.class);

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
		model.addAttribute("exchBean", exchBean);
		logger.info("==" + exchBean.getId());
		// 数据入库
		return "updateexchange";
	}

	/**
	 * 保存兑换配置更新信息入库
	 * @param exchBean
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/exchange/saveupdate", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveUpdate(
			@ModelAttribute("exchBean") ExchangeBean exchBean,
			Model model) throws InterruptedException {
		logger.info("GameExchangeController  -->   【/exchange/saveupdate】");
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
		
		String result = gameConfigService.updateExch(exchBean);
		if(result != null ) {
			model.addAttribute("exchBean",exchBean);
			model.addAttribute("updateMsg","与已存在活动存在时间冲突，请重新规划活动时间！");
			return "updateexchange";
		}
		int i = gameConfigService.updateExchangeById(exchBean);
		if (i != 0 ) {
			model.addAttribute("updateMsg", "保存成功！");
			String gameId = gameConfigService.getGameIdByGameName(exchBean.getGameName());
			//修改后应该删除Redis 中的该条配置信息
			RedisMethonds.delActivitieKey("pointservice:RMI:exchApiResultBean:gameid="+gameId);
			logger.info("删除Redis Key :"+"pointservice:RMI:exchApiResultBean:gameid="+gameId);
			RedisMethonds.delActivitieKey("act_exchange_"+gameId);
			logger.info("删除Redis Key = "+"act_exchange_"+gameId);
		} else {
			model.addAttribute("updateMsg", "保存失败,请重新保存！");
		}
		return "updateexchange";
	}

	/**
	 * 保存创建兑换信息入库
	 * @param exchBean
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/exchange/savecreate", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveCreate(
			@ModelAttribute("exchBean") ExchangeBean exchBean,
			Model model) throws InterruptedException {
		logger.info("GameExchangeController  -->   【/exchange/savecreate】");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		exchBean.setCreatedBy(userName);
		
		String gameName = gameConfigService.selectUserGameType(userName);
		String[] gameNameArr = null;
		if (gameName != null) {
			gameNameArr = gameName.split(",");
		}else
			gameNameArr = new String[] { "" };
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		
		String result = gameConfigService.createExch(exchBean);
		if(result != null ) {
			model.addAttribute("exchBean",exchBean);
			model.addAttribute("insertMsg","与已存在活动存在时间冲突，请重新规划活动时间！");
			return "createexchange";
		}
		int i = gameConfigService.insertExchange(exchBean);
		if (i != 0 ) {
			model.addAttribute("insertMsg", "新建成功！");
		}else {
			model.addAttribute("insertMsg", "新建失败,请重新创建！");
		}

		return "redirect:/exchange/exchange";
	}

	/**
	 * 删除兑换信息
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/exchange/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String delete(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("GameExchangeController  -->   【/exchange/delete】");
		int id = Integer.parseInt(request.getParameter("id").toString());
		logger.info("==delete==" + id);

		int i = gameConfigService.deleteExchangeById(id);
		if (i != 0 ) {
			model.addAttribute("deleteMsg", "删除成功！");
		}else {
			model.addAttribute("deleteMsg", "删除失败,请重新删除！");
		}
		return "redirect:/exchange/exchange";
	}

}
