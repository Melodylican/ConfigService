/**   
 * @文件名称: RedisTemplateTest.java
 * @类路径: com.idreamsky.promoter.test
 * @描述: TODO
 * @作者：chris.li(李灿)
 * @时间：2016年12月21日 上午10:02:20
 * @版本：V1.0   
 */
package com.idreamsky.promoter.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import com.dsky.baas.configservice.dao.RedisMethonds;
import com.dsky.baas.configservice.model.PromoterBean;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：dsky
 * @作者：chris.li
 * @创建时间：2016年12月21日 上午10:02:20
 * @版本：V1.0
 */
@Configuration("file:D:\\app.xml")
public class RedisTemplateTest {
	private static RedisTemplate rt;
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("file:D:\\app.xml");
		rt = (RedisTemplate) ctx.getBean("redisTemplate");
		PromoterBean pb = new PromoterBean();
		pb.setId("6");
		pb.setGameId("11076");
		pb.setGameName("圣斗士");
		pb.setGameType("10级");
		rt.opsForValue().set("act_11076", pb);
		PromoterBean p = (PromoterBean) rt.opsForValue().get("act_11076");
		
		System.out.println(p.toString());
		
	}

}
