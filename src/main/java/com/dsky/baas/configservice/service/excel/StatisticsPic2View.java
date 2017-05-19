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

import com.dsky.baas.configservice.model.ActInfoBean;
import com.dsky.baas.configservice.model.OrderBean;

public class StatisticsPic2View extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("数据统计信息");
		
		setExcelHeader(excelSheet);
		HSSFCellStyle setBorder = workbook.createCellStyle();//设置样式
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		
		List<ActInfoBean> actInfoList = (List<ActInfoBean>) model.get("statisticspic2");
		logger.info("actInfoList "+actInfoList.size());
		setExcelRows(excelSheet,actInfoList);
		setColumnWidth(excelSheet);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("游戏ID");
		excelHeader.createCell(1).setCellValue("活动ID");
		excelHeader.createCell(2).setCellValue("日期");
		excelHeader.createCell(3).setCellValue("微信浏览次数");
		excelHeader.createCell(4).setCellValue("朋友圈浏览次数");
		excelHeader.createCell(5).setCellValue("QQ浏览次数");
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<ActInfoBean> actInfoList){
		int record = 1;
		for (ActInfoBean act : actInfoList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(act.getGameId());
			excelRow.createCell(1).setCellValue(act.getActId());
			excelRow.createCell(2).setCellValue(act.getDate());
			excelRow.createCell(3).setCellValue(act.getWeiXinFriends());
			excelRow.createCell(4).setCellValue(act.getWeiXinMoments());
			excelRow.createCell(5).setCellValue(act.getQq());
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
