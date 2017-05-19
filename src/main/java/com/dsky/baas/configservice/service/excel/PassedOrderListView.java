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

public class PassedOrderListView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("待支付订单");
		
		setExcelHeader(excelSheet);
		HSSFCellStyle setBorder = workbook.createCellStyle();//设置样式
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		
		List<OrderBean> orderList = (List<OrderBean>) model.get("orderList");
		if(orderList != null) {
			logger.info("orderList "+orderList.size());
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
		excelHeader.createCell(5).setCellValue("审核状态");
		excelHeader.createCell(6).setCellValue("积分兑换方案");
		excelHeader.createCell(7).setCellValue("兑换金额");
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
			excelRow.createCell(5).setCellValue("通过");
			excelRow.createCell(6).setCellValue(score+" 积分兑换  1 元");//兑换积分方案
			excelRow.createCell(7).setCellValue(order.getAmount());
		}
	}
	
	public void setColumnWidth(HSSFSheet excelSheet) {
		excelSheet.setColumnWidth(0, 3766);
		excelSheet.setColumnWidth(1, 3766);
		excelSheet.setColumnWidth(2, 3766);
		excelSheet.setColumnWidth(3, 3766);
		excelSheet.setColumnWidth(4, 3766);
		excelSheet.setColumnWidth(5, 3766);
		excelSheet.setColumnWidth(6, 3766);
		excelSheet.setColumnWidth(7, 3766);
	}

}
