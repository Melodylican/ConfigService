/**
 * IRmiServer.java
 * ��Ȩ����(C) 2012 
 * ����:cuiran 2012-08-08 11:12:40
 */
package com.idreamsky.promoter.test.rmi;

import com.dsky.baas.configservice.model.ApiResultBean;

/**
 * TODO
 * @author chris.li
 */
public interface IRmiServer {
	
	public boolean test();
	public ApiResultBean getOption(String gameId,String location);
	public ApiResultBean getOptionById(String id);

}
