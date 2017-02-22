package com.dsky.baas.configservice.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.baas.configservice.dao.GameConfigMapper;
import com.dsky.baas.configservice.dao.GameExchangeMapper;
import com.dsky.baas.configservice.dao.GameMapper;
import com.dsky.baas.configservice.model.ExchangeBean;
import com.dsky.baas.configservice.model.GameBean;
import com.dsky.baas.configservice.model.GameTypeBean;
import com.dsky.baas.configservice.model.IntegralSchemeBean;
import com.dsky.baas.configservice.model.OrderBean;
import com.dsky.baas.configservice.model.PromoterBean;
import com.dsky.baas.configservice.model.RedeemCodeBean;
import com.dsky.baas.configservice.model.UserBean;
import com.dsky.baas.configservice.service.IGameConfigService;
/**
 * @ClassName: GameConfigServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
@Service("gameConfigService")
public  class GameConfigServiceImpl implements IGameConfigService {
	private static final Logger logger = Logger.getLogger(GameConfigServiceImpl.class);
	private GameConfigMapper gameConfigMapper;
	private GameExchangeMapper gameExchangeMapper;
	private GameMapper gameMapper;

	public GameConfigMapper getGameConfigMapper() {
		return gameConfigMapper;
	}
	@Autowired
	public void setGameConfigMapper(GameConfigMapper gameConfigMapper) {
		this.gameConfigMapper = gameConfigMapper;
	}
	
	public GameExchangeMapper getGameExchangeMapper() {
		return gameExchangeMapper;
	}
	@Autowired
	public void setGameExchangeMapper(GameExchangeMapper gameExchangeMapper) {
		this.gameExchangeMapper = gameExchangeMapper;
	}
	
	public GameMapper getGameMapper() {
		return gameMapper;
	}
	@Autowired
	public void setGameMapper(GameMapper gameMapper) {
		this.gameMapper = gameMapper;
	}
	@Override
	public List<PromoterBean> selectByPaging(int startRow, int endRow,String gameName,String userRole) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("gameName", gameName);
		map.put("userRole",userRole);
		return gameConfigMapper.selectByPaging(map);
	}
	
	@Override
	public int getPromoterTotalRows(String gameName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gameName", gameName);
		return gameConfigMapper.getPromoterTotalRows(map);
	}
	
	@Override
	public List<PromoterBean> selectByOptions(String gameName, String location,
			String beginTime, String endTime, int startRow, int endRow,String createdBy,String userRole) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gameName", gameName);
		map.put("location", location);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("createdBy", createdBy);
		map.put("userRole", userRole);
		return gameConfigMapper.selectByOptions(map);
	}
	
	@Override
	public int selectBySearchingPage(String gameName, String location,
			String beginTime, String endTime,String createdBy,String userRole) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("gameName", gameName);
		map.put("location", location);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("createdBy", createdBy);
		map.put("userRole", userRole);
		return gameConfigMapper.selectBySearchingPage(map);
	}
	
	@Override
	public int getLastInsertId() {
		return gameConfigMapper.getLastInsertId();
	}
	
	@Override
	public RedeemCodeBean selectRedeemCodeById(int id) {
		return gameConfigMapper.selectRedeemCodeById(id);
	}
	
	@Override
	public IntegralSchemeBean selectIntegralSchemeById(int id) {
		return gameConfigMapper.selectIntegralSchemeById(id);
	}

	@Override
	public int updatePromoterById(PromoterBean promoterBean) {
		return gameConfigMapper.updatePromoterById(promoterBean);
	}

	@Override
	public int updateRedeemCodeById(RedeemCodeBean redeemCodeBean) {
		return gameConfigMapper.updateRedeemCodeById(redeemCodeBean);
	}

	@Override
	public int updateIntegralSchemeById(IntegralSchemeBean integralSchemeBean) {
		return gameConfigMapper.updateIntegralSchemeById(integralSchemeBean);
	}

	@Override
	public int insertPromoter(PromoterBean promoterBean) {
		return gameConfigMapper.insertPromoter(promoterBean);
	}

	@Override
	public int insertRedeemCode(RedeemCodeBean redeemCodeBean) {
		return gameConfigMapper.insertRedeemCode(redeemCodeBean);
	}

	@Override
	public int insertIntegralScheme(IntegralSchemeBean integralSchemeBean) {
		return gameConfigMapper.insertIntegralScheme(integralSchemeBean);
	}

	@Override
	public int deletePromoterById(int id) {
		return gameConfigMapper.deletePromoterById(id);
	}

	@Override
	public int deleteRedeemCodeById(int id) {
		return gameConfigMapper.deleteRedeemCodeById(id);
	}

	@Override
	public int deleteIntegralSchemeById(int id) {
		return gameConfigMapper.deleteIntegralSchemeById(id);
	}

	//对外提供的查询方法
	@Override
	public PromoterBean selectPromoterByGameIdAndLocation(String gameId,
			String location) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("gameId", gameId);
		map.put("location", location);

		return gameConfigMapper.selectPromoterByGameIdAndLocation(map);
	}

	@Override
	public PromoterBean selectPromoterBeanById(String id) {
		Map<String,String> map = new HashMap<String,String>();
        map.put("id", id);

		return gameConfigMapper.selectPromoterBeanById(map);
	}

	@Override
	public int updateState(int id, int state) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("id", id);
		map.put("state", state);
		return gameConfigMapper.updateState(map);
	}

	@Override
	public List<OrderBean> selectOrderByPaging(int startRow,int endRow) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		return gameConfigMapper.selectOrderByPaging(map);
	}

	@Override
	public int getOrderTotalRows() {
		return gameConfigMapper.getOrderTotalRows();
	}

	@Override
	public List<UserBean> selectUserByPaging(int startRow, int endRow) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		return gameConfigMapper.selectUserByPaging(map);
	}

	@Override
	public int getUserTotalRows() {
		return gameConfigMapper.getUserTotalRows();
	}

	@Override
	public int insertUser(UserBean userBean) {
		return gameConfigMapper.insertUser(userBean);
	}

	@Override
	public int updateUserByUserName(UserBean userBean) {
		return gameConfigMapper.updateUserByUserName(userBean);
	}

	@Override
	public int deleteUserByUserName(String userName) {
		return gameConfigMapper.deleteUserByUserName(userName);
	}

	@Override
	public int updateUserState(String userName, int state) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", userName);
		map.put("state", state);
		return gameConfigMapper.updateUserState(map);
	}

	@Override
	public List<GameTypeBean> selectGameType() {
		return gameConfigMapper.selectGameType();
	}

	@Override
	public String selectUserGameType(String userName) {
		return  gameConfigMapper.selectUserGameType(userName);
	}

	@Override
	public String selectUserRole(String userName) {
		return gameConfigMapper.selectUserRole(userName);
	}
    
	//积分配置管理相关方法
	@Override
	public List<UserBean> selectUserByUserName(String userName) {
		return gameConfigMapper.selectUserByUserName(userName);
	}
	//判断是否可创建promoter
	@Override
	public String createPromoter(PromoterBean promoterBean) {
		return gameConfigMapper.createPromoter(promoterBean);
	}
	//判断是否可更新promoter
	@Override
	public String updatePromoter(PromoterBean promoterBean) {
		return gameConfigMapper.updatePromoter(promoterBean);
	}
	@Override
	public List<ExchangeBean> selectExchByPaging(int startRow,int endRow,String gameName,String userRole) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("gameName", gameName);
		map.put("userRole", userRole);
		return gameExchangeMapper.selectExchByPaging(map);
	}

	@Override
	public int getExchangeTotalRows(String gameName,String userRole) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gameName", gameName);
		map.put("userRole", userRole);
		return gameExchangeMapper.getExchangeTotalRows(map);
	}

	@Override
	public int updateExchangeById(ExchangeBean exchBean) {
		return gameExchangeMapper.updateExchangeById(exchBean);
	}

	@Override
	public int insertExchange(ExchangeBean exchBean) {
		return gameExchangeMapper.insertExchange(exchBean);
	}

	@Override
	public int deleteExchangeById(int id) {
		return gameExchangeMapper.deleteExchangeById(id);
	}
	@Override
	public List<ExchangeBean> selectExchangeBygameName(String gameName,
			String exchBeginTime, String exchEndTime, String createdBy,
			String userRole,int startRow ,int endRow) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createdBy", createdBy);
		map.put("userRole", userRole);
		map.put("gameName", gameName);
		map.put("exchBeginTime", exchBeginTime);
		map.put("exchEndTime", exchEndTime);
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		return gameExchangeMapper.selectExchangeBygameName(map);
	}
	@Override
	public int selectExchangeCount(String gameName,
			String exchBeginTime, String exchEndTime, String createdBy,
			String userRole) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createdBy", createdBy);
		map.put("userRole", userRole);
		map.put("gameName", gameName);
		map.put("exchBeginTime", exchBeginTime);
		map.put("exchEndTime", exchEndTime);
		return gameExchangeMapper.selectExchangeCount(map);
	}
	//对外提供查询兑换配置的方法
	@Override
	public ExchangeBean selectExchBean(String gameName) {
		Map<String,Object> map = new HashMap<String,Object>();
		long time = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String currentDate = format.format(time);
		map.put("currentDate", currentDate);
		map.put("gameName", gameName);
		return gameExchangeMapper.selectExchBean(map);
	}
	//对外提供查询兑换配置的方法
	@Override
	public String selectGameNameByGameId(String gameId) {
		return gameExchangeMapper.selectGameNameByGameId(gameId);
	}
	@Override
	public String getGameIdByGameName(String gameName) {
		return gameExchangeMapper.getGameIdByGameName(gameName);
	}
	//用于判断创建的兑换活动是否和已有活动存在时间冲突
	@Override
	public String createExch(ExchangeBean exchBean) {
		return gameExchangeMapper.createExch(exchBean);
	}
	//用于判断更新的兑换活动是否和已有活动存在时间冲突
	@Override
	public String updateExch(ExchangeBean exchBean) {
		return gameExchangeMapper.updateExch(exchBean);
	}
	@Override
	public List<GameBean> selectGameByPaging(int startRow,int endRow) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		return gameMapper.selectGameByPaging(map);
	}
	@Override
	public int getGameTotalRows() {
		return gameMapper.getGameTotalRows();
	}
	@Override
	public int updateGameState(int id,int state) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("state", state);
		return gameMapper.updateGameState(map);
	}
	@Override
	public int updateGameById(GameBean gameBean) {
		return gameMapper.updateGameById(gameBean);
	}
	@Override
	public int insertGame(GameBean gameBean) {
		long time = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String currentDate = format.format(time);
		gameBean.setCreatedTime(currentDate);
		return gameMapper.insertGame(gameBean);
	}
	@Override
	public int deleteGameById(int id) {
		return gameMapper.deleteGameById(id);
	}




}
