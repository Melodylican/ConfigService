package com.dsky.baas.configservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.baas.configservice.model.GameRedeemCodeBean;
import com.dsky.baas.configservice.redeemcode.dao.RedeemCodeMapper;
import com.dsky.baas.configservice.service.IRedeemCodeService;
@Service("redeemCodeService")
public class RedeemCodeServiceImpl implements IRedeemCodeService {
	@Autowired
	private RedeemCodeMapper redeemCodeMapper;
	private static final Logger logger = Logger.getLogger(RedeemCodeServiceImpl.class);

	@Override
	public List<GameRedeemCodeBean> selectRedeemCodeByPaging(int gameId,int actId,String searchScore,int startRow,int endRow,int bTime,int eTime,String status,String playerId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("gameId", gameId);
		map.put("actId", actId);
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("status", status);
		map.put("searchScore", searchScore);
		map.put("playerId", playerId);
		return redeemCodeMapper.selectRedeemCodeByPaging(map);
	}

	@Override
	public int getRedeemCodeTotalRows(int gameId,int actId,int bTime,int eTime,String status,String searchScore,String playerId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("gameId", gameId);
		map.put("actId", actId);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("status", status);
		map.put("searchScore", searchScore);
		map.put("playerId", playerId);
		return redeemCodeMapper.getRedeemCodeTotalRows(map);
	}

	@Override
	public int updateRedeemCodeState(int id,int status) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("status", status);
		return redeemCodeMapper.updateRedeemCodeState(map);
	}

	@Override
	public int updateRedeemCodeById(GameRedeemCodeBean gameRedeemCodeBean) {
		return redeemCodeMapper.updateRedeemCodeById(gameRedeemCodeBean);
	}

	@Override
	public int insertRedeemCode(GameRedeemCodeBean gameRedeemCodeList) {
		return redeemCodeMapper.insertRedeemCode(gameRedeemCodeList);
	}

	@Override
	public int deleteRedeemCodeById(int id) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("delTime", System.currentTimeMillis()/1000);
		return redeemCodeMapper.deleteRedeemCodeById(map);
	}

	@Override
	public int insertRedeemCodeBatch(List<GameRedeemCodeBean> list) {
		return redeemCodeMapper.insertRedeemCodeBatch(list);
	}

	/*用于查询兑换码积分类别
	 * @see com.dsky.baas.configservice.service.IRedeemCodeService#getScoreDistinct()
	 */
	@Override
	public List<String> getScoreDistinct(int gameId,int actId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("gameId", gameId);
		map.put("actId", actId);
		return redeemCodeMapper.getScoreDistinct(map);
	}

	/*(non-Javadoc)
	 * @see com.dsky.baas.configservice.service.IRedeemCodeService#ifRedeemCodeExists(int, int, java.lang.String)
	 */
	@Override
	public int ifRedeemCodeExists(int gameId, int actId, String code) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("gameId", gameId);
		map.put("actId", actId);
		map.put("code", code);
		return redeemCodeMapper.ifRedeemCodeExists(map);
	}

	@Override
	public int ifRedeemCodeExistsUpdate(int gameId, int actId, String code,
			int id) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("gameId", gameId);
		map.put("actId", actId);
		map.put("code", code);
		map.put("id", id);
		return redeemCodeMapper.ifRedeemCodeExistsUpdate(map);
	}

	@Override
	public int deleteRedeemCode(int gameId, int actId, int status,int searchScore) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("gameId", gameId);
		map.put("actId", actId);
		map.put("status", status);
		map.put("score", searchScore);
		map.put("delTime", System.currentTimeMillis()/1000);
		return redeemCodeMapper.deleteRedeemCode(map);
	}

}
