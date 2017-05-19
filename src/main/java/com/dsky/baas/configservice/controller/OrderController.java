package com.dsky.baas.configservice.controller;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dsky.baas.configservice.logservice.IWarningReporterService;
import com.dsky.baas.configservice.model.DetailBean;
import com.dsky.baas.configservice.model.ForumBean;
import com.dsky.baas.configservice.model.OrderBean;
import com.dsky.baas.configservice.model.PlayerSearchBean;
import com.dsky.baas.configservice.model.PromoterBean;
import com.dsky.baas.configservice.model.ReplyForumBean;
import com.dsky.baas.configservice.service.IGameConfigService;
import com.dsky.baas.configservice.service.excel.ImportExcelUtil;
import com.dsky.baas.configservice.service.impl.ExchRMIServiceImpl;
import com.dsky.baas.configservice.service.impl.GameInviteRMIServerServiceImpl;
import com.dsky.baas.configservice.service.impl.RMIServiceImpl;
import com.dsky.baas.configservice.service.impl.UserInfoService;
import com.dsky.baas.configservice.util.DateUtil;
import com.dsky.baas.gameinvite.model.InvitedCode;
import com.dsky.baas.pointsservice.model.ExchangeOrder;
import com.dsky.baas.pointsservice.model.PointsLog;

/**
 * @ClassName: GameConfigController
 * @Description: TODO(主要处理订单审核和积分详情相关的功能)
 * @author Chris.li
 */
@Controller
public class OrderController {
	private static final Logger logger = Logger
			.getLogger(OrderController.class);
	@Resource
	private IGameConfigService gameConfigService;
	@Resource
	private ExchRMIServiceImpl exchRmiServiceImpl;

	private RMIServiceImpl rmiServiceImpl;
	@Resource
	private GameInviteRMIServerServiceImpl gameInviteServiceImpl;
	@Autowired
	private UserInfoService userInfoService;
	@Resource
	private IWarningReporterService warningReporterService;
	
	@Autowired
	public void setWarningReporterService(
			IWarningReporterService warningReporterService) {
		this.warningReporterService = warningReporterService;
	}	
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@Autowired
	public void setRmiServiceImpl(RMIServiceImpl rmiServiceImpl) {
		this.rmiServiceImpl = rmiServiceImpl;
	}

	@Autowired
	public void setGameConfigService(IGameConfigService gameConfigService)  {
		this.gameConfigService = gameConfigService;
	}

