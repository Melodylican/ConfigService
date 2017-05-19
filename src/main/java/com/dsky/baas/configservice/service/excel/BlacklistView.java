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

import com.dsky.baas.configservice.model.BlackListBean;
import com.dsky.baas.configservice.model.OrderBean;

public class BlacklistView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("黑名单");
		
		setExcelHeader(excelSheet);
		HSSFCellStyle setBorder = workbook.createCellStyle();//设置样式
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		
		List<BlackListBean> blacklist = (List<BlackListBean>) model.get("blacklist");
		if(blacklist != null) {
			logger.info("payedOrderList "+blacklist.size());
			setExcelRows(excelSheet,blacklist);
			setColumnWidth(excelSheet);
		} else {
			logger.info("blacklist 为空");
		}
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("用户ID");
		excelHeader.createCell(1).setCellValue("游戏ID");
		excelHeader.createCell(2).setCellValue("活动ID");
		excelHeader.createCell(3).setCellValue("过期时间");
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<BlackListBean> blacklist){
		int record = 1;
		//double score=1;
		for (BlackListBean blb : blacklist) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(blb.getPlayerId());
			excelRow.createCell(1).setCellValue(blb.getGameId());
			excelRow.createCell(2).setCellValue(blb.getId());
			if(blb.getExpireTime() != 0)
				excelRow.createCell(3).setCellValue(blb.getExpireTime(1));
			else
				excelRow.createCell(3).setCellValue("未设置过期时间");

		}
	}
	
	public void setColumnWidth(HSSFSheet excelSheet) {
		excelSheet.setColumnWidth(0, 4766);
		excelSheet.setColumnWidth(1, 4766);
		excelSheet.setColumnWidth(2, 4766);
		excelSheet.setColumnWidth(3, 4766);

	}

}
