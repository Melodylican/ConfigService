package com.dsky.baas.configservice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dsky.baas.configservice.model.ActInfoBean;
import com.dsky.baas.configservice.model.PayInfoBean;
import com.dsky.baas.configservice.model.Statistics;
import com.dsky.baas.configservice.service.IActInfoService;
import com.dsky.baas.configservice.service.IGameConfigService;
import com.dsky.baas.configservice.service.IPayInfoService;
import com.dsky.baas.configservice.util.StatisticsUtils;
/**
 * @ClassName: StatisticsController
 * @Description: (用于统计数据的展示)
 * @author Chris.li
 */
@Controller
public class StatisticsController {
	private static final Logger logger = Logger.getLogger(StatisticsController.class);
	@Resource
	private IGameConfigService gameConfigService;
	@Autowired
	private IActInfoService actInfoService;
	@Autowired
	private IPayInfoService payInfoService;
	@Autowired
	public void setGameConfigService(IGameConfigService gameConfigService) {
		this.gameConfigService = gameConfigService;
	}

	/**
	 * 进入数据展示页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/statistics/show", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String createUser(HttpServletRequest request,Model model) {
		logger.info("StatisticsController  -->   【/statistics/show】");
		String gameId = request.getParameter("gameId");
		String actId = request.getParameter("actId");
		String gameName = request.getParameter("gameName");
		logger.info("接收到传递参数为：gameId = "+gameId+"  actId = "+actId+"  gameName = "+gameName);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
		
		String beginTime1 = request.getParameter("beginTime1");
		String endTime1 = request.getParameter("endTime1");
		logger.info("beginTime1="+beginTime1+"  endTime1="+endTime1);
		String beginTime2 = request.getParameter("beginTime2");
		String endTime2 = request.getParameter("endTime2");
		logger.info("beginTime2="+beginTime2+"  endTime2="+endTime2);
		String beginTime3 = request.getParameter("beginTime3");
		String endTime3 = request.getParameter("endTime3");
		logger.info("beginTime3="+beginTime3+"  endTime3="+endTime3);
		
		String nowTime = sf.format(new Date());
		if(endTime1==null || "".equals(endTime1))
			endTime1 = nowTime;
		if(endTime2==null || "".equals(endTime1))
			endTime2 = nowTime;		
		if(endTime3==null || "".equals(endTime1))
			endTime3 = nowTime;
		
		if(beginTime1==null || "".equals(beginTime1))
			beginTime1 ="0";
		if(beginTime2==null || "".equals(beginTime1))
			beginTime2 ="0";
		if(beginTime3==null || "".equals(beginTime1))
			beginTime3 ="0";
		List<PayInfoBean> listPay = payInfoService.selectPayInfo(gameId, actId,beginTime1,endTime1);
		logger.info("payinfolist "+listPay.size());
		List<ActInfoBean> listAct2 = actInfoService.selectActInfo(gameId, actId,beginTime2,endTime2);
		List<ActInfoBean> listAct3 = actInfoService.selectActInfo(gameId, actId,beginTime3,endTime3);
		//邀请人数及付费情况图表
		Statistics s1 = StatisticsUtils.getSeriesJson(listPay);
		if(s1 != null) {
			model.addAttribute("series1",s1.getSeries().replace("\"", "\'"));
			model.addAttribute("categories1",s1.getCategories());
			logger.info("categories1 : "+s1.getCategories());
			logger.info("series1 : "+ s1.getSeries().replace("\"", "\'"));
		}else {
			model.addAttribute("series1","[{'data':[0],'name':'无数据'}]");
			model.addAttribute("categories1","['0']");			
			model.addAttribute("pic1msg", "没有当前游戏对应于"+gameName+"("+gameId+")&nbsp;&nbsp;<font color='red' >[邀请人数及付费情况的统计]</font>&nbsp;&nbsp;的数据");
		}
		
		//分享方式统计表
		Statistics s2 = StatisticsUtils.getSeriesJson(listAct2,"pic2");
		if(s2 != null) {
			model.addAttribute("series2",s2.getSeries().replace("\"", "\'"));
			model.addAttribute("categories2",s2.getCategories());
			logger.info("categories2 : "+s2.getCategories());
			logger.info("series2 : "+ s2.getSeries().replace("\"", "\'"));
		}else {
			logger.info("s2中数据为空，将为其生成默认数据");
			model.addAttribute("series2","[{'data':[0],'name':'无数据'}]");
			model.addAttribute("categories2","['0']");	
			model.addAttribute("pic2msg", "没有当前游戏对应于"+gameName+"("+gameId+")&nbsp;&nbsp;<font color='red' >[分享方式统计]</font>&nbsp;&nbsp;的数据");
		}
		//点击数统计表
		Statistics s3 = StatisticsUtils.getSeriesJson(listAct3,"pic3");
		if(s3 != null) {
			model.addAttribute("series3",s3.getSeries().replace("\"", "\'"));
			model.addAttribute("categories3",s3.getCategories());
			logger.info("categories3 : "+s3.getCategories());
			logger.info("series3 : "+ s3.getSeries().replace("\"", "\'"));
		}else {
			logger.info("s3为空中数据为空，将为其生成默认数据");
			model.addAttribute("series3","[{'data':[0],'name':'无数据'}]");
			model.addAttribute("categories3","['0']");
			model.addAttribute("pic3msg", "没有当前游戏对应于 "+gameName+"("+gameId+") &nbsp;&nbsp;<font color='red' >[点击数统计]</font>&nbsp;&nbsp;的数据");
		}
		model.addAttribute("gameId", gameId);
		model.addAttribute("actId",actId);
		model.addAttribute("gameName", gameName);
		if("0".equals(beginTime1))
			model.addAttribute("beginTime1",null);
		else
			model.addAttribute("beginTime1", beginTime1);
		model.addAttribute("endTime1", endTime1);
		if("0".equals(beginTime2))
			model.addAttribute("beginTime2",null);
		else
			model.addAttribute("beginTime2", beginTime2);
		model.addAttribute("endTime2", endTime2);
		if("0".equals(beginTime3))
			model.addAttribute("beginTime3",null);
		else
			model.addAttribute("beginTime3", beginTime3);
		model.addAttribute("endTime3", endTime3);		
		return "statistics";
	}
}