	@Autowired
	public void setExchRmiServiceImpl(ExchRMIServiceImpl exchRmiServiceImpl) {
		this.exchRmiServiceImpl = exchRmiServiceImpl;
	}
	@Autowired
	public void setGameInviteRmiServerServiceImpl(
			GameInviteRMIServerServiceImpl gameInviteServiceImpl) {
		this.gameInviteServiceImpl = gameInviteServiceImpl;
	}
	/**
	 * 点击进入订单审批页面 获取订单信息
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/promoter/order", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String order(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			HttpServletRequest request, Model model) {
		logger.info("OrderController  -->   【/promoter/order】");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();// 查询当前登录用户名
		// 获取拥有权限的游戏名列表
		String gameNames = gameConfigService.selectUserGameType(userName);

		String[] gameNameArr = null;
		if (gameNames == null || gameNames.equals("")) {
			logger.info("当前账号可操作的游戏为空  ");
			return "order";
		} else {
			gameNameArr = gameNames.split(",");
		}
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		// 根据游戏名称查询游戏对应的id
		int gameId = Integer.parseInt(gameConfigService.getGameIdByGameName(gameNameArr[0]));
		logger.info("================gameId=" + gameId);
		PromoterBean pb = gameConfigService.selectPromoterByGameIdAndLocation(gameId+"", "中国大陆");
		int actId = Integer.MAX_VALUE;;
		try {
			actId = Integer.parseInt(pb.getId());
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
			//如果活动actId 不存在 则默认设置为最大值
			
		}

		List<ExchangeOrder> listOrg = null;
		try {
				listOrg = exchRmiServiceImpl.getExchangeOrderByGameId(gameId,((page - 1) * pageSize), pageSize);
		}catch (RemoteLookupFailureException e) {
			logger.error("/promoter/order RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/order RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());
			return "order";
		}catch (RemoteAccessException e) {
			logger.error("/promoter/order RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/order RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());
			return "order";
		}catch (ConnectException e) {
			logger.error("/promoter/order RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI连接失败\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/order RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());
			return "order";
		}
		logger.info("查询时的startRow=" + ((page - 1) * pageSize) + " endRow="
				+ pageSize);


		List<OrderBean> list = new ArrayList<OrderBean>();
		for (ExchangeOrder eo : listOrg) {
			OrderBean ob = new OrderBean();
			ob.setId(eo.getId());
			ob.setGameId(eo.getGameId());
			ob.setPlayerId(eo.getPlayerId());
			ob.setGameName(gameNameArr[0]);
			ob.setLevel(eo.getLevel());
			ob.setOrderCreatedDate(DateUtil.parseDate(Long.parseLong(eo.getCreateAt() + "") * 1000));
			ob.setHasPoints(eo.getHasPoints());
			ob.setOrderId(eo.getOrderId());
			ob.setOnlineTime(eo.getOnlineTime());
			ob.setAmount(eo.getAmount());
			ob.setRequestExchangePoints(eo.getRequestExchangePoints());
			ob.setPayInfo(eo.getPayInfo());
			ob.setStatus(eo.getStatus());
			ob.setUserMemo(eo.getUserMemo());
			if(pb != null) {
				try {
					InvitedCode ic = gameInviteServiceImpl.getInvitedCodeByPlayerIdAndGameIdAndActId(eo.getPlayerId(), gameId, actId);
					logger.info("查询  [getInvitedCodeByPlayerIdAndGameIdAndActId] 参数："+eo.getPlayerId()+"  "+gameId+"  "+actId );
					
					if(ic != null) {
						ob.setInvitedPeople(ic.getApplyCount()+"");
						logger.info("查询的次数为： "+ic.getApplyCount());
						logger.info("查询的结果为： "+ic.toString());
					} else {
						ob.setInvitedPeople("未查询到使用次数");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				ob.setInvitedPeople("未查询到使用次数");
			}
			
			list.add(ob);
		}

		// 获得总行数
		int rowCount = 0;
		try {
			rowCount = exchRmiServiceImpl.getExchangeOrderTotalNum(gameId);
		
		}catch(RemoteLookupFailureException e) {
			logger.error("/promoter/order RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/order RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());
			return "order";
		}catch(RemoteAccessException e) {
			logger.error("/promoter/order RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/order RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());
			return "order";
		}catch (ConnectException e) {
			logger.error("/promoter/order RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI连接异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/order RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());
			return "order";
		}
		logger.info("查询的数据条数======" + list.size() + "   ====总行数" + rowCount);
		int pages = 0;
		if (rowCount % pageSize == 0)
			pages = rowCount / pageSize;
		else
			pages = rowCount / pageSize + 1;

		model.addAttribute("list", list);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("searchGameName", gameNameArr[0]);
		return "order";
	}

	/**
	 * 
	 * 方法功能说明： excel 数据导入
	 * 创建：2017年2月8日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param page
	 * @参数： @param pageSize
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/orderajax", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String orderAjax(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			HttpServletRequest request, Model model) {
		logger.info("OrderController  -->   【/promoter/orderajax】");
		String searchGameName = null;
		try {
			logger.info("请求编码" + request.getCharacterEncoding());
			searchGameName = request.getParameter("searchGameName");
			logger.info("字符串转码 【" + request.getParameter("searchGameName") + "】-> 【" + searchGameName + "】");
		} catch (Exception e) {
			logger.error("字符串转码出现错误 【" + request.getParameter("searchGameName") + "】"+e.getMessage(),e);
			warningReporterService.reportWarnString("【/promoter/orderajax】 出现异常 字符串转码出现错误 【" + request.getParameter("searchGameName")+ "】"+ e.getMessage());
		}
		logger.info("==ajax searchGameName="+ searchGameName);
		String orderId = request.getParameter("orderId");
		logger.info("查询的orderId为： "+orderId);

		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();// 查询当前登录用户名
		// 获取拥有权限的游戏名列表
		String gameNames = gameConfigService.selectUserGameType(userName);
		String[] gameNameArr = null;
		if (gameNames != null) {
			gameNameArr = gameNames.split(",");
		} else
			gameNameArr = new String[] { "" };
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		model.addAttribute("searchGameName", searchGameName);

		// 根据游戏名称查询游戏对应的id
		int gameId = 0;
		try {
			String gameIdStr = gameConfigService.getGameIdByGameName(searchGameName);
			if(NumberUtils.isNumber(gameIdStr))
				gameId = Integer.parseInt(gameIdStr);
			else
				logger.info(searchGameName+"查询到的游戏ID不合法： "+gameIdStr);
		} catch (NumberFormatException e) {
			logger.error("转码时存在问题 "+e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderajax 【/promoter/orderajax】 出现异常 字符串转码出现错误 【" + request.getParameter("searchGameName")
					+ "】"+ e.getMessage());
		}
		logger.info("==gameId=" + gameId);
		PromoterBean pb = gameConfigService.selectPromoterByGameIdAndLocation(gameId+"", "中国大陆");
		int actId = Integer.MAX_VALUE;;
		try {
			actId = Integer.parseInt(pb.getId());
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
			//如果活动actId 不存在 则默认设置为最大值
			
		}
		List<ExchangeOrder> listOrg = null;
		try { 
			if(orderId == null || orderId.trim().equals(""))
				listOrg = exchRmiServiceImpl.getExchangeOrderByGameId(gameId,((page - 1) * pageSize), pageSize);
			else {
				listOrg = new ArrayList<ExchangeOrder>();
				ExchangeOrder eo = exchRmiServiceImpl.getExchangeOrderByOrderId(orderId);
				if(eo != null)
					listOrg.add(eo);
			}
		}catch(RemoteLookupFailureException e) {
			logger.error("/promoter/orderajax RMI调用远程接口【getExchangeOrderByGameId】出现异常  远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderajax RMI调用远程接口【getExchangeOrderByGameId】出现异常  远程RMI未启动\n"
					+ e.getMessage());
			return "order";
		}catch(RemoteAccessException e) {
			logger.error("/promoter/orderajax RMI调用远程接口【getExchangeOrderByGameId】出现异常  远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderajax RMI调用远程接口【getExchangeOrderByGameId】出现异常  远程RMI未启动\n"
					+ e.getMessage());
			return "order";
		}catch (ConnectException e) {
			logger.error("/promoter/orderajax  RMI调用远程接口【getExchangeOrderByGameId】出现异常 远程RMI连接异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderajax RMI调用远程接口【getExchangeOrderByGameId】出现异常  远程RMI未启动\n"
					+ e.getMessage());
			return "order";
		}
		logger.info("查询时的startRow=" + ((page - 1) * pageSize) + " endRow="
				+ pageSize);

		List<OrderBean> list = new ArrayList<OrderBean>();
		for (ExchangeOrder eo : listOrg) {
			OrderBean ob = new OrderBean();
			ob.setId(eo.getId());
			ob.setGameId(eo.getGameId());
			ob.setPlayerId(eo.getPlayerId());
			ob.setOrderCreatedDate(DateUtil.parseDate(Long.parseLong(eo
					.getCreateAt() + "") * 1000));
			ob.setGameName(searchGameName);
			ob.setLevel(eo.getLevel());
			ob.setHasPoints(eo.getHasPoints());
			ob.setOrderId(eo.getOrderId());
			ob.setOnlineTime(eo.getOnlineTime());
			ob.setAmount(eo.getAmount());
			ob.setOperationMemo(eo.getOperationMemo());
			ob.setRequestExchangePoints(eo.getRequestExchangePoints());
			ob.setPayInfo(eo.getPayInfo());
			ob.setStatus(eo.getStatus());
			ob.setUserMemo(eo.getUserMemo());
			if(pb != null) {
				try {
					InvitedCode ic = gameInviteServiceImpl.getInvitedCodeByPlayerIdAndGameIdAndActId(eo.getPlayerId(), gameId, actId);
					logger.info("查询  [getInvitedCodeByPlayerIdAndGameIdAndActId] 参数："+eo.getPlayerId()+"  "+gameId+"  "+actId );
					
					if(ic != null) {
						ob.setInvitedPeople(ic.getApplyCount()+"");
						logger.info("查询的次数为： "+ic.getApplyCount());
						logger.info("查询的结果为： "+ic.toString());
					} else {
						ob.setInvitedPeople("未查询到使用次数");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				ob.setInvitedPeople("未查询到使用次数");
			}
			list.add(ob);
		}
		if (request.getParameter("gameName") != null) {
			logger.info("=="+ request.getParameter("gameName").toString());
		}
		// 获得总行数
		int rowCount = 0;
		try {
			if(orderId == null || orderId.trim().equals(""))
				rowCount = exchRmiServiceImpl.getExchangeOrderTotalNum(gameId);
		
		}catch(RemoteLookupFailureException e) {
			logger.error("/promoter/orderajax RMI调用远程接口【getExchangeOrderTotalNum】出现异常  远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderajax RMI调用远程接口【getExchangeOrderTotalNum】出现异常  远程RMI未启动\n"
					+ e.getMessage());			
			return "order";
		}catch(RemoteAccessException e) {
			logger.error("RMI调用远程接口【getExchangeOrderTotalNum】出现异常  远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderajax RMI调用远程接口【getExchangeOrderTotalNum】出现异常  远程RMI未启动\n"
					+ e.getMessage());			
			return "order";
		}catch (ConnectException e) {
			logger.error("RMI调用远程接口【getExchangeOrderTotalNum】出现异常  远程RMI连接异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderajax RMI调用远程接口【getExchangeOrderTotalNum】出现异常  远程RMI未启动\n"
					+ e.getMessage());			
			return "order";
		}
		logger.info("查询的数据条数======" + list.size() + "   ====总行数" + rowCount);

		int pages = 0;
		if (rowCount % pageSize == 0)
			pages = rowCount / pageSize;
		else
			pages = rowCount / pageSize + 1;
		model.addAttribute("list", list);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("orderId", orderId);
		model.addAttribute("searchGameName", searchGameName);
		return "order";
	}

	/**
	 * 审核不通过
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/promoter/donotpassaudit", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String notThroughAudit(HttpServletRequest request, Model model)
			throws UnsupportedEncodingException {
		logger.info("OrderController  -->   【/promoter/donotpassaudit】");
		if (request.getParameter("orderId") != null) {
			logger.info("============donotpassaudit=============="+ request.getParameter("orderId").toString());
		}
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		String orderId = request.getParameter("orderId");
		String reason = request.getParameter("reason");
		String otherReason = request.getParameter("otherReason");
		if(otherReason != null && !"".equals(otherReason.trim())) {
			reason = otherReason;
			logger.info("输入的其他未通过审核的原因是： "+otherReason);
		}
		String gameName = request.getParameter("searchGameName");
		logger.info("未通过审批  游戏名字是：" + gameName);
		logger.info("未通过的原因=========" + reason + "  订单号是==" + orderId);
		try {
			exchRmiServiceImpl.updateExchangeOrderStatus(orderId, "REJECTED",
					reason);
		} catch(RemoteLookupFailureException e) {
			logger.error("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常  远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常  远程RMI未启动\n"
					+ e.getMessage());			
		} catch(RemoteAccessException e) {
			logger.error("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常  远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常  远程RMI未启动\n"
					+ e.getMessage());
		} catch (ConnectException e) {
			logger.error("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常  远程RMI未启动\n"
					+ e.getMessage());			
		}finally {
			return "forward:/promoter/orderajax?searchGameName="
			+ java.net.URLEncoder.encode(gameName, "utf-8") + "&page="
			+ page + "&pageSize=" + pageSize;
		}
		
	}

	/**
	 * 审核通过
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/promoter/throughaudit", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String throughAudit(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "12") int pageSize,
			HttpServletRequest request, Model model)
			throws UnsupportedEncodingException {
		logger.info("OrderController  -->   【/promoter/throughaudit】");
		if (request.getParameter("orderId") != null) {
			logger.info("============throughaudit=============="
					+ request.getParameter("orderId").toString());
		}
		String gameName = request.getParameter("searchGameName");
		logger.info("通过审批  游戏名字是：" + gameName);
		String orderId = request.getParameter("orderId").toString();
		try {
			exchRmiServiceImpl.updateExchangeOrderStatus(orderId, "PASSED", "");
		} catch(RemoteLookupFailureException e) {
			logger.error("/promoter/throughaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/throughaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常 远程RMI未启动\n"
					+ e.getMessage());
		} catch(RemoteAccessException e) {
			logger.error("RMI调用远程接口【updateExchangeOrderStatus】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/throughaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常 远程RMI未启动\n"
					+ e.getMessage());
		} catch (ConnectException e) {
			logger.error("RMI调用远程接口【updateExchangeOrderStatus】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/throughaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常 远程RMI未启动\n"
					+ e.getMessage());
		} finally{
			return "forward:/promoter/orderajax?searchGameName="
					+ java.net.URLEncoder.encode(gameName, "utf-8") + "&page="
					+ page + "&pageSize=" + pageSize;
		}
	}
	
    /**
     * 批量通过
     * @param page
     * @param pageSize
     * @param request
     * @param model
     * @return
     * @throws UnsupportedEncodingException
     */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/promoter/pass", method = {RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String pass(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "12") int pageSize,
			HttpServletRequest request, Model model)
			throws UnsupportedEncodingException {
		logger.info("OrderController  -->   【/promoter/throughaudit】");
		String[] orderIds= null;
		
		String searchGameName = "";
		if(request.getParameter("searchGameName") != null)
			searchGameName = request.getParameter("searchGameName");
		if (request.getParameter("orderIds") != null) {
			logger.info("==throughaudit=="
					+ request.getParameter("orderIds").toString());
			String orderIdsStr = request.getParameter("orderIds").toString();
			logger.info("获取的orderIds="+orderIdsStr);
			if(orderIdsStr == null ||orderIdsStr.length()==0) {
				return "forward:/promoter/orderajax?searchGameName="
				+ java.net.URLEncoder.encode(searchGameName, "utf-8") + "&page="
				+ page + "&pageSize=" + pageSize;
			}
			orderIds = orderIdsStr.substring(0, orderIdsStr.length()-1).split(",");
		}else {
			return "forward:/promoter/orderajax?searchGameName="
					+ java.net.URLEncoder.encode(searchGameName, "utf-8") + "&page="
					+ page + "&pageSize=" + pageSize;
		}
		if(request.getParameter("page") != null )
		    page = Integer.parseInt(request.getParameter("page"));
		if(request.getParameter("pageSize") !=null )
	        pageSize = Integer.parseInt(request.getParameter("pageSize"));
		try {
			exchRmiServiceImpl.updateExchangeOrderStatusByQuantity(orderIds, "PASSED");
		} catch(RemoteLookupFailureException e) {
			logger.error("RMI调用远程接口【updateExchangeOrderStatus】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/pass RMI调用远程接口【updateExchangeOrderStatus】出现异常 远程RMI未启动\n"
					+ e.getMessage());
		} catch(RemoteAccessException e) {
			logger.error("/promoter/pass RMI调用远程接口【updateExchangeOrderStatus】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/pass RMI调用远程接口【updateExchangeOrderStatus】出现异常 远程RMI未启动\n"
					+ e.getMessage());
		} catch (ConnectException e) {
			logger.error("/promoter/pass RMI调用远程接口【updateExchangeOrderStatus】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/pass RMI调用远程接口【updateExchangeOrderStatus】出现异常 远程RMI未启动\n"
					+ e.getMessage());
		} finally{
			return "forward:/promoter/orderajax?searchGameName="
					+ java.net.URLEncoder.encode(searchGameName, "utf-8") + "&page="
					+ page + "&pageSize=" + pageSize;
		}

	}

	/**
	 * 审核作废
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/promoter/invalid", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String invalid(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		logger.info("OrderController  -->   【/promoter/invalid】");
		logger.info("OrderController  -->   【/promoter/donotpassaudit】");
		if (request.getParameter("orderId") != null) {
			logger.info("============donotpassaudit=============="+ request.getParameter("orderId").toString());
		}
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		String orderId = request.getParameter("orderId");
		String otherReason = request.getParameter("otherReason");
		if(otherReason == null && "".equals(otherReason.trim())) {
			otherReason = "未输入作废原因";
			logger.info("输入的其他未通过审核的原因是： "+otherReason);
		}
		String gameName = request.getParameter("searchGameName");
		logger.info("未通过审批  游戏名字是：" + gameName);
		logger.info("未通过的原因=========" + otherReason + "  订单号是==" + orderId);
		try {
			exchRmiServiceImpl.updateExchangeOrderStatus(orderId, "INVALID",otherReason);
		} catch(RemoteLookupFailureException e) {
			logger.error("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常  远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常  远程RMI未启动\n"
					+ e.getMessage());			
		} catch(RemoteAccessException e) {
			logger.error("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常  远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常  远程RMI未启动\n"
					+ e.getMessage());
		} catch (ConnectException e) {
			logger.error("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/donotpassaudit RMI调用远程接口【updateExchangeOrderStatus】出现异常  远程RMI未启动\n"
					+ e.getMessage());			
		}finally {
			return "forward:/promoter/orderajax?searchGameName="
			+ java.net.URLEncoder.encode(gameName, "utf-8") + "&page="
			+ page + "&pageSize=" + pageSize;
		}
	}

	/**
	 * 查询积分详情
	 * 
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/promoter/detail", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String detail(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			HttpServletRequest request, Model model) {
		logger.info("OrderController  -->   【/promoter/detail】");
		String userIdStr = request.getParameter("userId");
		int userId =0;
		if(NumberUtils.isNumber(userIdStr)){
			try{
				userId = Integer.parseInt(userIdStr);
			}catch(NumberFormatException e) {
				logger.info("传入的userId= "+userIdStr+" 不是合法的数字型");
				return "detail";
			}
		} else {
			logger.info("传入的userId= "+userIdStr+" 不是合法的数字型");
			return "detail";
		}
		int gameId = Integer.parseInt(request.getParameter("gameId").trim());
		
		logger.info("在查询详情中  userId=" + userId + "  gameId=" + gameId);
		model.addAttribute("userId", userId);
		model.addAttribute("gameId", gameId);
		logger.info("积分详情中放入的userId ="+userId +" gameId = "+gameId);
		
		//获取查询时间
		String beginTime = request.getParameter("beginTime");
		logger.info("获取的 beginTime "+beginTime);
		String endTime = request.getParameter("endTime");
		if(endTime == null) {
			//当未配置结束时间时，取系统当前时间作为结束时间
			SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			endTime = sf.format(System.currentTimeMillis());
			logger.info("结束时间是："+endTime);
		}
		
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		
		int eTime = (int)(DateUtil.parseDateFromString(endTime, "yyyy/MM/dd HH:mm", 0)/1000);
		logger.info("结束时间是："+eTime);
		int bTime = 0;
		if(beginTime != null && beginTime.length() >0) {
			bTime = (int)(DateUtil.parseDateFromString(beginTime, "yyyy/MM/dd HH:mm", 0)/1000);
			logger.info("开始时间是："+bTime);
		}
		
		// 获得总行数
		int rowCount = 0;
		try{
			int actId =0;
			PromoterBean pb = gameConfigService.selectPromoterByGameIdAndLocation(gameId+"", "中国大陆");
			if(pb != null) {
				actId = Integer.parseInt(pb.getId());
				logger.info("查询到的活动ID为："+actId);
			}
			//查询当前账号的总积分
			int allpoints = rmiServiceImpl.getPoints(userId, gameId, actId);
			model.addAttribute("allpoints", allpoints);
			//TODO 修改成带时间参数的 (已修改)
			rowCount = rmiServiceImpl.getPonitsLogCount(userId, gameId,true,bTime,true,eTime);

		}catch(RemoteLookupFailureException e) {
			logger.error("/promoter/detail RMI调用远程接口【getPonitsLogCount】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/detail RMI调用远程接口【getPonitsLogCount】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "detail";
		} catch(RemoteAccessException e) {
			logger.error("/promoter/detail RMI调用远程接口【getPonitsLogCount】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/detail RMI调用远程接口【getPonitsLogCount】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "detail";
		} catch (ConnectException e) {
			logger.error("/promoter/detail RMI调用远程接口【getPonitsLogCount】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/detail RMI调用远程接口【getPonitsLogCount】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "detail";
		}
		int pages = 0;
		if (rowCount % pageSize == 0)
			pages = rowCount / pageSize;
		else
			pages = rowCount / pageSize + 1;
		
		List<PointsLog> listBean = null;
		try{
			//TODO 修改成带时间参数的 (已修改)
			listBean = (List<PointsLog>) rmiServiceImpl.getPonitsLog(userId, gameId, ((page - 1) * pageSize), pageSize,true,bTime,true,eTime); 
		}catch(RemoteLookupFailureException e) {
			logger.error("/promoter/detail RMI调用远程接口【getPonitsLog】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/detail RMI调用远程接口【getPonitsLog】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "detail";
		} catch(RemoteAccessException e) {
			logger.error("/promoter/detail RMI调用远程接口【getPonitsLog】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/detail RMI调用远程接口【getPonitsLog】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "detail";
		} catch (ConnectException e) {
			logger.error("/promoter/detail RMI调用远程接口【getPonitsLog】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/detail RMI调用远程接口【getPonitsLog】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "detail";
		}
		logger.info("查询的数据条数   =====" + listBean.size() + "查询的用户id是 " + userId);

		List<DetailBean> list = new ArrayList<DetailBean>();
		for (PointsLog p : listBean) {
			DetailBean d = new DetailBean();
			d.setAddNum(p.getAddNum());
			d.setCreateTime(DateUtil.parseDate(Long.parseLong(p.getCreateTime()
					+ "") * 1000));
			d.setInvitedCode(p.getInvitedCode());
			d.setFromPlayerId(p.getFromPlayerId());
			d.setPlayerId(p.getPlayerId());
			d.setMemo(p.getMemo());
			logger.info("积分详情中 playerId=" + d.getPlayerId());
			d.setGameId(p.getGameId());
			list.add(d);
		}
		logger.info("查询的数据条数 list " + list.size());
		PromoterBean pb = gameConfigService.selectPromoterByGameIdAndLocation(gameId+"", "中国大陆");

		model.addAttribute("list", list);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("gameId", gameId);
		model.addAttribute("playerId", userIdStr);
		if(pb != null) {
			model.addAttribute("gameName", pb.getGameName());
			model.addAttribute("actId", pb.getId());
			logger.info("向积分详情页面传递的参数： playerId = "+userIdStr+"  gameId = "+gameId+"  ");
		}

		return "detail";
	}
	
	/**
	 * 已通过审核的订单支付
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/promoter/orderpay", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String orderpay(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			HttpServletRequest request, Model model) {
		logger.info("OrderController  -->   【/promoter/orderpay】");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();// 查询当前登录用户名
		// 获取拥有权限的游戏名列表
		String gameNames = gameConfigService.selectUserGameType(userName);

		String[] gameNameArr = null;
		if (gameNames == null || gameNames.equals("")) {
			logger.info("当前账号可操作的游戏为空  ");
			return "passwedorder";
		} else {
			gameNameArr = gameNames.split(",");
		}
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		// 根据游戏名称查询游戏对应的id
		int gameId = Integer.parseInt(gameConfigService
				.getGameIdByGameName(gameNameArr[0]));
		logger.info("==gameId=" + gameId);
		PromoterBean pb = gameConfigService.selectPromoterByGameIdAndLocation(gameId+"", "中国大陆");
		int actId = Integer.MAX_VALUE;;
		try {
			actId = Integer.parseInt(pb.getId());
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
			//如果活动actId 不存在 则默认设置为最大值
			
		}
		int rowCount = 0;
		try{
			//获取已通过订单总行数
			rowCount = exchRmiServiceImpl.getExchangeOrderTotalNum(gameId, "PASSED");
			logger.info("游戏："+gameId+"已通过订单总行数 ："+rowCount);
		}catch(RemoteLookupFailureException e) {
			logger.error("/promoter/orderpay RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "passwedorder";
		} catch(RemoteAccessException e) {
			logger.error("RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "passwedorder";
		} catch (ConnectException e) {
			logger.error("RMI调用远程接口【getExchangeOrderTotalNum】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "passwedorder";
		}
		int pages = 0;
		if (rowCount % pageSize == 0)
			pages = rowCount / pageSize;
		else
			pages = rowCount / pageSize + 1;

		List<ExchangeOrder> listOrg = null;
		try {
				listOrg = exchRmiServiceImpl.getExchangeOrderBystatus(gameId, "PASSED", ((page - 1) * pageSize), pageSize);
		}catch (RemoteLookupFailureException e) {
			logger.error("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "passwedorder";
		}catch (RemoteAccessException e) {
			logger.error("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "passwedorder";
		}catch (ConnectException e) {
			logger.error("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI连接失败\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "passwedorder";
		}
		List<OrderBean> list = new ArrayList<OrderBean>();
		for (ExchangeOrder eo : listOrg) {
			OrderBean ob = new OrderBean();
			ob.setId(eo.getId());
			ob.setGameId(eo.getGameId());
			ob.setPlayerId(eo.getPlayerId());
			ob.setGameName(gameNameArr[0]);
			ob.setLevel(eo.getLevel());
			ob.setOrderCreatedDate(DateUtil.parseDate(Long.parseLong(eo
					.getCreateAt() + "") * 1000));
			ob.setHasPoints(eo.getHasPoints());
			ob.setOrderId(eo.getOrderId());
			ob.setOnlineTime(eo.getOnlineTime());
			ob.setOperationMemo(eo.getOperationMemo());
			ob.setAmount(eo.getAmount());
			ob.setRequestExchangePoints(eo.getRequestExchangePoints());
			ob.setPayInfo(eo.getPayInfo());
			ob.setStatus(eo.getStatus());
			ob.setUserMemo(eo.getUserMemo());
			if(pb != null) {
				try {
					InvitedCode ic = gameInviteServiceImpl.getInvitedCodeByPlayerIdAndGameIdAndActId(eo.getPlayerId(), gameId, actId);
					logger.info("查询  [getInvitedCodeByPlayerIdAndGameIdAndActId] 参数："+eo.getPlayerId()+"  "+gameId+"  "+actId );
					
					if(ic != null) {
						ob.setInvitedPeople(ic.getApplyCount()+"");
						logger.info("查询的次数为： "+ic.getApplyCount());
						logger.info("查询的结果为： "+ic.toString());
					} else {
						ob.setInvitedPeople("未查询到使用次数");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				ob.setInvitedPeople("未查询到使用次数");
			}
			list.add(ob);
		}
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("list", list);
		model.addAttribute("searchGameName", gameNameArr[0]);
		return "passwedorder";
	}
	
	/**
	 * 获取已支付过的订单信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/promoter/payedorderlist", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String payedOrders(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			HttpServletRequest request, Model model) {
		logger.info("OrderController  -->   【/promoter/payedorderlist】");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();// 查询当前登录用户名
		// 获取拥有权限的游戏名列表
		String gameNames = gameConfigService.selectUserGameType(userName);

		String[] gameNameArr = null;
		if (gameNames == null || gameNames.equals("")) {
			logger.info("当前账号可操作的游戏为空  ");
			return "payedorderlist";
		} else {
			gameNameArr = gameNames.split(",");
		}
		String type = request.getParameter("type");
		String status = "PAID";
		if(type != null && type.equals("2"))
			status = "REJECTED";
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		// 根据游戏名称查询游戏对应的id
		int gameId = Integer.parseInt(gameConfigService
				.getGameIdByGameName(gameNameArr[0]));
		logger.info("==gameId=" + gameId);
		PromoterBean pb = gameConfigService.selectPromoterByGameIdAndLocation(gameId+"", "中国大陆");
		int actId = Integer.MAX_VALUE;;
		try {
			actId = Integer.parseInt(pb.getId());
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
			//如果活动actId 不存在 则默认设置为最大值
			
		}
		int rowCount = 0;
		try{
			//获取已通过订单总行数
			rowCount = exchRmiServiceImpl.getExchangeOrderTotalNum(gameId, status);
			logger.info("游戏："+gameId+"已通过订单总行数 ："+rowCount);
		}catch(RemoteLookupFailureException e) {
			logger.error("/promoter/orderpay RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "payedorderlist";
		} catch(RemoteAccessException e) {
			logger.error("RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "payedorderlist";
		} catch (ConnectException e) {
			logger.error("RMI调用远程接口【getExchangeOrderTotalNum】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "payedorderlist";
		}
		int pages = 0;
		if (rowCount % pageSize == 0)
			pages = rowCount / pageSize;
		else
			pages = rowCount / pageSize + 1;

		List<ExchangeOrder> listOrg = null;
		try {
				listOrg = exchRmiServiceImpl.getExchangeOrderBystatus(gameId, status, ((page - 1) * pageSize), pageSize);
				List<OrderBean> list = new ArrayList<OrderBean>();
				for (ExchangeOrder eo : listOrg) {
					OrderBean ob = new OrderBean();
					ob.setId(eo.getId());
					ob.setGameId(eo.getGameId());
					ob.setPlayerId(eo.getPlayerId());
					ob.setGameName(gameNameArr[0]);
					ob.setLevel(eo.getLevel());
					ob.setOrderCreatedDate(DateUtil.parseDate(Long.parseLong(eo.getCreateAt() + "") * 1000));
					ob.setHasPoints(eo.getHasPoints());
					ob.setOperationMemo(eo.getOperationMemo());
					ob.setOrderId(eo.getOrderId());
					ob.setOnlineTime(eo.getOnlineTime());
					ob.setAmount(eo.getAmount());
					ob.setRequestExchangePoints(eo.getRequestExchangePoints());
					ob.setPayInfo(eo.getPayInfo());
					ob.setStatus(eo.getStatus());
					ob.setUserMemo(eo.getUserMemo());
					ob.setHasPoints(eo.getHasPoints());
					logger.info("当前账号拥有的积分："+ob.toString());
					if(pb != null) {
						try {
							InvitedCode ic = gameInviteServiceImpl.getInvitedCodeByPlayerIdAndGameIdAndActId(eo.getPlayerId(), gameId, actId);
							logger.info("查询  [getInvitedCodeByPlayerIdAndGameIdAndActId] 参数："+eo.getPlayerId()+"  "+gameId+"  "+actId );
							
							if(ic != null) {
								ob.setInvitedPeople(ic.getApplyCount()+"");
								logger.info("查询的次数为： "+ic.getApplyCount());
								logger.info("查询的结果为： "+ic.toString());
							} else {
								ob.setInvitedPeople("未查询到使用次数");
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						ob.setInvitedPeople("未查询到使用次数");
					}
					list.add(ob);
				}
				model.addAttribute("list", list);
				
		}catch (RemoteLookupFailureException e) {
			logger.error("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "payedorderlist";
		}catch (RemoteAccessException e) {
			logger.error("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "payedorderlist";
		}catch (ConnectException e) {
			logger.error("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI连接失败\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpay RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "payedorderlist";
		}

		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("type", type);
		
		model.addAttribute("currentGameName", gameNameArr[0]);
		return "payedorderlist";
	}
	/**
	 * 
	 * 方法功能说明：查询已通过审核的订单
	 * 创建：2017年5月2日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param page
	 * @参数： @param pageSize
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/orderpaysearch", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String orderpaysearch(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			HttpServletRequest request, Model model) {
		logger.info("OrderController  -->   【/promoter/orderpay】");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();// 查询当前登录用户名
		// 获取拥有权限的游戏名列表
		String gameNames = gameConfigService.selectUserGameType(userName);

		String[] gameNameArr = null;
		if (gameNames == null || gameNames.equals("")) {
			logger.info("当前账号可操作的游戏为空  ");
			return "passwedorder";
		} else {
			gameNameArr = gameNames.split(",");
		}
		String searchGameName = request.getParameter("searchGameName");
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		// 根据游戏名称查询游戏对应的id
		int gameId = Integer.parseInt(gameConfigService.getGameIdByGameName(searchGameName));
		logger.info("==gameId=" + gameId);
		PromoterBean pb = gameConfigService.selectPromoterByGameIdAndLocation(gameId+"", "中国大陆");
		int actId = Integer.MAX_VALUE;;
		try {
			actId = Integer.parseInt(pb.getId());
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
    	}
		int rowCount = 0;
		try{
			//获取已通过订单总行数
			rowCount = exchRmiServiceImpl.getExchangeOrderTotalNum(gameId, "PASSED");
			logger.info("游戏："+gameId+"已通过订单总行数 ："+rowCount);
		}catch(RemoteLookupFailureException e) {
			logger.error("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "passwedorder";
		} catch(RemoteAccessException e) {
			logger.error("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "passwedorder";
		} catch (ConnectException e) {
			logger.error("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "passwedorder";
		}
		int pages = 0;
		if (rowCount % pageSize == 0)
			pages = rowCount / pageSize;
		else
			pages = rowCount / pageSize + 1;

		List<ExchangeOrder> listOrg = null;
		try {
				listOrg = exchRmiServiceImpl.getExchangeOrderBystatus(gameId, "PASSED", ((page - 1) * pageSize), pageSize);
		}catch (RemoteLookupFailureException e) {
			logger.error("/promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "passwedorder";
		}catch (RemoteAccessException e) {
			logger.error("/promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch /promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "passwedorder";
		}catch (ConnectException e) {
			logger.error("/promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI连接失败\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "passwedorder";
		}
		List<OrderBean> list = new ArrayList<OrderBean>();
		for (ExchangeOrder eo : listOrg) {
			OrderBean ob = new OrderBean();
			ob.setId(eo.getId());
			ob.setGameId(eo.getGameId());
			ob.setPlayerId(eo.getPlayerId());
			if(searchGameName != null && !"".equals(searchGameName))
			    ob.setGameName(searchGameName);
			else
				ob.setGameName(gameNameArr[0]);
			ob.setLevel(eo.getLevel());
			ob.setOrderCreatedDate(DateUtil.parseDate(Long.parseLong(eo
					.getCreateAt() + "") * 1000));
			ob.setHasPoints(eo.getHasPoints());
			ob.setOrderId(eo.getOrderId());
			ob.setOperationMemo(eo.getOperationMemo());
			ob.setOnlineTime(eo.getOnlineTime());
			ob.setAmount(eo.getAmount());
			ob.setRequestExchangePoints(eo.getRequestExchangePoints());
			ob.setPayInfo(eo.getPayInfo());
			ob.setStatus(eo.getStatus());
			ob.setUserMemo(eo.getUserMemo());
			if(pb != null) {
				try {
					InvitedCode ic = gameInviteServiceImpl.getInvitedCodeByPlayerIdAndGameIdAndActId(eo.getPlayerId(), gameId, actId);
					logger.info("查询  [getInvitedCodeByPlayerIdAndGameIdAndActId] 参数："+eo.getPlayerId()+"  "+gameId+"  "+actId );
					
					if(ic != null) {
						ob.setInvitedPeople(ic.getApplyCount()+"");
						logger.info("查询的次数为： "+ic.getApplyCount());
						logger.info("查询的结果为： "+ic.toString());
					} else {
						ob.setInvitedPeople("未查询到使用次数");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				ob.setInvitedPeople("未查询到使用次数");
			}
			list.add(ob);
		}
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("list", list);
		model.addAttribute("searchGameName", searchGameName);
		
		return "passwedorder";
	}

	/**
	 * 
	 * 方法功能说明：查询已支付的订单
	 * 创建：2017年5月2日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param page
	 * @参数： @param pageSize
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/payedorderlistsearch", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String payedorderlistsearch(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			HttpServletRequest request, Model model) {
		logger.info("OrderController  -->   【/promoter/payedorderlistsearch】");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();// 查询当前登录用户名
		// 获取拥有权限的游戏名列表
		String gameNames = gameConfigService.selectUserGameType(userName);
		String type = request.getParameter("type");
		String msg = request.getParameter("msg");
		
		String status = "PAID";
		if(type != null && type.equals("2"))
			status = "REJECTED";
		else if(type != null && type.equals("3"))
			status = "INVALID";

		String[] gameNameArr = null;
		if (gameNames == null || gameNames.equals("")) {
			logger.info("当前账号可操作的游戏为空  ");
			return "payedorderlist";
		} else {
			gameNameArr = gameNames.split(",");
		}
		String searchGameName = request.getParameter("searchGameName");
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		// 根据游戏名称查询游戏对应的id
		int gameId = Integer.parseInt(gameConfigService.getGameIdByGameName(searchGameName));
		logger.info("==gameId=" + gameId);
		PromoterBean pb = gameConfigService.selectPromoterByGameIdAndLocation(gameId+"", "中国大陆");
		int actId = Integer.MAX_VALUE;;
		try {
			actId = Integer.parseInt(pb.getId());
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
    	}
		int rowCount = 0;
		try{
			//获取已通过订单总行数
			rowCount = exchRmiServiceImpl.getExchangeOrderTotalNum(gameId, status);
			logger.info("游戏："+gameId+"已通过订单总行数 ："+rowCount);
		}catch(RemoteLookupFailureException e) {
			logger.error("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "payedorderlist";
		} catch(RemoteAccessException e) {
			logger.error("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "payedorderlist";
		} catch (ConnectException e) {
			logger.error("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch RMI调用远程接口【getExchangeOrderTotalNum】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "payedorderlist";
		}
		int pages = 0;
		if (rowCount % pageSize == 0)
			pages = rowCount / pageSize;
		else
			pages = rowCount / pageSize + 1;

		List<ExchangeOrder> listOrg = null;
		try {
				listOrg = exchRmiServiceImpl.getExchangeOrderBystatus(gameId, status, ((page - 1) * pageSize), pageSize);
				List<OrderBean> list = new ArrayList<OrderBean>();
				for (ExchangeOrder eo : listOrg) {
					OrderBean ob = new OrderBean();
					ob.setId(eo.getId());
					ob.setGameId(eo.getGameId());
					ob.setPlayerId(eo.getPlayerId());
					if(searchGameName != null && !"".equals(searchGameName))
					    ob.setGameName(searchGameName);
					else
						ob.setGameName(gameNameArr[0]);
					ob.setLevel(eo.getLevel());
					ob.setOrderCreatedDate(DateUtil.parseDate(Long.parseLong(eo
							.getCreateAt() + "") * 1000));
					ob.setHasPoints(eo.getHasPoints());
					ob.setOrderId(eo.getOrderId());
					ob.setOnlineTime(eo.getOnlineTime());
					ob.setOperationMemo(eo.getOperationMemo());
					ob.setAmount(eo.getAmount());
					ob.setRequestExchangePoints(eo.getRequestExchangePoints());
					ob.setPayInfo(eo.getPayInfo());
					ob.setStatus(eo.getStatus());
					ob.setUserMemo(eo.getUserMemo());
					ob.setHasPoints(eo.getHasPoints());
					if(pb != null) {
						try {
							InvitedCode ic = gameInviteServiceImpl.getInvitedCodeByPlayerIdAndGameIdAndActId(eo.getPlayerId(), gameId, actId);
							logger.info("查询  [getInvitedCodeByPlayerIdAndGameIdAndActId] 参数："+eo.getPlayerId()+"  "+gameId+"  "+actId );
							
							if(ic != null) {
								ob.setInvitedPeople(ic.getApplyCount()+"");
								logger.info("查询的次数为： "+ic.getApplyCount());
								logger.info("查询的结果为： "+ic.toString());
							} else {
								ob.setInvitedPeople("未查询到使用次数");
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						ob.setInvitedPeople("未查询到使用次数");
					}
					list.add(ob);
				}
				model.addAttribute("list", list);							
				
		}catch (RemoteLookupFailureException e) {
			logger.error("/promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
			return "payedorderlist";
		}catch (RemoteAccessException e) {
			logger.error("/promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch /promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "payedorderlist";
		}catch (ConnectException e) {
			logger.error("/promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI连接失败\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/orderpaysearch RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());				
			return "payedorderlist";
		}

		model.addAttribute("msg", msg);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("type", type);
		model.addAttribute("searchGameName", searchGameName);
		return "payedorderlist";
	}
	/**
	 * 
	 * 方法功能说明：短信回复
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
	 * 创建：2017年5月5日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/shortmessage", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String sendShortMessage(HttpServletRequest request,Model model) {
		logger.info("GameConfigController  -->   【/promoter/shortmessage】");
		//********************************************************************************//
		String cellphone = request.getParameter("cellphone");
		String gameId = request.getParameter("gameId");
		String gameName = request.getParameter("gameName");
		int page= Integer.parseInt(request.getParameter("page"));
		String searchGameName = request.getParameter("searchGameName");
		String type = request.getParameter("type");
		String pageSize = request.getParameter("pageSize");
		logger.info("page="+page+"   pageSize="+pageSize);
		String content = request.getParameter("content");
		logger.info("回复的信息是： "+content);
		
		logger.info("查询到的手机号： "+cellphone);
		String msg="短信发送失败";
		//对手机号进行校验 ^1[3|4|5|7|8][0-9]{9}$
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher m = p.matcher(cellphone); 
		
		if(m.matches()) {
			//手机号验证通过 则发送短信
			String msgResult = userInfoService.sendShortMsg(cellphone, content);
			//发送成功返回结果：  {"ret":"0","error_code":"0","data":"0"}
			if(msgResult != null) {
				JSONObject json = JSONObject.parseObject(msgResult);
				logger.info("发送短信后返回的消息： "+msgResult);
				if(json != null) {
					if(json.get("ret").equals("0")) {
						msg="短信发送成功！";
					} 
				}
			}
			
		} else {
			msg="用户手机号不正确，未发送 ："+cellphone;
			logger.info("手机号验证不通过： "+cellphone);
		}
		
