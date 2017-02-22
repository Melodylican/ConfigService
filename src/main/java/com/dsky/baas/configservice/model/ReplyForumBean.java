/**   
 * @文件名称: ReplyForumBean.java
 * @类路径: com.dsky.baas.configservice.model
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2017年1月19日 下午2:19:12
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.model;

import com.dsky.baas.configservice.util.DateUtil;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2017年1月19日 下午2:19:12
 * @版本：V1.0
 */
public class ReplyForumBean {
	private String gid;
	private String cid;
	private String rstat;
	private String rid;
	private String content;
	private String from_nickname;
	private String to_nickname;
	private String from_uid;
	private String to_uid;
	private String createtime;

	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getRstat() {
		return rstat;
	}
	public void setRstat(String rstat) {
		this.rstat = rstat;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFrom_nickname() {
		return from_nickname;
	}
	public void setFrom_nickname(String from_nickname) {
		this.from_nickname = from_nickname;
	}
	public String getTo_nickname() {
		return to_nickname;
	}
	public void setTo_nickname(String to_nickname) {
		this.to_nickname = to_nickname;
	}
	public String getFrom_uid() {
		return from_uid;
	}
	public void setFrom_uid(String from_uid) {
		this.from_uid = from_uid;
	}
	public String getTo_uid() {
		return to_uid;
	}
	public void setTo_uid(String to_uid) {
		this.to_uid = to_uid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public String getCreatetime(int tag) {
		return DateUtil.parseDate(Integer.parseInt(createtime)*1000L,"yyyy/MM/dd HH:mm:ss");
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	@Override
	public String toString() {
		return "ReplyForumBean [gid=" + gid + ", cid=" + cid + ", rstat="
				+ rstat + ", rid=" + rid + ", content=" + content
				+ ", from_nickname=" + from_nickname + ", to_nickname="
				+ to_nickname + ", from_uid=" + from_uid + ", to_uid=" + to_uid
				+ ", createtime=" + createtime + "]";
	}
}
