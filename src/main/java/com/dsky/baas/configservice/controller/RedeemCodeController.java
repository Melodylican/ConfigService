package com.dsky.baas.configservice.controller;

import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.dsky.baas.configservice.logservice.IWarningReporterService;
import com.dsky.baas.configservice.model.GameRedeemCodeBean;
import com.dsky.baas.configservice.service.IGameConfigService;
import com.dsky.baas.configservice.service.IRedeemCodeService;
import com.dsky.baas.configservice.service.excel.ImportExcelUtil;
import com.dsky.baas.configservice.util.DateUtil;
/**
 * @ClassName: GameController
 * @Description: TODO(用于游戏管理)
 * @author Chris.li
 */
@Controller
public class RedeemCodeController {
	private static final Logger logger = Logger.getLogger(RedeemCodeController.class);
	@Resource
	private IRedeemCodeService redeemCodeService;
	@Resource
	private IWarningReporterService warningReporterService;
	
	@Autowired
	public void setWarningReporterService(
			IWarningReporterService warningReporterService) {
		this.warningReporterService = warningReporterService;
	}
	@Autowired
	public void setRedeemCodeService(IRedeemCodeService redeemCodeService) {
		this.redeemCodeService = redeemCodeService;
	}
	@Resource
	private IGameConfigService gameConfigService;

