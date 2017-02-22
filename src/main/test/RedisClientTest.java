


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;

import com.idreamsky.api.lbs.model.RedisClient;


/** 
 * @ClassName: ConfigCacheTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author jimmy.deng 
 * @date 2014年5月19日 下午3:06:06 
 *  
 */
public class RedisClientTest {
	BeanFactory context = null;
	RedisClient redisClient;

	@Before
	public void setUp() throws Exception {
		context = BeanFactoryTest.getContext();
		redisClient = (RedisClient) context.getBean("redisClient");
	
	}

	
	@Test
	public void getConfigTest(){
		
	}
	
}
