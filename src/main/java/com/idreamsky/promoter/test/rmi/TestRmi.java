package com.idreamsky.promoter.test.rmi;


import java.rmi.RMISecurityManager;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dsky.baas.configservice.model.ExchApiResultBean;
import com.dsky.baas.configservice.service.IPointsLogService;

public class TestRmi {
	public static void main(String[] arg) {
		System.out.println("rmi客户端开始调用");
        @SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("file:D:\\applicationContext1.xml");
        
        
        /*
        IGameExchConfig iec = (IGameExchConfig) ctx.getBean("testRmiService");
        ExchApiResultBean erb = iec.getExchangeByGameId(10909+"");
        if(erb == null)
        	System.out.println("返回结果为空");
        System.out.println(erb.getCode()+"==="+erb.getGameName()+"==="+erb.getExchBeginTime());
        */
        ///*
        //SecurityManager sm = new SecurityManager();
        //System.setSecurityManager(sm);

        //System.setSecurityManager(new RMISecurityManager());
        IPointsLogService rmi=(IPointsLogService)ctx.getBean("testRmiService");
        //rmi.getPonitsLogCount(10909, 111);
        int i=rmi.getPonitsLogCount(1111, 10909, false, 0, true,(int)(System.currentTimeMillis()/1000));
        System.out.println(i);
        System.out.println((System.currentTimeMillis()/1000));
        /*
        rmi.test();
        ApiResultBean result1 = rmi.getOption("123", "中国大陆");
        ApiResultBean result2 = rmi.getOptionById("25");

        System.out.println(result1.getDescription()+"======="+result1.getH5Url());
        System.out.println(result2.getDeviceCount()+"======="+result1.getH5Url());
        List<PointsLog> list = (List<PointsLog>)rmi.getPonitsLog(63, 111,1,15);
        */
        System.out.println("rmi客户端调用结束");
	}

}
