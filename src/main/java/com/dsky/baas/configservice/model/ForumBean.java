/**   
 * @文件名称: ForumBean.java
 * @类路径: com.dsky.baas.configservice.model
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2017年1月19日 下午2:15:40
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.model;

import java.util.List;

import com.dsky.baas.configservice.util.DateUtil;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2017年1月19日 下午2:15:40
 * @版本：V1.0
 */
public class ForumBean {
	private String userId; 
	private String cid; //评论ID
	private String tid; //主题ID
	private String content; //评论类容
	private String nickname; //用户昵称
	private String gameId; //游戏ID
	private String createTime; //评论发表时间
	private String cstat; //评论状态
	private String extend; //扩展字段
	private int isTop;  //评论置顶
	private String extend_2; //扩张字段
	private String updatetime;//更新时间
	private String deletetime;//删除时间
	private List<ReplyForumBean> rfb;
	
	public ForumBean(){}
	
	/**
	 *@类名：ForumBean.java
	 *@描述：{todo}
	 * @param userId
	 * @param cid
	 * @param content
	 * @param nickname
	 * @param gameId
	 * @param createTime
	 * @param cstat
	 */
	public ForumBean(String userId, String cid, String content,
			String nickname, String gameId, String createTime, String cstat,String tid,String extend,int isTop) {
		super();
		this.userId = userId;
		this.cid = cid;
		this.content = content;
		this.nickname = nickname;
		this.gameId = gameId;
		this.createTime = createTime;
		this.cstat = cstat;
		this.tid = tid;
		this.extend = extend;
		this.isTop = isTop;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public String getCreateTime(int tag) {
		return DateUtil.parseDate(Integer.parseInt(createTime)*1000L,"yyyy/MM/dd HH:mm:ss");
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCstat() {
		return cstat;
	}
	public void setCstat(String cstat) {
		this.cstat = cstat;
	}
	public List<ReplyForumBean> getRfb() {
		return rfb;
	}
	public void setRfb(List<ReplyForumBean> rfb) {
		this.rfb = rfb;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}

	public String getExtend_2() {
		return extend_2;
	}

	public void setExtend_2(String extend_2) {
		this.extend_2 = extend_2;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getDeletetime() {
		return deletetime;
	}

	public void setDeletetime(String deletetime) {
		this.deletetime = deletetime;
	}

	@Override
	public String toString() {
		return "ForumBean [userId=" + userId + ", cid=" + cid + ", tid=" + tid
				+ ", content=" + content + ", nickname=" + nickname
				+ ", gameId=" + gameId + ", createTime=" + createTime
				+ ", cstat=" + cstat + ", extend=" + extend + ", isTop="
				+ isTop + ", extend_2=" + extend_2 + ", updatetime="
				+ updatetime + ", deletetime=" + deletetime + ", rfb=" + rfb
				+ "]";
	}
}
