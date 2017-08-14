package com.dsky.baas.configservice.service;

import java.util.List;

import com.dsky.baas.pointsservice.model.ExchangeOrder;


public interface IExchangeOrderService {
	public ExchangeOrder createOrder(int playerId,int gameId,int planId,int requestPoints,int payMethod,String payInfo,String userMemo);
	public List<ExchangeOrder> getExchangeLogs(Long playerId,int gameId,int pageNum,int resultSize);
	public List<ExchangeOrder> getExchangeLogs(Long playerId,int gameId,int pageNum);
	public List<ExchangeOrder> getExchangeOrderByGameId(int gameId,int startRow,int endRow);
	public List<ExchangeOrder> getExchangeOrderBystatus(int gameId,String status,int startRow,int endRow);
	public int getExchangeOrderTotalNum(int gameId);
	public int updateExchangeOrderStatus(String orderId,String status);
	public int updateExchangeOrderStatus(String orderId,String status,String operation_memo);
	public int updateExchangeOrderStatusByQuantity(String []orderId,String status);//批量通过
	public int getExchangeOrderTotalNum(int gameId,String status);//获取已通过订单的总行数
	//根据订单号查询订单信息
	public ExchangeOrder getExchangeOrderByOrderId(String orderId);
}
