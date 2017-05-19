package com.dsky.baas.configservice.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dsky.baas.configservice.model.ExchangeBean;
import com.dsky.baas.configservice.model.GameBean;
import com.dsky.baas.configservice.model.GameTypeBean;
import com.dsky.baas.configservice.model.IntegralSchemeBean;
import com.dsky.baas.configservice.model.OrderBean;
import com.dsky.baas.configservice.model.PromoterBean;
import com.dsky.baas.configservice.model.RedeemCodeBean;
import com.dsky.baas.configservice.model.UserBean;
/**
 * @ClassName: IGameConfigService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
@Component
public interface IGameConfigService {
    public List<PromoterBean> selectByPaging(int startRow,int endRow,String gameName,String userRole);
    public int getPromoterTotalRows(String gameName);
    public List<PromoterBean> selectByOptions(String gameName,String location,String beginTime,String endTime,int startRow,int endRow,String createdBy,String userRole);
    public int getLastInsertId();
    public RedeemCodeBean selectRedeemCodeById(int id);
    public IntegralSchemeBean selectIntegralSchemeById(int id);
    
    public int updatePromoterById(PromoterBean promoterBean);
    public int updateRedeemCodeById(RedeemCodeBean redeemCodeBean );
    public int updateIntegralSchemeById(IntegralSchemeBean integralSchemeBean);
    
    public int insertPromoter(PromoterBean promoterBean);
    public int insertRedeemCode(RedeemCodeBean redeemCodeBean);
    public int insertIntegralScheme(IntegralSchemeBean integralSchemeBean);
    
    public int deletePromoterById(int id);
    public int deleteRedeemCodeById(int id);
    public int deleteIntegralSchemeById(int id);
    
    public PromoterBean selectPromoterByGameIdAndLocation(String gameId,String location);
    public PromoterBean selectPromoterBeanById(String id);
    public int selectBySearchingPage(String gameName, String location,String beginTime, String endTime,String createdBy,String userRole);
    public int updateState(int id,int state);
    public List<OrderBean> selectOrderByPaging(int startRow,int endRow);
    public int getOrderTotalRows();
    
    //用户管理相关方法
    public List<UserBean> selectUserByPaging(int startRow,int endRow);
    public int getUserTotalRows();
    public int insertUser(UserBean userBean);
    public int updateUserByUserName(UserBean userBean);
    public int deleteUserByUserName(String userName);
    public int updateUserState(String userName,int state);
    
    public List<GameTypeBean> selectGameType();
    public String selectUserGameType(String userName);
    public String selectUserRole(String userName);
    public List<UserBean> selectUserByUserName(String userName);
 
    //积分兑换相关方法
	public List<ExchangeBean> selectExchByPaging(int startRow,int endRow,String gameName, String userRole);
	public int getExchangeTotalRows(String gameName,String userRole);
	public int updateExchangeById(ExchangeBean exchBean);
	public int insertExchange(ExchangeBean exchBean);
	public int deleteExchangeById(int id);
	public List<ExchangeBean> selectExchangeBygameName(String gameName,String exchBeginTime,String exchEndTime,String createdBy,
			String role,int startRow,int endRow);
	public int selectExchangeCount(String gameName,String exchBeginTime,String exchEndTime,
			String createdBy,String userRole);
	public ExchangeBean selectExchBean(String gameName);//对外提供查询兑换配置的api
	public ExchangeBean selectExchangeById(int id);
	public String selectGameNameByGameId(String gameId);//对外提供查询兑换配置的api
	public String getGameIdByGameName(String gameName);
	
	//游戏管理相关方法
	public List<GameBean> selectGameByPaging(int startRow,int endRow);
    public int getGameTotalRows();
    public int updateGameState(int id,int state);
    public int updateGameById(GameBean gameBean);
    public int insertGame(GameBean gameBean);
    public int deleteGameById(int id);
    
    //判断是否可插入tb_promoter
    public String createPromoter(PromoterBean promoterBean);
    public String updatePromoter(PromoterBean promoterBean);
    public String createExch(ExchangeBean exchBean);//用于判定是否可以创建该兑换活动
    public String updateExch(ExchangeBean exchBean);//用于判定是否可以更新该兑换活动
}
