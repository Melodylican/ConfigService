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
import com.dsky.baas.configservice.model.GameBean;
import com.dsky.baas.configservice.model.GameTypeBean;
import com.dsky.baas.configservice.model.UserBean;
import com.dsky.baas.configservice.service.IGameConfigService;
/**
 * @ClassName: GameController
 * @Description: TODO(用于游戏管理)
 * @author Chris.li
 */
@Controller
public class GameController {
	private static final Logger logger = Logger.getLogger(GameController.class);
	@Resource
	private IGameConfigService gameConfigService;

	@Autowired
	public void setGameConfigService(IGameConfigService gameConfigService) {
		this.gameConfigService = gameConfigService;
	}

	/**
	 * 进入创建游戏页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/game/create", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String createUser(Model model) {
		logger.info("GameController  -->   【/game/create】");
		return "creategame";
	}
	/**
	 * 根据游戏名查询游戏
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/game/search", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String userSearch(HttpServletRequest request,
			Model model) throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/search】");
		String userName = request.getParameter("userName");
		logger.info("查询用户的用户名为【"+userName+"】");
		try{
		List<UserBean> list = gameConfigService.selectUserByUserName(userName);
		model.addAttribute("list", list);
		}catch(Exception e ) {
			logger.error("根据用户名查询【"+userName+"】用户时出现异常：\n"+e.getMessage(),e);
		}
		return "user";
	}
	/**
	 * 更新用户配置信息
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/game/update", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String updateUser(HttpServletRequest request, Model model) throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/update】");
		//获得总行数
		GameBean gameBean =JSONObject.parseObject((request.getParameter("gameBean")),GameBean.class);
		try{
		List<GameTypeBean> list = gameConfigService.selectGameType();
		model.addAttribute("list", list);
		model.addAttribute("gameBean", gameBean);
		//数据入库
		}catch(Exception e ) {
			logger.error("更新用户【"+gameBean.getGameName()+"】时出现异常:\n"+e.getMessage(),e);
		}
		return "updategame";
	}
	/**
	 * 保存更新用户信息入库
	 * @param userBean
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/game/saveupdate", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String saveUserUpdate(@ModelAttribute("userBean") GameBean gameBean,
			           Model model) throws InterruptedException {

		logger.info("UserConfigController  -->   【/user/saveupdate】");
		int i= gameConfigService.updateGameById(gameBean);
		model.addAttribute("gameBean", gameBean);
		
		if(i!=0) {
			model.addAttribute("updateMsg", "保存成功！");
		}else {
			model.addAttribute("updateMsg", "保存失败,请重新保存！");
		}
		return "updategame";
	}
	
	/**
	 * 保存创建游戏信息入库
	 * @param gameBean
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/game/savecreate", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String saveUserCreate(@ModelAttribute("gameBean") GameBean gameBean,Model model) throws InterruptedException {
		logger.info("GameController  -->   【/game/savegamecreate】");
		int i= gameConfigService.insertGame(gameBean);
		if(i!=0) {
			model.addAttribute("insertMsg", "新建成功！");
		}else {
			model.addAttribute("insertMsg", "新建失败,请重新创建！");
		}
		model.addAttribute("gameBean", gameBean);
		List<GameTypeBean> list = gameConfigService.selectGameType();
		model.addAttribute("list", list);
		return "redirect:/game/game";
	}
	
	/**
	 * 删除游戏
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/game/delete", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String deleteUser(HttpServletRequest request, Model model) throws InterruptedException {
		logger.info("UserConfigController  -->   【/game/delete】");
		int id =0;
		int i =0;
		if(request.getParameter("id")!=null){
		    id = Integer.parseInt(request.getParameter("id").toString());
		    i= gameConfigService.deleteGameById(id);
		}
		if(i!=0 ) {
			model.addAttribute("deleteMsg", "删除成功！");
		}else {
			model.addAttribute("deleteMsg", "删除失败,请重新删除！");
		}
		return "redirect:/game/game";
	}
	
	/**
	 * 更新游戏
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/game/state", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String userState(HttpServletRequest request, Model model) throws InterruptedException {
		logger.info("GameController  -->   【/game/state】");
		//此处需要继续完善
		int id =0;
		if(request.getParameter("id")!=null){
		    id = Integer.parseInt(request.getParameter("id").toString());
		}
		int state = Integer.parseInt(request.getParameter("state").toString());
		int page = Integer.parseInt(request.getParameter("page").toString());
		int pageSize = Integer.parseInt(request.getParameter("pageSize").toString());
		
		logger.info("page ="+page +"   pageSize ="+pageSize);
		String msg="";
		if(state == 1)
			msg="关闭成功！";
		else
			msg="开启成功！";
		int updateStateMsg = gameConfigService.updateGameState(id, state^1);
		if(updateStateMsg > 0)
			model.addAttribute("updateStateMsg", msg);
		return "redirect:/game/game?page="+page+"&pageSize="+pageSize;
	}
	
	/**
	 * 进入游戏信息总页面
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/game/game", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String gameCenter(@RequestParam(value="page" ,defaultValue="1") int page, @RequestParam(value="pageSize" ,defaultValue="10")int pageSize,HttpServletRequest request,Model model) {
		logger.info("GameController  -->   【/game/game】"+page+"  "+pageSize);
		
		List<GameBean> list = gameConfigService.selectGameByPaging(((page-1)*pageSize), pageSize);
		//获得总行数
		int rowCount = gameConfigService.getGameTotalRows();
		model.addAttribute("list", list);
		int pages=0;
		if(rowCount % pageSize == 0)
			pages = rowCount/pageSize;
		else
			pages = rowCount/pageSize+1;
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);

		return "game";
	}

}
