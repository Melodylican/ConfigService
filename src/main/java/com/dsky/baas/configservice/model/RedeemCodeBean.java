package com.dsky.baas.configservice.model;
/**
 * @ClassName: RedeemCodeBean
 * @Description: TODO(兑换码配置信息)
 * @author Chris.li
 */
public class RedeemCodeBean {
	private int id;
	private int deviceCount;//单台设备可生成邀请码个数
	private int recommandCount;//一个邀请码可以有效使用的次数
	private int deviceLimit;//活动参与设备次数限制
	private String h5Url;//邀请码分享的连接地址
	private String imgUrl;//分享小图片的服务器地址
	private String title;//分享标题
	private String redeemDesc;//分享描述
    private String shareMethod;//可分享的方式（微信，朋友圈，QQ，微博）
    private String callbackUrl;//用于回调通知游戏朋友关系
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeviceCount() {
		return deviceCount;
	}
	public void setDeviceCount(int deviceCount) {
		this.deviceCount = deviceCount;
	}
	public int getRecommandCount() {
		return recommandCount;
	}
	public void setRecommandCount(int recommandCount) {
		this.recommandCount = recommandCount;
	}
	public String getH5Url() {
		return h5Url;
	}
	public void setH5Url(String h5Url) {
		this.h5Url = h5Url;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRedeemDesc() {
		return redeemDesc;
	}
	public void setRedeemDesc(String redeemDesc) {
		this.redeemDesc = redeemDesc;
	}
	public String getShareMethod() {
		return shareMethod;
	}
	public void setShareMethod(String shareMethod) {
		this.shareMethod = shareMethod;
	}
	public int getDeviceLimit() {
		return deviceLimit;
	}
	public void setDeviceLimit(int deviceLimit) {
		this.deviceLimit = deviceLimit;
	}
	
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	@Override
	public String toString() {
		return "RedeemCodeBean [id=" + id + ", deviceCount=" + deviceCount
				+ ", recommandCount=" + recommandCount + ", deviceLimit="
				+ deviceLimit + ", h5Url=" + h5Url + ", imgUrl=" + imgUrl
				+ ", title=" + title + ", redeemDesc=" + redeemDesc
				+ ", shareMethod=" + shareMethod + ", callbackUrl="
				+ callbackUrl + "]";
	}

}
