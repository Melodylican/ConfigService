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

import com.dsky.baas.configservice.model.GameRedeemCodeBean;
import com.dsky.baas.configservice.model.OrderBean;

public class RedeemCodeListView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("已使用兑换码");
		
		setExcelHeader(excelSheet);
		HSSFCellStyle setBorder = workbook.createCellStyle();//设置样式
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		
		List<GameRedeemCodeBean> grcb = (List<GameRedeemCodeBean>) model.get("list");
		if(grcb != null) {
	 		logger.info("grcb "+grcb.size());
			setExcelRows(excelSheet,grcb);
			setColumnWidth(excelSheet);
		}
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("游戏ID");
		excelHeader.createCell(1).setCellValue("兑换码");
		excelHeader.createCell(2).setCellValue("兑换积分");
		excelHeader.createCell(3).setCellValue("创建时间");
		excelHeader.createCell(4).setCellValue("使用时间");
		excelHeader.createCell(5).setCellValue("使用者PlayerID");
		excelHeader.createCell(6).setCellValue("状态");
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<GameRedeemCodeBean> grcb){
		int record = 1;
		for (GameRedeemCodeBean redeem : grcb) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(redeem.getGameId());
			excelRow.createCell(1).setCellValue(redeem.getCode());
			excelRow.createCell(2).setCellValue(redeem.getScore());
			excelRow.createCell(3).setCellValue(redeem.getCreateTime(1));
			excelRow.createCell(4).setCellValue(redeem.getUpdateTime(1));
			excelRow.createCell(5).setCellValue(redeem.getPlayerId());
			excelRow.createCell(6).setCellValue("已使用");//兑换积分方案
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
	}

}
