/**   
 * @文件名称: UserInfoService.java
 * @类路径: com.dsky.baas.configservice.service.impl
 * @描述: 用于访问调用用户api
 * @作者：chris.li(李灿)
 * @时间：2016年11月25日 下午4:33:46
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dsky.baas.configservice.controller.OrderController;
import com.dsky.baas.configservice.service.IUserInfo;
import com.dsky.baas.configservice.util.HttpUtils;

/**
 * @类功能说明：用于访问调用用户api
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年11月25日 下午4:33:46
 * @版本：V1.0
 */
public class UserInfoService implements IUserInfo {
	
	private String userInfoUrl;//获取用户信息的api
	private String forumUrl;//获取用户吐槽信息的api //192.168.4.115:9999/commentsandreplies?tid=1&gid=11292&offset=2&pagesize=1
	private String forumReply;//官方回复用户吐槽信息的api
	private String move2top;//置顶api
	private String cancleMove2top;//取消置顶api
	private String deleteForum;//删除某条吐槽的api
	private String deleteForumReply;//删除某条吐槽回复的api
	private String updateForumReply;//修改每条吐槽回复的api
	private String shortMsgUrl;
	private static final Logger logger = Logger.getLogger(UserInfoService.class);
	public String getUserInfoUrl() {
		return userInfoUrl;
	}

	public void setUserInfoUrl(String userInfoUrl) {
		this.userInfoUrl = userInfoUrl;
	}
	
	public String getForumUrl() {
		return forumUrl;
	}

	public void setForumUrl(String forumUrl) {
		this.forumUrl = forumUrl;
	}

	public String getForumReply() {
		return forumReply;
	}

	public void setForumReply(String forumReply) {
		this.forumReply = forumReply;
	}
	
	public String getMove2top() {
		return move2top;
	}

	public void setMove2top(String move2top) {
		this.move2top = move2top;
	}

	public String getCancleMove2top() {
		return cancleMove2top;
	}

	public void setCancleMove2top(String cancleMove2top) {
		this.cancleMove2top = cancleMove2top;
	}
	
	public String getDeleteForum() {
		return deleteForum;
	}

	public void setDeleteForum(String deleteForum) {
		this.deleteForum = deleteForum;
	}

	public String getDeleteForumReply() {
		return deleteForumReply;
	}

	public void setDeleteForumReply(String deleteForumReply) {
		this.deleteForumReply = deleteForumReply;
	}
	
	public String getUpdateForumReply() {
		return updateForumReply;
	}

	public void setUpdateForumReply(String updateForumReply) {
		this.updateForumReply = updateForumReply;
	}
	
	public String getShortMsgUrl() {
		return shortMsgUrl;
	}

	public void setShortMsgUrl(String shortMsgUrl) {
		this.shortMsgUrl = shortMsgUrl;
	}

	/*userInfoUrl 通过配置文件进行配置
	 * @see com.dsky.baas.configservice.service.IUserInfo#getUserInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public String getUserInfo(String userId, String gameId) {
		String requestUrl = this.userInfoUrl+"?uid="+userId+"&gid="+gameId;
		logger.info("访问的Url： "+requestUrl);
		return HttpUtils.httpRequest(requestUrl);
	}
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                logger.info(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public  String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
	@Override
	public String getForumInfo(String actId,String gameId,int offset,int pageSize) {
		String requestUrlParams = "actId="+actId+"&gid="+gameId+"&offset="+offset+"&pagesize="+pageSize+"&field=updatetime&order=desc&rfield=createtime&rorder=asc";
		logger.info("访问的Url： "+this.forumUrl+" 传递的参数是："+requestUrlParams);
		return sendGet(this.forumUrl.trim(),requestUrlParams);
	}
	
	@Override
	public String replyForum(String actId,String gameId,String content,String cid,String from_uid,String to_uid,String from_nickname,String to_nickname) {
		String requestUrlParams="";
		try {
			requestUrlParams = "actId="+actId+"&gid="+gameId+"&content="+URLEncoder.encode(content,"utf-8")+"&cid="+cid+"&from_uid="+from_uid+"&to_uid="+to_uid+"&from_nickname="+from_nickname+"&to_nickname="+to_nickname;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("访问的Url： "+this.forumReply+"  传递的参数是： "+requestUrlParams);		
		return sendGet(this.forumReply.trim(), requestUrlParams);
		//return HttpUtils.httpRequest(requestUrl);
	}
	
	@Override
	public String move2top(String cid) {
		String requestUrlParams="";
		requestUrlParams = "cid="+cid;
		logger.info("访问的Url： "+this.move2top+"  传递的参数是： "+requestUrlParams);
		return sendGet(this.move2top.trim(), requestUrlParams);
	}
	
	@Override
	public String cancleMove2top(String cid) {
		String requestUrlParams="";
		requestUrlParams = "cid="+cid;
		logger.info("访问的Url： "+this.cancleMove2top+"  传递的参数是： "+requestUrlParams);
		return sendGet(this.cancleMove2top.trim(), requestUrlParams);
	}

	@Override
	public String deleteforum(String cid) {
		String requestUrlParams="";
		requestUrlParams = "cid="+cid;
		logger.info("访问的Url： "+this.deleteForum+"  传递的参数是： "+requestUrlParams);
		return sendGet(this.deleteForum.trim(), requestUrlParams);
	}

	@Override
	public String deleteforumreply(String rid) {
		String requestUrlParams="";
		requestUrlParams = "rid="+rid;
		logger.info("访问的Url： "+this.deleteForumReply+"  传递的参数是： "+requestUrlParams);
		return sendGet(this.deleteForumReply.trim(), requestUrlParams);
	}
	
	@Override
	public String updateforumreply(String rid,String content) {
		String requestUrlParams="";
		try {
			requestUrlParams = "content="+URLEncoder.encode(content,"utf-8")+"&rid="+rid;
			logger.info("访问的Url： "+this.updateForumReply+"  传递的参数是： "+requestUrlParams);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sendGet(this.updateForumReply.trim(), requestUrlParams);
	}
	
	@Override
	public String sendShortMsg(String mobile,String content) {
		String requestUrlParams="";
		try {
			requestUrlParams = "content="+URLEncoder.encode(content,"utf-8")+"&mobile="+mobile;
			logger.info("访问的Url： "+this.shortMsgUrl+"  传递的参数是： "+requestUrlParams);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sendPost(this.shortMsgUrl.trim(), requestUrlParams);
	}

}
