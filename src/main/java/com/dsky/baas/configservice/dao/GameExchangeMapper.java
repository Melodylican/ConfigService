package com.dsky.baas.configservice.dao;

import java.util.List;
import java.util.Map;

import com.dsky.baas.configservice.model.ExchangeBean;

/**
 * @ClassName: GameExchangeMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public interface GameExchangeMapper {
	List<ExchangeBean> selectExchByPaging(Map<String, Object> map);
	List<ExchangeBean> selectExchangeBygameName(Map<String, Object> map);
	ExchangeBean selectExchBean(Map<String, Object> map);//对外提供查询的api
	ExchangeBean selectExchangeById(int id);
	String selectGameNameByGameId(String gameId); //对外提供的查询api
	String getGameIdByGameName(String gameName);//对外提供查询的api 根据游戏名称查询游戏id
	int selectExchangeCount(Map<String, Object> map);
	int getExchangeTotalRows(Map<String,Object> map);
	int updateExchangeById(ExchangeBean exchBean);
	int insertExchange(ExchangeBean exchBean);
	int deleteExchangeById(int id);
	String createExch(ExchangeBean exchBean);//用于判定是否可以创建该兑换活动
	String updateExch(ExchangeBean exchBean);//用于判定是否可以更新该兑换活动

}