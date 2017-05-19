package com.dsky.baas.configservice.service.impl;

import java.rmi.ConnectException;
import java.util.List;

import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteLookupFailureException;

import com.dsky.baas.configservice.service.IPointsLogService;
import com.dsky.baas.pointsservice.model.PointsLog;

public class RMIServiceImpl {
	IPointsLogService pointsLogService;

	public IPointsLogService getPointsLogService() {
		return pointsLogService;
	}

	public void setPointsLogService(IPointsLogService pointsLogService) {
		this.pointsLogService = pointsLogService;
	}
	
	public List<PointsLog> getPonitsLog(int uid, int gid,int startRow,int endRow,boolean begin,int beginTime,boolean end,int endTime) throws ConnectException,RemoteLookupFailureException,RemoteAccessException{
		return pointsLogService.getPonitsLog(uid, gid,startRow,endRow,begin,beginTime,end,endTime);
	}
	
	public int getPonitsLogCount(int uid,int gid,boolean begin,int beginTime,boolean end,int endTime) throws ConnectException,RemoteLookupFailureException,RemoteAccessException {
		return pointsLogService.getPonitsLogCount(uid, gid,begin,beginTime,end,endTime);
	}
	
	public int getPonitsLogCount(int uid, int gid) throws ConnectException,RemoteLookupFailureException,RemoteAccessException{
		return pointsLogService.getPonitsLogCount(uid, gid);
	}
	/**
	 * 
	 * 方法功能说明：用于查询该账号的总积分
	 * 创建：2017年5月4日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param uid
	 * @参数： @param gid
	 * @参数： @param actid
	 * @参数： @return    
	 * @return int   
	 * @throws
	 */
	public int getPoints(int uid,int gid,int actid){
		return pointsLogService.getPoints(uid,gid,actid);
	}

}
