package com.dsky.baas.configservice.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dsky.baas.configservice.model.GameRedeemCodeBean;
@Component
public interface IRedeemCodeService {
	public List<GameRedeemCodeBean> selectRedeemCodeByPaging(int gameId,int actId,String searchScore,int startRow,int endRow,int bTime,int eTime,String status,String playerId);
    public int getRedeemCodeTotalRows(int gameId,int actId,int bTime,int eTime,String status,String searchScore,String playerId);
    public int updateRedeemCodeState(int id,int status);
    public int updateRedeemCodeById(GameRedeemCodeBean gameRedeemCodeBean);
    public int insertRedeemCode(GameRedeemCodeBean gameRedeemCodeList);
    public int deleteRedeemCodeById(int id);
    public int deleteRedeemCode(int gameId,int actId,int status,int searchScore);
    public int insertRedeemCodeBatch(List<GameRedeemCodeBean> list);
    public List<String> getScoreDistinct(int gameId,int actId);
    public int ifRedeemCodeExists(int gameId,int actId,String code);
    public int ifRedeemCodeExistsUpdate(int gameId,int actId,String code,int id);
    public List<GameRedeemCodeBean> exportRedeemCode(int gameId,int actId,String searchScore,String status,int bTime,int eTime);
    

}
