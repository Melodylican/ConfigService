package com.dsky.baas.configservice.model;

import java.io.Serializable;
/**
 * @ClassName: ApiResultBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class ExchApiResultBean implements Serializable{

	private static final long serialVersionUID = -8663247821365544626L;
		//访问状态码
		private int code ;
		//访问附加信息
		private String msg ;
		//兑换配置id号
		private String id;
		//游戏名称
		private String gameName;
		//游戏Id
		private String gameId;
		//兑换开始时间
		private long exchBeginTime;
		//兑换结束时间
		private long exchEndTime;
		//兑换积分上限
		private int exchLimit;
		//兑换周期（每周、每天）
		private int period1;
		//兑换周期(星期一,星期二,...)
		private int period2;
		//兑换标准(配置多少积分兑换多少元,这个参数用于配置积分)
		private int exchStandard1;
		//兑换标准(配置多少积分兑换多少元,这个参数用于配置元)
		private int exchStandard2;
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getGameName() {
			return gameName;
		}
		public void setGameName(String gameName) {
			this.gameName = gameName;
		}
		public String getGameId() {
			return gameId;
		}
		public void setGameId(String gameId) {
			this.gameId = gameId;
		}
		public int getExchLimit() {
			return exchLimit;
		}
		public void setExchLimit(int exchLimit) {
			this.exchLimit = exchLimit;
		}
		public int getPeriod1() {
			return period1;
		}
		public void setPeriod1(int period1) {
			this.period1 = period1;
		}
		public int getPeriod2() {
			return period2;
		}
		public void setPeriod2(int period2) {
			this.period2 = period2;
		}
		public int getExchStandard1() {
			return exchStandard1;
		}
		public void setExchStandard1(int exchStandard1) {
			this.exchStandard1 = exchStandard1;
		}
		public int getExchStandard2() {
			return exchStandard2;
		}
		public void setExchStandard2(int exchStandard2) {
			this.exchStandard2 = exchStandard2;
		}
		public long getExchBeginTime() {
			return exchBeginTime;
		}
		public void setExchBeginTime(long exchBeginTime) {
			this.exchBeginTime = exchBeginTime;
		}
		public long getExchEndTime() {
			return exchEndTime;
		}
		public void setExchEndTime(long exchEndTime) {
			this.exchEndTime = exchEndTime;
		}
}