//废弃通过用户playerid查询用户手机号的方式		
/*		
		String result = userInfoService.getUserInfo(playerId, gameId);
		logger.info("访问获取的信息为："+result);
		//短信接口测试
		//String msgResulttest = userInfoService.sendShortMsg("15728046545", content);
		//logger.info("发送短信测试结果： "+msgResulttest);
		
		JSONObject json1 = JSONObject.parseObject(result);
		if(json1 != null) {
			if(json1.get("data") != null && !json1.get("data").toString().equals("") ) {
				String jsondata = json1.get("data").toString();
				
				JSONObject json2 = JSONObject.parseObject(jsondata);
				if(json2 != null) {
					if(json2.get("mobile") != null && !json2.get("mobile").toString().equals("") ) {
						String mobile = json2.get("mobile").toString();
						logger.info("查询到的手机号： "+mobile);
						//对手机号进行校验 ^1[3|4|5|7|8][0-9]{9}$
						Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
						Matcher m = p.matcher(mobile); 
						
						if(m.matches()) {
							//手机号验证通过 则发送短信
							String msgResult = userInfoService.sendShortMsg(mobile, content);
							//发送成功返回结果：  {"ret":"0","error_code":"0","data":"0"}
							if(msgResult != null) {
								JSONObject json3 = JSONObject.parseObject(msgResult);
								logger.info("发送短信后返回的消息： "+msgResult);
								if(json3 != null) {
									if(json3.get("ret").equals("0")) {
										msg="短信发送成功！";
									} 
								}
							}
							
						} else {
							msg="用户手机号不正确，未发送 ："+mobile;
							logger.info("手机号验证不通过： "+mobile);
						}
					}
				}
			}
		}
    */
		return "forward:/promoter/payedorderlistsearch?searchGameName="+searchGameName+"&page="+page+"&pageSize="+pageSize+"&type="+type+"&msg="+msg;
	}
	
	
	/**
	 *导出已通过审核的订单excel 
	 * @return
	 */
	@RequestMapping(value = "/promoter/export", method = RequestMethod.GET)
	public ModelAndView getExcel(HttpServletRequest request) {
		//List<Animal> animalList = animalService.getAnimalList();
		
		logger.info("OrderController  -->   【/promoter/export】");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String searchGameName = request.getParameter("searchGameName");
		logger.info("导出时传递的游戏名称是："+searchGameName);
		String userName = userDetails.getUsername();// 查询当前登录用户名
		// 获取拥有权限的游戏名列表
		String gameNames = gameConfigService.selectUserGameType(userName);

		String[] gameNameArr = null;
		if (gameNames == null || gameNames.equals("")) {
			logger.info("当前账号可操作的游戏为空  ");
		} else {
			gameNameArr = gameNames.split(",");
		}
		//List<String> gameNameList = Arrays.asList(gameNameArr);
		// 根据游戏名称查询游戏对应的id
		String gName = gameNameArr[0];
		if(searchGameName != null && !"".equals(searchGameName))
			gName = searchGameName;
		logger.info("gName: "+gName+"  "+gameNameArr[0]);
		int gameId = Integer.parseInt(gameConfigService.getGameIdByGameName(gName));
		logger.info("======gameId=" + gameId);
		PromoterBean pb = gameConfigService.selectPromoterByGameIdAndLocation(gameId+"", "中国大陆");
		int actId = Integer.MAX_VALUE;;
		try {
			actId = Integer.parseInt(pb.getId());
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
    	}
		List<ExchangeOrder> listOrg = null;
		try {
				listOrg = exchRmiServiceImpl.getExchangeOrderBystatus(gameId, "PASSED", 0,0);
		}catch (RemoteLookupFailureException e) {
			logger.error("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());		
		}catch (RemoteAccessException e) {
			logger.error("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
		}catch (ConnectException e) {
			logger.error("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI连接失败\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
		}
		List<OrderBean> list = new ArrayList<OrderBean>();
		for (ExchangeOrder eo : listOrg) {
			OrderBean ob = new OrderBean();
			ob.setId(eo.getId());
			ob.setGameId(eo.getGameId());
			ob.setPlayerId(eo.getPlayerId());
			if(searchGameName != null && !"".equals(searchGameName))
			    ob.setGameName(searchGameName);
			else
				ob.setGameName(gameNameArr[0]);
			ob.setLevel(eo.getLevel());
			ob.setOrderCreatedDate(DateUtil.parseDate(Long.parseLong(eo
					.getCreateAt() + "") * 1000));
			ob.setHasPoints(eo.getHasPoints());
			ob.setOrderId(eo.getOrderId());
			ob.setOperationMemo(eo.getOperationMemo());
			ob.setOnlineTime(eo.getOnlineTime());
			ob.setAmount(eo.getAmount());
			ob.setRequestExchangePoints(eo.getRequestExchangePoints());
			ob.setPayInfo(eo.getPayInfo());
			ob.setStatus(eo.getStatus());
			ob.setUserMemo(eo.getUserMemo());
			if(pb != null) {
				try {
					InvitedCode ic = gameInviteServiceImpl.getInvitedCodeByPlayerIdAndGameIdAndActId(eo.getPlayerId(), gameId, actId);
					logger.info("查询  [getInvitedCodeByPlayerIdAndGameIdAndActId] 参数："+eo.getPlayerId()+"  "+gameId+"  "+actId );
					
					if(ic != null) {
						ob.setInvitedPeople(ic.getApplyCount()+"");
						logger.info("查询的次数为： "+ic.getApplyCount());
						logger.info("查询的结果为： "+ic.toString());
					} else {
						ob.setInvitedPeople("未查询到使用次数");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				ob.setInvitedPeople("未查询到使用次数");
			}
			list.add(ob);
		}
		logger.info("PassedOrderListExcel");
		return new ModelAndView("OrderListExcel", "orderList", list);
	}
	
	@RequestMapping(value = "/promoter/exportpayedorder", method = RequestMethod.GET)
	public ModelAndView getPayedOrderExcel(HttpServletRequest request) {
		//List<Animal> animalList = animalService.getAnimalList();
		
		logger.info("OrderController  -->   【/promoter/exportpayedorder】");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String searchGameName = request.getParameter("searchGameName");
		logger.info("导出时传递的游戏名称是："+searchGameName);
		String userName = userDetails.getUsername();// 查询当前登录用户名
		// 获取拥有权限的游戏名列表
		String gameNames = gameConfigService.selectUserGameType(userName);

		String[] gameNameArr = null;
		if (gameNames == null || gameNames.equals("")) {
			logger.info("当前账号可操作的游戏为空  ");
		} else {
			gameNameArr = gameNames.split(",");
		}
		
		String type = request.getParameter("type");
		
		String status = "PAID";
		if(type != null && type.equals("2"))
			status = "REJECTED";
		if(type != null && type.equals("3"))
			status = "INVALID";		
		//List<String> gameNameList = Arrays.asList(gameNameArr);
		// 根据游戏名称查询游戏对应的id
		String gName = gameNameArr[0];
		if(searchGameName != null && !"".equals(searchGameName))
			gName = searchGameName;
		logger.info("gName: "+gName+"  "+gameNameArr[0]);
		int gameId = Integer.parseInt(gameConfigService
				.getGameIdByGameName(gName));
		logger.info("======gameId=" + gameId);
		PromoterBean pb = gameConfigService.selectPromoterByGameIdAndLocation(gameId+"", "中国大陆");
		int actId = Integer.MAX_VALUE;;
		try {
			actId = Integer.parseInt(pb.getId());
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
    	}
		List<ExchangeOrder> listOrg = null;
		try {
				listOrg = exchRmiServiceImpl.getExchangeOrderBystatus(gameId, status, 0,0);
		}catch (RemoteLookupFailureException e) {
			logger.error("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());		
		}catch (RemoteAccessException e) {
			logger.error("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
		}catch (ConnectException e) {
			logger.error("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI连接失败\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/export RMI远程连接中调用 【getExchangeOrderByGameId】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
		}
		List<OrderBean> list = new ArrayList<OrderBean>();
		for (ExchangeOrder eo : listOrg) {
			OrderBean ob = new OrderBean();
			ob.setId(eo.getId());
			ob.setGameId(eo.getGameId());
			ob.setPlayerId(eo.getPlayerId());
			if(searchGameName != null && !"".equals(searchGameName))
			    ob.setGameName(searchGameName);
			else
				ob.setGameName(gameNameArr[0]);
			ob.setLevel(eo.getLevel());
			ob.setOrderCreatedDate(DateUtil.parseDate(Long.parseLong(eo
					.getCreateAt() + "") * 1000));
			ob.setHasPoints(eo.getHasPoints());
			ob.setOrderId(eo.getOrderId());
			ob.setOperationMemo(eo.getOperationMemo());
			ob.setOnlineTime(eo.getOnlineTime());
			ob.setAmount(eo.getAmount());
			ob.setRequestExchangePoints(eo.getRequestExchangePoints());
			ob.setPayInfo(eo.getPayInfo());
			ob.setStatus(eo.getStatus());
			ob.setUserMemo(eo.getUserMemo());
			if(pb != null) {
				try {
					InvitedCode ic = gameInviteServiceImpl.getInvitedCodeByPlayerIdAndGameIdAndActId(eo.getPlayerId(), gameId, actId);
					logger.info("查询  [getInvitedCodeByPlayerIdAndGameIdAndActId] 参数："+eo.getPlayerId()+"  "+gameId+"  "+actId );
					
					if(ic != null) {
						ob.setInvitedPeople(ic.getApplyCount()+"");
						logger.info("查询的次数为： "+ic.getApplyCount());
						logger.info("查询的结果为： "+ic.toString());
					} else {
						ob.setInvitedPeople("未查询到使用次数");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				ob.setInvitedPeople("未查询到使用次数");
			}
			list.add(ob);
		}
		logger.info("PayedOrderListExcel");
		return new ModelAndView("PayedOrderListExcel", "payedOrderList", list);
	}
	
	//处理Excel文件上传
	 /** 
	 * 暂时不使用这个方法  
     * 描述：通过传统方式form表单提交方式导入excel文件  
     * @param request  
     * @throws Exception  
     */  
    @RequestMapping(value="/promoter/upload",method={RequestMethod.GET,RequestMethod.POST})  
    public  String  uploadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
        logger.info("通过传统方式form表单提交方式导入excel文件！");  
        InputStream in =null;  
        List<List<Object>> listob = null;  
        MultipartFile file = multipartRequest.getFile("upfile");
        logger.info("获取文件完毕！");
        if(file.isEmpty()){  
            throw new Exception("文件不存在！");  
        }  
        in = file.getInputStream();  
        listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());  
        in.close();  
          
        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出  
        String[] orderIds = new String[listob.size()]; 
        for (int i = 0; i < listob.size(); i++) {  
            List<Object> lo = listob.get(i);  
            OrderBean ob = new OrderBean();  
            ob.setGameName(String.valueOf(lo.get(0)));
            ob.setUserMemo(String.valueOf(lo.get(1)));
            ob.setOrderId(String.valueOf(lo.get(2)));
            ob.setPayInfo(String.valueOf(lo.get(3)));
            ob.setRequestExchangePoints(Integer.getInteger(String.valueOf(lo.get(4))));
            ob.setStatus(String.valueOf(lo.get(5)));
            ob.setAmount(Integer.parseInt(String.valueOf(lo.get(7))));
            logger.info("打印信息-->游戏名:"+ob.getGameName()+"  订单编号："+ob.getOrderId()+"   支付宝账号："+ob.getPayInfo()+"   金额："+ob.getAmount()+"  "+String.valueOf(lo.get(6))+" "+String.valueOf(lo.get(7)));  
        }
        PrintWriter out = null;  
        response.setCharacterEncoding("utf-8");  //防止ajax接受到的中文信息乱码  
        out = response.getWriter();
        try {
        	//已支付订单提交
			exchRmiServiceImpl.updateExchangeOrderStatusByQuantity(orderIds, "PAID");
			out.print("文件导入成功！"); 
		} catch(RemoteLookupFailureException e) {
			logger.error("/promoter/upload RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/upload RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
	        out.print("文件导入失败，请重新提交！"); 
	        out.flush();  
	        out.close();

		} catch(RemoteAccessException e) {
			logger.error("RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/upload RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
	        out.print("文件导入失败，请重新提交！"); 
	        out.flush();  
	        out.close();

		} catch (ConnectException e) {
			logger.error("RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/upload RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
	        out.print("文件导入失败，请重新提交！");
	        out.flush();  
	        out.close();

		}
        return "payedordersubmit";
    }  
      
    /** 
     * 描述：通过 jquery.form.js 插件提供的ajax方式上传文件 
     * @param request 
     * @param response 
     * @throws Exception 
     */  
    @ResponseBody  
    @RequestMapping(value="/promoter/ajaxUpload",method={RequestMethod.GET,RequestMethod.POST})  
    public  void  ajaxUploadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	logger.info("OrderController  -->   【/promoter/ajaxUpload】");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        logger.info("通过 jquery.form.js 提供的ajax方式上传文件！");  
          
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
        
        String[] orderIds = new String[listob.size()];  
        //该处可调用service相应方法进行数据保存到数据库中，  
        for (int i = 0; i < listob.size(); i++) {  
        	logger.info("将数据组合成数组形式！");
            List<Object> lo = listob.get(i);  
            OrderBean ob = new OrderBean();  
            ob.setGameName(String.valueOf(lo.get(0)));
            ob.setUserMemo(String.valueOf(lo.get(1)));
            ob.setOrderId(String.valueOf(lo.get(2)));
            orderIds[i]=ob.getOrderId();
            ob.setPayInfo(String.valueOf(lo.get(3)));
            ob.setRequestExchangePoints(Integer.getInteger(String.valueOf(lo.get(4))));
            ob.setStatus(String.valueOf(lo.get(5)));
            ob.setAmount(Integer.parseInt(String.valueOf(lo.get(7))));
            logger.info("打印信息-->游戏名:"+ob.getGameName()+"  订单编号："+ob.getOrderId()+"   支付宝账号："+ob.getPayInfo()+"   金额："+ob.getAmount()+"  "+String.valueOf(lo.get(6))+" "+String.valueOf(lo.get(7)));  
        }
        PrintWriter out = null;  
        response.setCharacterEncoding("utf-8");  //防止ajax接受到的中文信息乱码  
        out = response.getWriter();
        try {
        	//已支付订单提交
        	logger.info("开始调用更改订单的RMI方法  updateExchangeOrderStatusByQuantity");
			exchRmiServiceImpl.updateExchangeOrderStatusByQuantity(orderIds, "PAID");
			logger.info("调用方法结束");
		} catch(RemoteLookupFailureException e) {
			logger.error("/promoter/ajaxUpload RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/ajaxUpload RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
	        out.print("文件导入失败，请重新提交！");  
	        out.flush();  
	        out.close(); 
		} catch(RemoteAccessException e) {
			logger.error("/promoter/ajaxUpload RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常 远程RMI未启动\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/ajaxUpload RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常 远程RMI未启动\n"
					+ e.getMessage());			
	        out.print("文件导入失败，请重新提交！");  
	        out.flush();  
	        out.close();
		} catch (ConnectException e) {
			logger.error("/promoter/ajaxUpload RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/promoter/ajaxUpload RMI调用远程接口【updateExchangeOrderStatusByQuantity】出现异常 远程RMI未启动\n"
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
    
	@RequestMapping(value = "/promoter/payedorder", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String payedorder(HttpServletRequest request, Model model) {
		logger.info("OrderController  -->   【/promoter/payedorder】");
		return "payedordersubmit";
	}
	/**
	 * 
	 * 方法功能说明：通过userId和gameId获取用户信息
	 * 创建：2017年5月4日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/getuserinfo", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getUserInfo(HttpServletRequest request, Model model) {
		logger.info("OrderController  -->   【/promoter/getuserinfo】");
		String userId = request.getParameter("uid").toString();
		String gameId = request.getParameter("gid").toString();
		logger.info("传递的uid="+userId+"  gid="+gameId);
		String result = userInfoService.getUserInfo(userId, gameId);
		logger.info("访问获取的信息为："+result);
		return result;
	}
	/**
	 * 
	 * 方法功能说明：通过邀请码获取用户信息
	 * 创建：2017年5月4日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/getuserinfowithredeemcode", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getUserInfoWithRedeemcode(HttpServletRequest request, Model model) {
		logger.info("OrderController  -->   【/promoter/getuserinfowithredeemcode】");
		String redeemcode = request.getParameter("redeemcode").toString();
		String gameId = request.getParameter("gid").toString();
		String userId = null;
		String result="{\"code\":-1,\"msg\":\"created by configservice!\",\"data\":\"\",\"ext\":\"\"}";
		if(redeemcode != null) {
			InvitedCode ic;
			try {
				ic = gameInviteServiceImpl.getInvitedCodeByCode(redeemcode);
				if(ic != null) {
					logger.info("通过邀请码查询的用户信息为："+ic.toString());
					userId = ic.getPlayerId()+"";
					logger.info("通过查询兑换码查询的用户ID是： "+userId);
					result = userInfoService.getUserInfo(userId, gameId);
				} else {
					logger.info("通过邀请码："+redeemcode+" 查询的用户信息为null");
				}
			} catch (Exception e) {
				logger.error("访问RMI获取用户信息出现异常：getInvitedCodeByCode()传递的参数为： "+redeemcode);
				e.printStackTrace();
			}
		}
 		
		logger.info("查询的uid="+userId+"  gid="+gameId);
		logger.info("访问获取的信息为："+result);
		return result;
	}
	/**
	 * 
	 * 方法功能说明：用于用户查询页面
	 * 创建：2017年1月12日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/playersearch", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String playerSearch(HttpServletRequest request,Model model) {
		logger.info("OrderController  -->   【/promoter/playersearch】");
		String actId = request.getParameter("actId").trim();
		String gameId = request.getParameter("gameId").trim();
		String gameName = request.getParameter("gameName").trim();
		String playerId = request.getParameter("playerId");
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    

		model.addAttribute("actId", actId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		model.addAttribute("playerId", playerId);
		if(playerId == null || "".equals(playerId)) {
			return "playersearch";
		}else {
			if(!pattern.matcher(playerId).matches()) {
				model.addAttribute("playererror", "输入的playerId不合法");
				logger.info("输入的playerId非法 "+playerId);
				return "playersearch";
			}
			String result = userInfoService.getUserInfo(playerId, gameId);
			logger.info("收到的返回结果是： "+result);
			JSONObject jb = (JSONObject) com.alibaba.fastjson.JSON.parse(result);
			String level ="未查到等级信息";
			String onlineTime ="未查到在线时长信息";
			String amount ="未查到充值金额信息";
			if(jb.get("data") != null && !"".equals(jb.get("data"))) {
				JSONObject json = (JSONObject) jb.get("data");
				if(json != null) {
					level = json.getString("level");
					onlineTime = json.getString("onlinetime");
					amount = json.getString("amount");
				}
				logger.info("level= "+level+"  onlinetime= "+onlineTime+"  amount= "+amount);

			}
			PlayerSearchBean psb = new PlayerSearchBean(gameId,playerId,onlineTime,amount,gameName,level);
			List<PlayerSearchBean> list = new ArrayList<PlayerSearchBean>();
			list.add(psb);
			model.addAttribute("list", list);
		}
		return "playersearch";
	}
	
	/**
	 * 
	 * 方法功能说明：用于回复用户的吐槽功能
	 * 创建：2017年1月19日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/forum", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String forum(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
			HttpServletRequest request,Model model) {
		logger.info("OrderController  -->   【/promoter/forum】");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();		
		String userName = userDetails.getUsername();// 查询当前登录用户名
		// 获取拥有权限的游戏名列表
		String gameNames = gameConfigService.selectUserGameType(userName);

		String[] gameNameArr = null;
		if (gameNames == null || gameNames.equals("")) {
			logger.info("当前账号可操作的游戏为空  ");
			return "order";
		} else {
			gameNameArr = gameNames.split(",");
		}
		List<String> gameNameList = Arrays.asList(gameNameArr);
		model.addAttribute("gameNameList", gameNameList);
		
		String actId = request.getParameter("actId");
		String gameId = request.getParameter("gameId");
		String gameName = request.getParameter("gameName");
		logger.info("查询的游戏名称是： "+gameName+"  游戏Id是： "+gameId+"  查询的活动Id: "+actId);
		if(gameName == null || gameName.equals("")) {
			if(gameNameArr != null && gameNameArr.length > 0)
				gameName = gameNameArr[0];
		}
		if(gameId == null || gameId.equals("")) {
			gameId = gameConfigService.getGameIdByGameName(gameName);
			PromoterBean pb = gameConfigService.selectPromoterByGameIdAndLocation(gameId, "中国大陆");
			if(pb != null)
				actId = pb.getId();
			logger.info("当前游戏名称是 ："+gameName+"  游戏Id： "+gameId);
			logger.info("查询到的PromoterBean是 ："+pb.toString());
		}
		
		model.addAttribute("actId", actId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		model.addAttribute("searchGameName", gameName);
		
		if(gameId == null || "".equals(gameId)) {
			logger.info("查询的gameId是  : "+gameId);
			return "commentlist";
		}else {
			
			String result = userInfoService.getForumInfo(actId, gameId, (page - 1) * pageSize, pageSize);
			logger.info("收到的返回结果是： "+result);
			JSONObject jb = (JSONObject) com.alibaba.fastjson.JSON.parse(result);
			String pages ="";
			String totalCount="";
			JSONObject json1 = null;
			
			if(jb != null) {
				if(jb.get("data") != null && !"".equals(jb.get("data"))) {
					json1 = (JSONObject) jb.get("data");
					logger.info(json1.toJSONString());
					if(json1 != null) {
						pages = json1.getString("totalPage");
						totalCount = json1.getString("totalRecord");
						model.addAttribute("pages", pages);
						model.addAttribute("totalCount", totalCount);
					}
					logger.info("page="+page+"  pages= "+pages+"  totalCount= "+totalCount);
				}
			} else {
				return "commentlist";
			}
			ForumBean fb = null;
			List<ForumBean> list = null;
			JSONArray  json2 = null;
			if(json1.get("pageContent") != null && !"".equals(json1.get("pageContent"))) {
				logger.info("进入pageContent...");
				list = new ArrayList<ForumBean>();
				json2 =  (JSONArray) json1.get("pageContent");
				if(json2 != null) {
					for(int i=0;i<json2.size();i++) {
						
						JSONObject jObject = json2.getJSONObject(i);
						String  uid = jObject.getString("uid");
						String extend = jObject.getString("extend");//暂时未使用
						String cid = jObject.getString("cid");
						String nickname = jObject.getString("nickname");
						String content = jObject.getString("content");
						String gid = jObject.getString("gid");
						String tid = jObject.getString("tid");
						String cstat = jObject.getString("cstat");
						String createtime = jObject.getString("createtime");
						int isTop = jObject.getIntValue("istop");						
						logger.info("uid: "+uid+" cid: "+cid+" nickname: "+nickname);
						List<ReplyForumBean> replyList = null;
						if(jObject.get("replies") != null && !"".equals(jObject.get("replies"))) {
							String replys = jObject.get("replies").toString();
							logger.info("返回的回复信息是： "+replys);
							replyList = com.alibaba.fastjson.JSON.parseArray(replys, ReplyForumBean.class);
							logger.info("replyList= "+replyList.size());
							if(replyList.size()>=1)
								logger.info("取回复信息中的第一个 -> 测试： "+replyList.get(0).getFrom_nickname());
						}
						fb = new ForumBean(uid, cid, content, nickname, gid, createtime, cstat,tid,extend,isTop);
						fb.setRfb(replyList);
						list.add(fb);
					}
				} else {
					logger.info(json1.get("pageContent").toString()+" ...");
				}
			}else {
				logger.info(json1.get("pageContent").toString());
			}
			model.addAttribute("list", list);
		}
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		return "commentlist";
	}
	
	@RequestMapping(value = "/promoter/forumreply", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String forumReply(HttpServletRequest request,Model model) {
		logger.info("GameConfigController  -->   【/promoter/forumreply】");
		String actId = request.getParameter("actId");
		String gameId = request.getParameter("gameId");
		String gameName = request.getParameter("gameName");
		int page= Integer.parseInt(request.getParameter("page"));
		
		String pageSize = request.getParameter("pageSize");
		logger.info("page="+page+"   pageSize="+pageSize);
		String from_uid = request.getParameter("from_uid");
		String to_uid = request.getParameter("to_uid");
		String from_nickname = request.getParameter("from_nickname");
		String to_nickname = request.getParameter("to_nickname");
		String cid = request.getParameter("cid");
		String content = request.getParameter("content");
		logger.info("回复的信息是： "+content+"  cid: "+cid);
		
		model.addAttribute("actId", actId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		
		String result = userInfoService.replyForum(actId, gameId, content,cid, from_uid, to_uid, from_nickname, to_nickname);
		logger.info("回复吐槽后的返回信息是： "+result);

		return "forward:/promoter/forum?page="+page+"&pageSize="+pageSize+"&actId="+actId+"&gameId="+gameId+"&gameName="+gameName;
	}
	
	/**
	 * 
	 * 方法功能说明：处理置顶和取消置顶
	 * 创建：2017年4月28日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/move2top", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String move2top(HttpServletRequest request,Model model) {
		logger.info("GameConfigController  -->   【/promoter/move2top】");
		String actId = request.getParameter("actId");
		String gameId = request.getParameter("gameId");
		String gameName = request.getParameter("gameName");
		int page= Integer.parseInt(request.getParameter("page"));
		String pageSize = request.getParameter("pageSize");
		String type = request.getParameter("type");
		logger.info("page="+page+"   pageSize="+pageSize+"  请求类型： "+type);

		String cid = request.getParameter("cid");

		logger.info("置顶的cid： "+cid);
		
		model.addAttribute("actId", actId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		if(type.equals("1")) {
			String result = userInfoService.move2top(cid);
			logger.info("置顶吐槽后的返回信息是： "+result);
		} else if(type.equals("2")) {
			String result = userInfoService.cancleMove2top(cid);
			logger.info("取消置顶后的返回信息是： "+result);
		}
		return "forward:/promoter/forum?page="+page+"&pageSize="+pageSize+"&actId="+actId+"&gameId="+gameId+"&gameName="+gameName;
	}
	/**
	 * 
	 * 方法功能说明：处理某条删除吐槽
	 * 创建：2017年4月28日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/promoter/deleteforum", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String deleteforum(HttpServletRequest request,Model model) {
		logger.info("GameConfigController  -->   【/promoter/deleteforum】");
		String actId = request.getParameter("actId");
		String gameId = request.getParameter("gameId");
		String gameName = request.getParameter("gameName");
		int page= Integer.parseInt(request.getParameter("page"));
		String pageSize = request.getParameter("pageSize");
		logger.info("page="+page+"   pageSize="+pageSize);

		String cid = request.getParameter("cid");

		logger.info("删除的cid： "+cid);
		
		model.addAttribute("actId", actId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		String result = userInfoService.deleteforum(cid);
		logger.info("删除吐槽后的返回信息是： "+result);
	
		return "forward:/promoter/forum?page="+page+"&pageSize="+pageSize+"&actId="+actId+"&gameId="+gameId+"&gameName="+gameName;
	}
	
	@RequestMapping(value = "/promoter/deleteforumreply", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String deleteforumreply(HttpServletRequest request,Model model) {
		logger.info("GameConfigController  -->   【/promoter/deleteforumreply】");
		String actId = request.getParameter("actId");
		String gameId = request.getParameter("gameId");
		String gameName = request.getParameter("gameName");
		int page= Integer.parseInt(request.getParameter("page"));
		String pageSize = request.getParameter("pageSize");
		logger.info("page="+page+"   pageSize="+pageSize);

		String rid = request.getParameter("rid");

		logger.info("删除的rid： "+rid);
		
		model.addAttribute("actId", actId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		String result = userInfoService.deleteforumreply(rid);
		logger.info("删除吐槽回复后的返回信息是： "+result);
	
		return "forward:/promoter/forum?page="+page+"&pageSize="+pageSize+"&actId="+actId+"&gameId="+gameId+"&gameName="+gameName;
	}
	
	@RequestMapping(value = "/promoter/updateforumreply", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String updateforumreply(HttpServletRequest request,Model model) {
		logger.info("GameConfigController  -->   【/promoter/updateforumreply】");
		String actId = request.getParameter("actId");
		String gameId = request.getParameter("gameId");
		String gameName = request.getParameter("gameName");
		int page= Integer.parseInt(request.getParameter("page"));
		String pageSize = request.getParameter("pageSize");
		logger.info("page="+page+"   pageSize="+pageSize);

		String rid = request.getParameter("rid");
		String content = request.getParameter("content");
		if(content != null && !"".equals(content)) {
			String result = userInfoService.updateforumreply(rid, content);
			logger.info("删除吐槽回复后的返回信息是： "+result);
		}

		logger.info("修改的rid： "+rid);
		
		model.addAttribute("actId", actId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
	
		return "forward:/promoter/forum?page="+page+"&pageSize="+pageSize+"&actId="+actId+"&gameId="+gameId+"&gameName="+gameName;
	}
    


}
