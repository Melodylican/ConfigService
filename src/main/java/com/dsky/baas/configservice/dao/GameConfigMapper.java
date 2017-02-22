package com.dsky.baas.configservice.dao;

import java.util.List;
import java.util.Map;

import com.dsky.baas.configservice.model.GameTypeBean;
import com.dsky.baas.configservice.model.IntegralSchemeBean;
import com.dsky.baas.configservice.model.OrderBean;
import com.dsky.baas.configservice.model.PromoterBean;
import com.dsky.baas.configservice.model.RedeemCodeBean;
import com.dsky.baas.configservice.model.UserBean;
/**
 * @ClassName: DateUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public interface GameConfigMapper {
    
    List<PromoterBean> selectByPaging(Map<String, Object> map);
    List<OrderBean> selectOrderByPaging(Map<String, Object> map);
    List<UserBean> selectUserByPaging(Map<String, Integer> map);
    List<GameTypeBean> selectGameType();
    
    int getPromoterTotalRows(Map<String, Object> map);
    List<PromoterBean> selectByOptions(Map<String, Object> map);
    int getLastInsertId();
    RedeemCodeBean selectRedeemCodeById(int id);
    IntegralSchemeBean selectIntegralSchemeById(int id);
    int updatePromoterById(PromoterBean promoterBean);
    int updateRedeemCodeById(RedeemCodeBean redeemCodeBean );
    int updateIntegralSchemeById(IntegralSchemeBean integralSchemeBean);
    
    int insertPromoter(PromoterBean promoterBean);
    int insertRedeemCode(RedeemCodeBean redeemCodeBean);
    int insertIntegralScheme(IntegralSchemeBean integralSchemeBean);
    
    int deletePromoterById(int id);
    int deleteRedeemCodeById(int id);
    int deleteIntegralSchemeById(int id);
    
    PromoterBean selectPromoterByGameIdAndLocation(Map<String, String> map);
    PromoterBean selectPromoterBeanById(Map<String, String> map);
    int selectBySearchingPage(Map<String, String> map);
    int updateState(Map<String, Integer> map);
    int getOrderTotalRows();
    
    int getUserTotalRows();
    int insertUser(UserBean userBean);
    int updateUserByUserName(UserBean userBean);
    int deleteUserByUserName(String userName);
    int updateUserState(Map<String, Object> map);
    
    String selectUserGameType(String userName);
    String selectUserRole(String userName);
    List<UserBean> selectUserByUserName(String userName);
    
    String createPromoter(PromoterBean promoterBean);
    String updatePromoter(PromoterBean promoterBean);
}