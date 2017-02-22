package com.idreamsky.promoter.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dsky.baas.configservice.model.OrderBean;
import com.dsky.baas.configservice.service.excel.ImportExcelUtil;

public class ExcelTest {
/*	
	public static void main(String[] args) throws Exception {
		
		InputStream in =null;  
        List<List<Object>> listob = null;  
        File file = new File("C:\\Users\\chris.li\\Downloads\\export1.xls");  
        System.out.println("获取文件完毕！  "+file.getName());
        if(file==null){  
        	System.out.println("抛出异常！");
            throw new Exception("文件不存在！");  
        }  
          
        in = new FileInputStream(file);  
        listob = new ImportExcelUtil().getBankListByExcel(in,file.getName()); 
        System.out.println("listob size "+listob.size());
        
        String[] orderIds = new String[listob.size()];  
        //该处可调用service相应方法进行数据保存到数据库中，  
        for (int i = 0; i < listob.size(); i++) {  
        	System.out.println("将数据组合成数组形式！");
            List<Object> lo = listob.get(i);  
            OrderBean ob = new OrderBean();  
            ob.setGameName(String.valueOf(lo.get(0)));
            ob.setUserMemo(String.valueOf(lo.get(1)));
            ob.setOrderId(String.valueOf(lo.get(2)));
            orderIds[i]=ob.getOrderId();
            ob.setPayInfo(String.valueOf(lo.get(3)));
            ob.setRequestExchangePoints(Integer.getInteger(String.valueOf(lo.get(4))));
            ob.setStatus(String.valueOf(lo.get(5)));
            ob.setAmount(Integer.parseInt(String.valueOf(lo.get(7))));
            System.out.println("打印信息-->游戏名:"+ob.getGameName()+"  订单编号："+ob.getOrderId()+"   支付宝账号："+ob.getPayInfo()+"   金额："+ob.getAmount()+"  "+String.valueOf(lo.get(6))+" "+String.valueOf(lo.get(7)));  
        }
	}
*/
}
