package com.dsky.baas.configservice.service.impl;

import java.rmi.ConnectException;
import java.util.List;

import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteLookupFailureException;

import com.dsky.baas.configservice.service.IExchangeOrderService;
import com.dsky.baas.pointsservice.model.ExchangeOrder;

public class ExchRMIServiceImpl {
	IExchangeOrderService iExchangeOrderService;

	public IExchangeOrderService getiExchangeOrderService() {
		return iExchangeOrderService;
	}

	public void setiExchangeOrderService(IExchangeOrderService iExchangeOrderService) {
		this.iExchangeOrderService = iExchangeOrderService;
	}

	public List<ExchangeOrder> getExchangeOrderByGameId(int gameId,int startRow,int endRow) throws ConnectException,RemoteLookupFailureException,RemoteAccessException{
		List<ExchangeOrder> list = null;
		list = iExchangeOrderService.getExchangeOrderByGameId(gameId, startRow, endRow);
 		return  list;
	}

	public int getExchangeOrderTotalNum(int gameId) throws ConnectException,RemoteLookupFailureException,RemoteAccessException {
		return iExchangeOrderService.getExchangeOrderTotalNum(gameId);
	}

	public int updateExchangeOrderStatus(String orderId,String status) throws ConnectException,RemoteLookupFailureException,RemoteAccessException{
		return iExchangeOrderService.updateExchangeOrderStatus(orderId, status);
	}

	public int updateExchangeOrderStatus(String orderId,String status,String operation_memo) throws ConnectException,RemoteLookupFailureException,RemoteAccessException{
		return iExchangeOrderService.updateExchangeOrderStatus(orderId, status, operation_memo);
	}
	
	public int updateExchangeOrderStatusByQuantity(String []orderId,String status) throws ConnectException,RemoteLookupFailureException,RemoteAccessException{
		return iExchangeOrderService.updateExchangeOrderStatusByQuantity(orderId, status);
	}
	
	public List<ExchangeOrder> getExchangeOrderBystatus(int gameId,String status,int startRow,int endRow) throws ConnectException,RemoteLookupFailureException,RemoteAccessException{
		return iExchangeOrderService.getExchangeOrderBystatus(gameId, status, startRow, endRow);
	}
	
	public int getExchangeOrderTotalNum(int gameId,String status)throws ConnectException,RemoteLookupFailureException,RemoteAccessException{
		return iExchangeOrderService.getExchangeOrderTotalNum(gameId, status);
	}
	
	public ExchangeOrder getExchangeOrderByOrderId(String orderId){
		return iExchangeOrderService.getExchangeOrderByOrderId(orderId);
	}

}
