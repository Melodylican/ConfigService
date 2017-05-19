/**   
 * @文件名称: IUserInfo.java
 * @类路径: com.dsky.baas.configservice.service
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2016年11月25日 下午4:32:45
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.service;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年11月25日 下午4:32:45
 * @版本：V1.0
 */
public interface IUserInfo {
	/**
	 * 
	 * 方法功能说明：用户获得用户的信息
	 * 创建：2017年1月19日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param userId
	 * @参数： @param gameId
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	public String getUserInfo(String userId,String gameId);

	/**
	 * 方法功能说明：用于获得用户的吐槽信息
	 * 创建：2017年1月19日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param gameId
	 * @参数： @param offset
	 * @参数： @param pageSize
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	public String getForumInfo(String actId,String gameId, int offset, int pageSize);


	/**
	 * 
	 * 方法功能说明：用户回复用户的吐槽
	 * 创建：2017年1月19日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param actId
	 * @参数： @param gameId
	 * @参数： @param content
	 * @参数： @param cid
	 * @参数： @param from_uid
	 * @参数： @param to_uid
	 * @参数： @param from_nickname
	 * @参数： @param to_nickname
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	public String replyForum(String actId,String gameId, String content,String cid,
			String from_uid, String to_uid, String from_nickname,
			String to_nickname);
	/**
	 * 方法功能说明：用于消息置顶
	 * 创建：2017年4月28日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param cid
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	public String move2top(String cid);
	/**
	 * 方法功能说明：用于取消消息置顶
	 * 创建：2017年4月28日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param cid
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	public String cancleMove2top(String cid);
	/**
	 * 
	 * 方法功能说明：处理删除某条吐槽或者回复的功能
	 * 创建：2017年4月28日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param cid
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	public String deleteforum(String cid);
	/**
	 * 
	 * 方法功能说明：删除吐槽的某条回复
	 * 创建：2017年4月28日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param rid
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	public String deleteforumreply(String rid);
	
	/**
	 * 方法功能说明：支持对某条回复进行修改
	 * 创建：2017年4月29日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param rid
	 * @参数： @param content
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	public String updateforumreply(String rid,String content);
	/**
	 * 方法功能说明：调用发送短信的方法
	 * 创建：2017年5月5日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param mobile
	 * @参数： @param content
	 * @参数： @return    
	 * @return String   
	 * @throws
	 */
	public String sendShortMsg(String mobile,String content);

}




