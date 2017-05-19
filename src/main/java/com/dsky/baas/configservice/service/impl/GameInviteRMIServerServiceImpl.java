/**   
 * @文件名称: GameInviteRMIServerServiceImpl.java
 * @类路径: com.dsky.baas.configservice.service.impl
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2017年5月4日 上午10:46:03
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.service.impl;

import com.dsky.baas.configservice.service.IGameInviteRMIServer;
import com.dsky.baas.gameinvite.model.InvitedCode;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2017年5月4日 上午10:46:03
 * @版本：V1.0
 */
public class GameInviteRMIServerServiceImpl {
	private IGameInviteRMIServer gameInviteService;

	public IGameInviteRMIServer getGameInviteService() {
		return gameInviteService;
	}

	public void setGameInviteService(IGameInviteRMIServer gameInviteService) {
		this.gameInviteService = gameInviteService;
	}
	
	public InvitedCode getInvitedCodeByCode(String code) throws Exception {
		return gameInviteService.getInvitedCodeByCode(code);
	}
	
	public InvitedCode getInvitedCodeByPlayerIdAndGameIdAndActId(int playerId, int gameId, int actId) throws Exception {
		return gameInviteService.getInvitedCodeByPlayerIdAndGameIdAndActId(playerId, gameId, actId);
	}
	
}