	@Autowired
	public void setGameConfigService(IGameConfigService gameConfigService) {
		this.gameConfigService = gameConfigService;
	}
	/**
	 * 进入兑换码展示页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/redeem/list", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String redeemList(HttpServletRequest request,Model model,@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		logger.info("GameController  -->   【/redeem/list】");
		int actId = Integer.parseInt(request.getParameter("actId"));
		String gameName = request.getParameter("gameName");
		//根据gameName查询gameId
		int gameId = Integer.parseInt(gameConfigService
				.getGameIdByGameName(gameName));
		logger.info("==/redeem/list====gameId=" + gameId);
		
		String status = request.getParameter("status");
		if(status == null || "".equals(status))
			status = "1";
		
		logger.info("接收的参数为： actId= "+actId+"  gameId= "+gameId+"  gameName= "+gameName);
		//获取时间
		//获取查询时间
		String beginTime = request.getParameter("beginTime");
		logger.info("获取的 beginTime "+beginTime);
		String endTime = request.getParameter("endTime");
		if(endTime == null) {
			//当未配置结束时间时，取系统当前时间作为结束时间
			SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			endTime = sf.format(System.currentTimeMillis()+60000*5);//多加5分钟
			logger.info("结束时间是："+endTime);
		}
		String searchScore = request.getParameter("searchScore");//获取查询的积分类别
		String playerId = request.getParameter("playerId");
		if(playerId == null ) {
			logger.info("查询的playerId 为 null");
		}
		if("".equals(playerId)) {
			logger.info("查询的playerId 为空 ''");
		}

		//查询兑换码积分类别
		List<String> scoreList = redeemCodeService.getScoreDistinct(gameId,actId);
		
		if(searchScore == null) {
			if(scoreList.size() >=1 )
			     searchScore = scoreList.get(0);
			else 
				searchScore ="";
		}
		logger.info("searchScore="+searchScore);
		
		int eTime = (int)(DateUtil.parseDateFromString(endTime, "yyyy/MM/dd HH:mm", 0)/1000);
		logger.info("结束时间是："+eTime);
		int bTime = 0;
		if(beginTime != null && beginTime.length() >0) {
			bTime = (int)(DateUtil.parseDateFromString(beginTime, "yyyy/MM/dd HH:mm", 0)/1000);
			logger.info("开始时间是："+bTime);
		}
		
		try {
			List<GameRedeemCodeBean> list = redeemCodeService.selectRedeemCodeByPaging(gameId, actId,searchScore,((page - 1) * pageSize), pageSize,bTime,eTime,status,playerId);
			// 获得总行数
			int rowCount = redeemCodeService.getRedeemCodeTotalRows(gameId,actId,bTime,eTime,status,searchScore,playerId);
			logger.info("查询到了推广码的行数为："+rowCount);
			model.addAttribute("rowCount", rowCount);//存储总行数
			model.addAttribute("list", list);//存储查询的兑换码信息
			int pages = 0;
			if (rowCount % pageSize == 0)
				pages = rowCount / pageSize;
			else
				pages = rowCount / pageSize + 1;
			
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			String userName = userDetails.getUsername();
			// 获取拥有权限的游戏名列表
			String gameNames = gameConfigService.selectUserGameType(userName);
			String[] gameNameArr = null;
			if (gameNames != null) {
				gameNameArr = gameNames.split(",");
			} else
				gameNameArr = new String[] { "" };
			List<String> gameNameList = Arrays.asList(gameNameArr);

			model.addAttribute("scoreList", scoreList);//存储分数类别
			model.addAttribute("searchScore",searchScore);//存储查询的积分类别
			model.addAttribute("gameNameList", gameNameList);//存储可操作游戏类别
			model.addAttribute("beginTime", beginTime);
			model.addAttribute("endTime", endTime);
			model.addAttribute("gameId", gameId);
			model.addAttribute("actId", actId);
			model.addAttribute("gameName", gameName);
			model.addAttribute("pages", pages);
			model.addAttribute("page", page);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("status", status);
			model.addAttribute("playerId", playerId);
		} catch (Exception e) {
			logger.info("/redeem/list中出现了异常\n" + e.getMessage());
		}
		return "redeemlist";
	}
	
	@RequestMapping(value = "/redeem/update", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String updateRedeem(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/redeem/update】");
		// 获得总行数
		GameRedeemCodeBean gameRedeemBean = JSONObject.parseObject(
				(request.getParameter("gameRedeemBean")), GameRedeemCodeBean.class);
		String gameName = request.getParameter("gameName");
		logger.info("传递的游戏名是："+gameName);
		try {
			model.addAttribute("gameRedeemBean", gameRedeemBean);
			model.addAttribute("gameName", gameName);

		} catch (Exception e) {
			logger.error("/redeem/update 更新用户【" + gameRedeemBean.getGameId() + "】时出现异常:\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/redeem/update 更新用户【" + gameRedeemBean.getGameId() + "】时出现异常:\n"
					+ e.getMessage());			
		}
		return "updateredeem";
	}
	
	@RequestMapping(value = "/redeem/create", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String createRedeem(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/redeem/create】");
		// 获得总行数
        String gameId = request.getParameter("gameId");
		String gameName = request.getParameter("gameName");
		String actId = request.getParameter("actId");
		logger.info("传递的游戏名是："+gameName+" gameId: "+gameId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		model.addAttribute("actId", actId);

		return "createredeem";
	}
	
	@RequestMapping(value = "/redeem/saveupdate", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveRedeemUpdate(@ModelAttribute("gameRedeemBean") GameRedeemCodeBean gameRedeemBean,
			HttpServletRequest request,Model model) throws InterruptedException {

		logger.info("UserConfigController  -->   【/redeem/saveupdate】");
		String gameName = request.getParameter("gameName");
		gameRedeemBean.setUpdateTime((int)(System.currentTimeMillis()/1000));//设置更新时间
		model.addAttribute("gameRedeemBean", gameRedeemBean);
		model.addAttribute("gameName", gameName);
		int exists = redeemCodeService.ifRedeemCodeExistsUpdate(gameRedeemBean.getGameId(), gameRedeemBean.getActId(), gameRedeemBean.getCode(),gameRedeemBean.getId());
		logger.info("更新的兑换码对应于数据库id为："+gameRedeemBean.getId());
		if(exists > 0) {
			model.addAttribute("updateMsg", "该邀请码在本活动中已经存在，请重新更新！");
			return "updateredeem";
		}
			
		
		int i = redeemCodeService.updateRedeemCodeById(gameRedeemBean);

		logger.info("更新的对象是："+gameRedeemBean.toString()+" 游戏名："+gameName);

		if (i != 0) {
			model.addAttribute("updateMsg", "保存成功！");
		} else {
			model.addAttribute("updateMsg", "保存失败,请重新保存！");
		}
		return "updateredeem";
	}
	
	/**
	 * 
	 * 方法功能说明：保存创建的兑换码入库
	 * 创建：2016年12月5日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param gameRedeemBean
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @参数： @throws InterruptedException    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/redeem/savecreate", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveRedeemCreate(@ModelAttribute("gameRedeemBean") GameRedeemCodeBean gameRedeemBean,
			HttpServletRequest request,Model model) throws InterruptedException {

		logger.info("UserConfigController  -->   【/redeem/savecreate】");
		String gameName = request.getParameter("gameName");
		int nowTime = (int)(System.currentTimeMillis()/1000);
		gameRedeemBean.setCreateTime(nowTime);//设置创建时间时间
		gameRedeemBean.setUpdateTime(nowTime);//设置更新时间与创建时间相同

		model.addAttribute("gameRedeemBean", gameRedeemBean);
		model.addAttribute("gameName", gameName);
		logger.info("更新的对象是："+gameRedeemBean.toString()+" 游戏名："+gameName);
		int exists = redeemCodeService.ifRedeemCodeExists(gameRedeemBean.getGameId(), gameRedeemBean.getActId(), gameRedeemBean.getCode());
		if(exists > 0) {
			model.addAttribute("updateMsg", "该邀请码在本活动中已经存在，请重新创建！");
			model.addAttribute("gameId", gameRedeemBean.getGameId());
			model.addAttribute("gameName", gameName);
			model.addAttribute("actId", gameRedeemBean.getActId());
			return "createredeem";
		}
		int i = redeemCodeService.insertRedeemCode(gameRedeemBean);
		if (i != 0) {
			model.addAttribute("updateMsg", "保存成功！");
		} else {
			model.addAttribute("updateMsg", "保存失败,请重新保存！");
		}
		return "updateredeem";
	}
	
	/**
	 * 
	 * 方法功能说明：删除兑换码 记录其删除时间
	 * 创建：2016年12月5日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @参数： @throws InterruptedException    
	 * @return String   
	 * @throws
	 */
	@RequestMapping(value = "/redeem/deleteall", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String deleteRedeemAll(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/redeem/delete】");
		//设置兑换码的删除时间 并且将状态改成2
		// 获得总行数
        int gameId = Integer.parseInt(request.getParameter("gameId"));
        int actId = Integer.parseInt(request.getParameter("actId"));
		String gameName = request.getParameter("gameName");
		int status = Integer.parseInt(request.getParameter("status"));
		int searchScore = Integer.parseInt(request.getParameter("searchScore"));

		int i = redeemCodeService.deleteRedeemCode(gameId,actId,status,searchScore);
		logger.info("删除的结果为："+i);
		logger.info("传递的游戏名是："+gameName+" gameId: "+gameId);
		return "forward:/redeem/list?gameId="+gameId+"&gameName="+gameName+"&actId="+actId+"&status="+status;
	}
	
	@RequestMapping(value = "/redeem/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String deleteRedeem(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/redeem/create】");
		//设置兑换码的删除时间 并且将状态改成2
		// 获得总行数
        String gameId = request.getParameter("gameId");
        String actId = request.getParameter("actId");
		String gameName = request.getParameter("gameName");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String status = request.getParameter("status");
		String searchScore = request.getParameter("searchScore");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int id = Integer.parseInt(request.getParameter("id"));
		
		int i = redeemCodeService.deleteRedeemCodeById(id);
		logger.info("删除的结果为："+i);
		logger.info("传递的游戏名是："+gameName+" gameId: "+gameId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		model.addAttribute("actId", id);

		return "forward:/redeem/list?gameId="+gameId+"&gameName="+gameName+"&actId="+actId+"&page="+page+"&pageSize="+pageSize+"&beginTime="+beginTime+"&endTime="+endTime+"&status="+status+"&searchScore="+searchScore;
	}
	
	/**
	 * 此方法暂时弃用
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/redeem/status", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String redeemStatus(HttpServletRequest request, Model model) throws InterruptedException {
		logger.info("GameController  -->   【/redeem/status】");
		//此处需要继续完善
		int id =0;
		if(request.getParameter("id")!=null){
			try {
		    id = Integer.parseInt(request.getParameter("id").toString());
		    }catch(NumberFormatException e) {
		    	logger.error("/redeem/status id转码时出现异常 ["+request.getParameter("id").toString()+"]"+e.getMessage());
				warningReporterService.reportWarnString("/redeem/status id转码时出现异常 ["+request.getParameter("id").toString()+"]"+e.getMessage());
		    }
		}
		int status = Integer.parseInt(request.getParameter("status").toString());
		int page = Integer.parseInt(request.getParameter("page").toString());
		int pageSize = Integer.parseInt(request.getParameter("pageSize").toString());
        String gameId = request.getParameter("gameId");
        String actId = request.getParameter("actId");
		String gameName = request.getParameter("gameName");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String searchScore = request.getParameter("searchScore");
		
		logger.info("page ="+page +"   pageSize ="+pageSize);
		String msg="";
		if(status == 1)
			msg="关闭成功！";
		else
			msg="开启成功！";
		int updateStateMsg = redeemCodeService.updateRedeemCodeState(id, status^1);
		if(updateStateMsg > 0)
			model.addAttribute("updateStateMsg", msg);
		return "forward:/redeem/list?gameId="+gameId+"&gameName="+gameName+"&actId="+actId+"&page="+page+"&pageSize="+pageSize+"&beginTime="+beginTime+"&endTime="+endTime+"&searchScore="+searchScore;
	}
	
	@RequestMapping(value = "/redeem/importpage", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String importpage(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/redeem/create】");
		// 获得总行数
        String gameId = request.getParameter("gameId");
		String gameName = request.getParameter("gameName");
		String actId = request.getParameter("actId");
		logger.info("传递的游戏名是："+gameName+" gameId: "+gameId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("gameName", gameName);
		model.addAttribute("actId", actId);

		return "redeemimport";
	}
	
    @ResponseBody  
    @RequestMapping(value="/redeem/import",method={RequestMethod.GET,RequestMethod.POST})  
    public  void  redeemAjaxImportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        logger.info("通过 jquery.form.js 提供的ajax方式上传文件！");
        int actId=0;
        int gameId =0;
        try{
            actId = Integer.parseInt(request.getParameter("actId"));
            gameId = Integer.parseInt(request.getParameter("gameId"));
        }catch(NumberFormatException e) {
        	logger.error("/redeem/import 参数转整形出现异常！"+e.getMessage());
			warningReporterService.reportWarnString("/redeem/status 参数转整形出现异常！"+e.getMessage());
		            	
        }

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
 
        int timenow = (int)(System.currentTimeMillis()/1000);
        List<GameRedeemCodeBean> list = new ArrayList<GameRedeemCodeBean>();
        //该处可调用service相应方法进行数据保存到数据库中，  
        for (int i = 0; i < listob.size(); i++) {  
        	logger.info("将数据组合成数组形式！");
            List<Object> lo = listob.get(i);  
            GameRedeemCodeBean ob = new GameRedeemCodeBean();  
            ob.setCode(String.valueOf(lo.get(0)));
            ob.setScore(Integer.parseInt(String.valueOf(lo.get(1))));
            ob.setCreateTime(timenow);
            ob.setUpdateTime(timenow);
            ob.setActId(actId);
            ob.setGameId(gameId);
            list.add(ob);
            logger.info("读取的信息--->:"+ob.toString());  
        }
        PrintWriter out = null;  
        response.setCharacterEncoding("utf-8");  //防止ajax接受到的中文信息乱码  
        out = response.getWriter();
        try {
        	//已支付订单提交
        	logger.info("开始数据入库");
			// 数据入库
        	int i = redeemCodeService.insertRedeemCodeBatch(list);
        	if(i <= 0)
        		logger.info("数据入库不成功！");
			logger.info("调用方法结束");
		} catch(Exception e) {
			logger.error("出现异常\n"
					+ e.getMessage(),e);
			warningReporterService.reportWarnString("/redeem/import 出现异常\n"+ e.getMessage());
	        out.print("文件导入失败，请重新提交！");
	        out.flush();  
	        out.close();
		}
        logger.info("文件导入成功");
        out.print("文件导入成功！");
        out.flush();  
        out.close();
    }
}
