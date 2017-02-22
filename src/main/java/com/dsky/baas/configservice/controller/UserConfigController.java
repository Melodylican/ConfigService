package com.dsky.baas.configservice.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.dsky.baas.configservice.logservice.IWarningReporterService;
import com.dsky.baas.configservice.model.GameTypeBean;
import com.dsky.baas.configservice.model.UserBean;
import com.dsky.baas.configservice.service.IGameConfigService;

/**
 * @ClassName: UserConfigController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
@Controller
public class UserConfigController {
	private static final Logger logger = Logger
			.getLogger(UserConfigController.class);
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
	 * 进入创建用户页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/create", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String createUser(Model model) {
		logger.info("UserConfigController  -->   【/user/create】");
		List<GameTypeBean> list = gameConfigService.selectGameType();
		model.addAttribute("list", list);
		return "createuser";
	}

	/**
	 * 根据用户名查询用户
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/search", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String userSearch(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/search】");
		String userName = request.getParameter("userName");
		logger.info("查询用户的用户名为【" + userName + "】");
		try {
			List<UserBean> list = gameConfigService
					.selectUserByUserName(userName);
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error("/user/search 根据用户名查询【" + userName + "】用户时出现异常：\n" + e.getMessage(),e);
			warningReporterService.reportWarnString("/user/search 根据用户名查询【" + userName + "】用户时出现异常：\n" + e.getMessage());
		}
		model.addAttribute("search", "search");
		return "user";
	}

	/**
	 * 更新用户配置信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/update", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String updateUser(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/update】");
		// 获得总行数
		UserBean userBean = JSONObject.parseObject(
				(request.getParameter("userBean")), UserBean.class);
		try {
			List<GameTypeBean> list = gameConfigService.selectGameType();
			model.addAttribute("list", list);
			model.addAttribute("userBean", userBean);
			logger.info("================userBean gameType"
					+ userBean.getGameType());
			// 数据入库
		} catch (Exception e) {
			logger.error("/user/update 更新用户【" + userBean.getUserName() + "】时出现异常:\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/user/update 更新用户【" + userBean.getUserName() + "】时出现异常:\n"
					+ e.getMessage());
		}
		return "updateuser";
	}

	/**
	 * 保存更新用户信息入库
	 * 
	 * @param userBean
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/saveupdate", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveUserUpdate(@ModelAttribute("userBean") UserBean userBean,
			Model model) throws InterruptedException {

		logger.info("UserConfigController  -->   【/user/saveupdate】");
		int i = gameConfigService.updateUserByUserName(userBean);
		List<GameTypeBean> list = gameConfigService.selectGameType();
		model.addAttribute("list", list);

		if (i != 0) {
			model.addAttribute("updateMsg", "保存成功！");
		} else {
			model.addAttribute("updateMsg", "保存失败,请重新保存！");
		}
		return "updateuser";
	}

	/**
	 * 保存创建用户信息入库
	 * 
	 * @param userBean
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/saveusercreate", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveUserCreate(@ModelAttribute("userBean") UserBean userBean,
			Model model) throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/saveusercreate】");
		int i = gameConfigService.insertUser(userBean);
		if (i != 0) {
			model.addAttribute("insertMsg", "新建成功！");
		} else {
			model.addAttribute("insertMsg", "新建失败,请重新创建！");
		}
		model.addAttribute("userBean", userBean);
		List<GameTypeBean> list = gameConfigService.selectGameType();
		model.addAttribute("list", list);
		return "updateuser";
	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String deleteUser(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/delete】");
		String userName = request.getParameter("userName").toString();
		int i = gameConfigService.deleteUserByUserName(userName);
		if (i != 0) {
			model.addAttribute("deleteMsg", "删除成功！");
		} else {
			model.addAttribute("deleteMsg", "删除失败,请重新删除！");
		}

		return "redirect:/user/user";
	}

	/**
	 * 更新用户
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/state", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String userState(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/state】");
		String userName = request.getParameter("userName").toString();
		int state = Integer.parseInt(request.getParameter("state").toString());
		int page = Integer.parseInt(request.getParameter("page").toString());
		int pageSize = Integer.parseInt(request.getParameter("pageSize")
				.toString());

		logger.info("page =" + page + "   pageSize =" + pageSize);
		String msg = "";
		if (state == 1)
			msg = "关闭成功！";
		else
			msg = "开启成功！";
		int updateStateMsg = gameConfigService.updateUserState(userName,
				state ^ 1);
		if (updateStateMsg > 0)
			model.addAttribute("updateStateMsg", msg);
		return "redirect:/user/user?page=" + page + "&pageSize=" + pageSize;
	}

	/**
	 * 进入用户信息总页面
	 * 
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/user", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String userCenter(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
			HttpServletRequest request, Model model) {
		logger.info("UserConfigController  -->   【/user/user】");
		List<UserBean> list = gameConfigService.selectUserByPaging(
				((page - 1) * pageSize), pageSize);
		// 获得总行数
		int rowCount = gameConfigService.getUserTotalRows();
		model.addAttribute("list", list);
		int pages = 0;
		if (rowCount % pageSize == 0)
			pages = rowCount / pageSize;
		else
			pages = rowCount / pageSize + 1;
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);

		return "user";
	}

}
