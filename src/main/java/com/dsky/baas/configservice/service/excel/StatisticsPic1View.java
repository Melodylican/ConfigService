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
import com.dsky.baas.configservice.model.PayInfoBean;

public class StatisticsPic1View extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("数据统计信息");
		
		setExcelHeader(excelSheet);
		HSSFCellStyle setBorder = workbook.createCellStyle();//设置样式
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		
		List<PayInfoBean> payInfoList = (List<PayInfoBean>) model.get("statisticspic1");
		logger.info("orderList "+payInfoList.size());
		setExcelRows(excelSheet,payInfoList);
		setColumnWidth(excelSheet);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("游戏Id");
		excelHeader.createCell(1).setCellValue("活动Id");
		excelHeader.createCell(2).setCellValue("日期");
		excelHeader.createCell(3).setCellValue("已邀请人数");
		excelHeader.createCell(4).setCellValue("已支付人数");
		excelHeader.createCell(5).setCellValue("已支付金额");
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<PayInfoBean> payInfoList){
		int record = 1;
		for (PayInfoBean pay : payInfoList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(pay.getGameId());
			excelRow.createCell(1).setCellValue(pay.getActId());
			excelRow.createCell(2).setCellValue(pay.getDate());
			excelRow.createCell(3).setCellValue(pay.getInvitedPeople());
			excelRow.createCell(4).setCellValue(pay.getPayingPeople());
			excelRow.createCell(5).setCellValue(pay.getPayingAmount());

		}
	}
	
	public void setColumnWidth(HSSFSheet excelSheet) {
		excelSheet.setColumnWidth(0, 4766);
		excelSheet.setColumnWidth(1, 4766);
		excelSheet.setColumnWidth(2, 4766);
		excelSheet.setColumnWidth(3, 4766);
		excelSheet.setColumnWidth(4, 4766);
		excelSheet.setColumnWidth(5, 4766);

	}

}
