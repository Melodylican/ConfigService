package com.dsky.baas.configservice.service.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.dsky.baas.configservice.model.OrderBean;

public class PayedOrderListView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<OrderBean> orderList = (List<OrderBean>) model.get("payedOrderList");
		
		HSSFSheet excelSheet = null;
		if(orderList != null) {
			logger.info("payedOrderList "+orderList.size());
			if(orderList.get(0).getStatus().equals("REJECTED"))
				excelSheet = workbook.createSheet("未通过审核订单");
			else
				excelSheet = workbook.createSheet("已支付订单");
			
			setExcelHeader(excelSheet);
			HSSFCellStyle setBorder = workbook.createCellStyle();//设置样式
			setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
			
	
			setExcelRows(excelSheet,orderList);
			setColumnWidth(excelSheet);
		}
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("游戏名称");
		excelHeader.createCell(1).setCellValue("乐逗账号");
		excelHeader.createCell(2).setCellValue("申请单编号");
		excelHeader.createCell(3).setCellValue("支付宝账号");
		excelHeader.createCell(4).setCellValue("申请兑换积分");
		excelHeader.createCell(5).setCellValue("状态");
		excelHeader.createCell(6).setCellValue("积分兑换方案");
		excelHeader.createCell(7).setCellValue("兑换金额");
		excelHeader.createCell(8).setCellValue("备注");
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<OrderBean> orderList){
		int record = 1;
		double score=1;
		for (OrderBean order : orderList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(order.getGameName());
			excelRow.createCell(1).setCellValue(order.getUserMemo());
			excelRow.createCell(2).setCellValue(order.getOrderId());
			excelRow.createCell(3).setCellValue(order.getPayInfo());
			excelRow.createCell(4).setCellValue(order.getRequestExchangePoints());
			if( order.getAmount()!= 0 )
				score = Math.ceil(order.getRequestExchangePoints()/order.getAmount());
			excelRow.createCell(5).setCellValue(order.getStatus());
			excelRow.createCell(6).setCellValue(score+" 积分兑换  1 元");//兑换积分方案
			excelRow.createCell(7).setCellValue(order.getAmount());
			excelRow.createCell(8).setCellValue(order.getOperationMemo());
		}
	}
	
	public void setColumnWidth(HSSFSheet excelSheet) {
		excelSheet.setColumnWidth(0, 4766);
		excelSheet.setColumnWidth(1, 4766);
		excelSheet.setColumnWidth(2, 4766);
		excelSheet.setColumnWidth(3, 4766);
		excelSheet.setColumnWidth(4, 4766);
		excelSheet.setColumnWidth(5, 4766);
		excelSheet.setColumnWidth(6, 4766);
		excelSheet.setColumnWidth(7, 4766);
		excelSheet.setColumnWidth(8, 6766);
	}

}
