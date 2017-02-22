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

}
