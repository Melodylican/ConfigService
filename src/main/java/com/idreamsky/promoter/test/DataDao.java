package com.idreamsky.promoter.test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.dsky.baas.configservice.model.PromoterBean;

/**
 * @ClassName: DataDao
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 * @date 2015年7月25日 
 * 
 */
@Repository
public class DataDao {
	@Resource
	private JdbcTemplate jdbcTemplate;
	private int rowCount=0;
	private static final Logger logger = Logger.getLogger(DataDao.class);
	public List<PromoterBean> getByPaging(int page,int pageSize) {
		final List<PromoterBean> list = new ArrayList<PromoterBean>();
		String sql = "select * from tb_promoter ORDER BY id desc limit "+((page-1)*pageSize)+","+pageSize;
		logger.info("DataDao sql ============="+sql);
		this.jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) {
				try {
						PromoterBean pb = new PromoterBean();
						pb.setId(rs.getInt("id")+"");
						pb.setGameId(rs.getString("gameId"));
						pb.setGameName(rs.getString("gameName"));
						pb.setLocation(rs.getString("location"));
						//pb.setDescription(rs.getString("description"));
						pb.setBeginTime(rs.getTimestamp("beginTime")+"");
						pb.setEndTime(rs.getTimestamp("endTime")+"");
						pb.setPreheatingTime(rs.getTimestamp("preheatingTime")+"");
						pb.setGameType(rs.getString("gameType"));
						list.add(pb);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return list;
	}
	
	public int getTotalRows() {
		String sql = "select * from tb_promoter";
		this.jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) {
				try {
					rs.last();
					rowCount = rs.getRow(); //获得ResultSet的总行数
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return rowCount;
	}
	
	public List<PromoterBean> getBySearch(String gameName,String location,String beginTime,String endTime,int page,int pageSize) {
		final List<PromoterBean> list = new ArrayList<PromoterBean>();
		String sql = "select * from tb_promoter where gameName='"+gameName+"' and location='"+location+"'"+" and beginTime >= '"+beginTime+"' and endTime <='"+endTime+"'"+"ORDER BY id desc limit "+((page-1)*pageSize)+","+pageSize;
		logger.info("DataDao sql ============="+sql);
		this.jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) {
				try {
					PromoterBean pb = new PromoterBean();
					pb.setId(rs.getInt("id")+"");
					pb.setGameId(rs.getString("gameId"));
					pb.setGameName(rs.getString("gameName"));
					pb.setLocation(rs.getString("location"));
					//pb.setDescription(rs.getString("description"));
					pb.setBeginTime(rs.getTimestamp("beginTime")+"");
					pb.setEndTime(rs.getTimestamp("endTime")+"");
					pb.setPreheatingTime(rs.getTimestamp("preheatingTime")+"");
					pb.setGameType(rs.getString("gameType"));
					list.add(pb);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return list;
	}

	

}
