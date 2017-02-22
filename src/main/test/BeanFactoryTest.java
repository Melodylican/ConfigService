

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @ClassName: BeanFactoryTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jimmy.deng
 * @date 2014年5月19日 下午7:41:28
 * 
 */
public class BeanFactoryTest {
	public static FileSystemXmlApplicationContext context;
	static Lock lock = new ReentrantLock(true);

	public static BeanFactory getContext() {
		try {
			lock.lock();
			if (context != null)
				return context;
			System.setProperty("spring.profiles.active", "test");
			context = new FileSystemXmlApplicationContext(new String[] {
					"E:/project/lbs_service/lbs_api/trunk/lbs_api/src/main/webapp/WEB-INF/spring/app.xml"
				 });
			return context;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return context;
	}
}
