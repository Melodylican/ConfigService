/**   
 * @文件名称: StatisticsUtils.java
 * @类路径: com.dsky.baas.configservice.util
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2016年12月13日 下午4:09:06
 * @版本：V1.0   
 */
package com.dsky.baas.configservice.util;

import java.util.List;
import com.dsky.baas.configservice.model.ActInfoBean;
import com.dsky.baas.configservice.model.PayInfoBean;
import com.dsky.baas.configservice.model.Statistics;
import com.dsky.baas.configservice.model.StatisticsSeriesBean;
import com.alibaba.fastjson.JSON;
/**
 * @类功能说明：作为数据统计展示的工具类 针对于邀请人数及付费情况的数据封装
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年12月13日 下午4:09:06
 * @版本：V1.0
 */
public class StatisticsUtils {
	public static Statistics getSeriesJson(List<PayInfoBean> list) {
		if(list != null && list.size() >=1) {
			String[] date = new String[list.size()];
			Integer[] data1 = new Integer[list.size()];//存储新增用户数
			Integer[] data2 = new Integer[list.size()];//存储付费用户数
			Float[] data3 = new Float[list.size()];//存储付费金额

			int i=0;
			for(PayInfoBean p:list) {
				date[i] = p.getDate();
				data1[i] = p.getInvitedPeople();
				data2[i] = p.getPayingPeople();
				data3[i] = p.getPayingAmount();
				i++;
			}
			StatisticsSeriesBean[] ssbArray = new StatisticsSeriesBean[3];
			StatisticsSeriesBean ssb1 = new StatisticsSeriesBean();
			ssb1.setName("新增用户数");
			ssb1.setData(data1);
			StatisticsSeriesBean ssb2 = new StatisticsSeriesBean();
			ssb2.setName("付费用户数");
			ssb2.setData(data2);
			StatisticsSeriesBean ssb3 = new StatisticsSeriesBean();
			ssb3.setName("付费金额");
			ssb3.setData(data3);
			ssbArray[0] = ssb1;
			ssbArray[1] = ssb2;
			ssbArray[2] = ssb3;
			Statistics s = new Statistics();
			s.setCategories(JSON.toJSONString(date));
			s.setSeries(JSON.toJSONString(ssbArray));
			return s;
		}
		return null;
	}
	
	/**
	 * 
	 * 方法功能说明：将数据封装成json格式供前端展示
	 * 创建：2016年12月15日 by chris.li 
	 * 修改：日期 by 修改者
	 * 修改内容：
	 * @参数： @param list
	 * @参数： @param type
	 * @参数： @return    
	 * @return Statistics   
	 * @throws
	 */
	public static Statistics getSeriesJson(List<ActInfoBean> list,String type) {
		if(list != null && list.size() >=1) {
			switch(type) {
				case "pic3":
					String[] date = new String[list.size()];
					Integer[] data1 = new Integer[list.size()];//存储活动入口点击数
					Integer[] data2 = new Integer[list.size()];//存储分享数
					Integer[] data3 = new Integer[list.size()];//存储下载链接点击数
					Integer[] data4 = new Integer[list.size()];//存储新增玩家数
					Integer[] data5 = new Integer[list.size()];//存储页面浏览次数
					Integer[] data6_1 = new Integer[list.size()];//存储分享成功次数

					int i=0;
					for(ActInfoBean p:list) {
						date[i] = p.getDate();
						data1[i] = p.getClickActivity();//点击数
						data2[i] = p.getShareCode();//分享次数
						data3[i] = p.getClickCode();//活动入口点击数
						data4[i] = p.getInvitedPeople();//邀请人数
						data5[i] = p.getQq()+p.getWeibo()+p.getWeiXinFriends()+p.getWeiXinMoments();//页面浏览次数
						data6_1[i] = p.getShareSuccess();//分享成功次数
						System.out.println(p.getShareSuccess()+"  ...");
						i++;
					}
					StatisticsSeriesBean[] ssbArray = new StatisticsSeriesBean[6];
					StatisticsSeriesBean ssb1 = new StatisticsSeriesBean();
					ssb1.setName("活动入口点击数");
					ssb1.setData(data1);
					StatisticsSeriesBean ssb2 = new StatisticsSeriesBean();
					ssb2.setName("分享次数");
					ssb2.setData(data2);
					StatisticsSeriesBean ssb3 = new StatisticsSeriesBean();
					ssb3.setName("下载点击数");
					ssb3.setData(data3);
					StatisticsSeriesBean ssb4 = new StatisticsSeriesBean();
					ssb4.setName("新增玩家数");
					ssb4.setData(data4);
					StatisticsSeriesBean ssb5 = new StatisticsSeriesBean();
					ssb5.setName("页面浏览次数");
					ssb5.setData(data5);
					
					StatisticsSeriesBean ssb6_1 = new StatisticsSeriesBean();
					ssb6_1.setName("分享成功次数");
					ssb6_1.setData(data6_1);
					
					ssbArray[0] = ssb1;
					ssbArray[1] = ssb2;
					ssbArray[2] = ssb3;
					ssbArray[3] = ssb4;
					ssbArray[4] = ssb5;
					ssbArray[5] = ssb6_1;
					Statistics s = new Statistics();
					s.setCategories(JSON.toJSONString(date));
					s.setSeries(JSON.toJSONString(ssbArray));
					return s;
			case "pic2":
					String[] date2 = new String[list.size()];
					Integer[] data6 = new Integer[list.size()];//存储微信朋友分享次数
					Integer[] data7 = new Integer[list.size()];//存储朋友圈次数
					Integer[] data8 = new Integer[list.size()];//存储qq分享次数

					int j=0;
					for(ActInfoBean p:list) {
						date2[j] = p.getDate();
						data6[j] = p.getWeiXinFriends();//微信分享次数
						data7[j] = p.getWeiXinMoments();//朋友圈分享次数
						data8[j] = p.getQq();//QQ分享次数
						j++;
					}
					StatisticsSeriesBean[] ssbArray2 = new StatisticsSeriesBean[3];
					StatisticsSeriesBean ssb6 = new StatisticsSeriesBean();
					ssb6.setName("微信朋友浏览次数");
					ssb6.setData(data6);
					StatisticsSeriesBean ssb7 = new StatisticsSeriesBean();
					ssb7.setName("朋友圈浏览次数");
					ssb7.setData(data7);
					StatisticsSeriesBean ssb8 = new StatisticsSeriesBean();
					ssb8.setName("QQ好友浏览次数");
					ssb8.setData(data8);
					ssbArray2[0] = ssb6;
					ssbArray2[1] = ssb7;
					ssbArray2[2] = ssb8;
					Statistics s2 = new Statistics();
					s2.setCategories(JSON.toJSONString(date2));
					s2.setSeries(JSON.toJSONString(ssbArray2));
					return s2;
			}
		}
		return null;
	}
}
