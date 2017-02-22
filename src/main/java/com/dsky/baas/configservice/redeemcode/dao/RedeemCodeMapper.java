package com.dsky.baas.configservice.redeemcode.dao;

import java.util.List;
import java.util.Map;

import com.dsky.baas.configservice.model.GameRedeemCodeBean;

/**
 * @ClassName: RedeemCodeMapper
 * @Description: TODO(游戏管理相关方法)
 * @author Chris.li
 */
public interface RedeemCodeMapper {
	List<GameRedeemCodeBean> selectRedeemCodeByPaging(Map<String,Object> map);
    int getRedeemCodeTotalRows(Map<String,Object> map);
    int updateRedeemCodeState(Map<String,Object> map);
    int updateRedeemCodeById(GameRedeemCodeBean gameRedeemCodeBean);
    int insertRedeemCode(GameRedeemCodeBean gameRedeemCodeList);
    int deleteRedeemCodeById(Map<String,Object> map);
    int insertRedeemCodeBatch(List<GameRedeemCodeBean> list);
    List<String> getScoreDistinct(Map<String,Object> map);
    int ifRedeemCodeExists(Map<String,Object> map);
    int ifRedeemCodeExistsUpdate(Map<String,Object> map);
}