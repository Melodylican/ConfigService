package com.dsky.baas.configservice.service;

import java.util.List;

import com.dsky.baas.pointsservice.model.PointsLog;

public interface IPointsLogService {
	

	
	public void log(int playerId, int fromPlayerId,int gameId,int actid,String eventName,String eventArgs,int addNum,int shouldNum,
			String memo,String code,int createTime,int afterNum);
	
	/**
	 * @author eaves.zhu 2016.8.16
	 * 配置系统查询日志,分页查询
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<PointsLog> getPonitsLog(int uid, int gid,int startRow,int endRow);
	
	/**
	 * @author eaves.zhu
	 * 配置系统按需求查询日期
	 * @param DateFrom  日期开始
	 * @param  DateTo	日期
	 */
	public List<PointsLog> getPonitsLog(int uid, int gid,int startRow,int endRow,boolean from,int DateFrom,boolean to,int DateTo);
	//public List<PointsLog> getPointsLogByDate();
	
	/**
	 * @author eaves.zhu 2016.8.16
	 * 日志系统查询总数
	 * @param uid
	 * @param gid
	 * @return
	 */
	public int getPonitsLogCount(int uid, int gid);
	public int getPonitsLogCount(int uid, int gid,boolean from,int DateFrom,boolean to,int DateTo);
}
