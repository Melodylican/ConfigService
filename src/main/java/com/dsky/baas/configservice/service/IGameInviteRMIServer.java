package com.dsky.baas.configservice.service;

import com.dsky.baas.gameinvite.model.InvitedCode;

public interface IGameInviteRMIServer {
	public InvitedCode getInvitedCodeByCode(String code);
	public InvitedCode getInvitedCodeByPlayerIdAndGameIdAndActId(int playerId, int gameId, int actId);
}
