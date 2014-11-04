package com.smf.platform.common.xls;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.Region;

public class XlsUtil {
	/**
	 * ���õ�Ԫ��设置Long型数值ֵ
	 * @param cell
	 * @param value
	 */
	synchronized static public void setValue(HSSFCell cell, Long value){
		if (value!= null) {
			//cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
			cell.setCellValue(value.doubleValue());
		}
	}
	
	/**
	 * ���õ�Ԫ��设置Float型数值 ���õ�Ԫ���ֵ
	 * @param cell
	 * @param value
	 */
	synchronized static public void setValue(HSSFCell cell, Float value){
		if (value != null) {
			//cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
			cell.setCellValue(value.floatValue());
		}
	}
	
	/**
	 * ���õ�Ԫ��设置Double型数值 ���õ�Ԫ���ֵ
	 * @param cell
	 * @param value
	 */
	synchronized static public void setValue(HSSFCell cell, Double value){
		if (value != null) {
			//cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
			cell.setCellValue(value.floatValue());
		}
	}
	
	/**
	 * ���õ�Ԫ��设置Integer型数值 ���õ�Ԫ���ֵ
	 * @param cell
	 * @param value
	 */
	synchronized static public void setValue(HSSFCell cell, Integer value){
		if (value != null) {
			//cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
			cell.setCellValue(value.intValue());
		}
	}
	
	/**
	 * ���õ�Ԫ��设置String型数值 ���õ�Ԫ���ֵ
	 * @param cell
	 * @param value
	 */
	synchronized static public void setValue(HSSFCell cell, String value){
		//cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
		cell.setCellValue(value);
	}
	
	/**
	 * ���õ�Ԫ��设置Object型数值 ���õ�Ԫ���ֵ
	 * @param cell
	 * @param value
	 */
	synchronized static public void setValue(HSSFCell cell, Object value){
		//cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
		if(value!=null){
			String className = value.getClass().getName();
			if(className.equals("java.lang.Double")){
				XlsUtil.setValue(cell, (Double)value);
			}else if(className.equals("java.lang.Integer")){
				XlsUtil.setValue(cell, (Integer)value);
			}else if(className.equals("java.lang.Long")){
				XlsUtil.setValue(cell, (Long)value);
			}else if(className.equals("java.lang.String")){
				XlsUtil.setValue(cell, (String)value);
			}else{
				XlsUtil.setValue(cell, value.toString());
			}
		}
	}
	
	/**
	 * 合并单元格
	 * @param sheet
	 * @param rowFrom
	 * @param rowTo
	 * @param cellFrom
	 * @param cellTo
	 */
	synchronized static public Region mergeRegion(HSSFSheet sheet,short rowFrom,short rowTo,short cellFrom,short cellTo) {
		Region region = new Region(rowFrom,cellFrom,rowTo,cellTo);
		sheet.addMergedRegion(region);
		return region;
	}
	
	/**
	 * 给合并后的单元格加样式，比如加边框
	 * @param sheet
	 * @param region
	 * @param cs
	 */	
	synchronized static public void setRegionStyle(HSSFSheet sheet,Region region, HSSFCellStyle cs) {
		for (int i = region.getRowFrom(); i <= region.getRowTo(); i++) {
			HSSFRow row = HSSFCellUtil.getRow(i, sheet);
			for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
				HSSFCell cell = HSSFCellUtil.getCell(row, (short) j);
				cell.setCellStyle(cs);
			}
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param cellnum
	 * @return
	 */
	synchronized static public HSSFCell getCell(HSSFRow row,short cellnum){
		HSSFCell cell = row.getCell(cellnum);
		if(cell==null || cell.getCellType()==HSSFCell.CELL_TYPE_BLANK){
			cell = row.createCell(cellnum);
		}
		return cell;
	}
	
	/**
	 * 
	 * @param sheet
	 * @param rownum
	 * @return
	 */
	synchronized static public HSSFRow getRow(HSSFSheet sheet, int rownum) {
		HSSFRow row = sheet.getRow(rownum);
		if (row == null) {
			row = sheet.createRow(rownum);
		}
		return row;
	}

	/**
	 * 
	 * @param workbook
	 * @param sheetName
	 * @return
	 */
	synchronized static public HSSFSheet getSheet(HSSFWorkbook workbook, String sheetName) {
		if (workbook == null)
			workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		if (sheetName == null || sheetName.trim().equals("")) {
			sheetName = "Sheet1";
		}
		workbook.setSheetName(0, sheetName);
		return sheet;
	}
	
	/**
	 * ���õ�Ԫ���ֵ
	 * 
	 * @param cell
	 * @param int
	 *            rowstart 行初始化值
	 * @param int
	 *            rowsize 行的总数
	 * @param int
	 *            cellstart 列初始化值
	 * @param int
	 *            cellsize 列的总数
	 * @param int
	 *            worksheet worksheet对象
	 */
	synchronized static public HSSFSheet setInitValue(int rowstart,int rowsize, int cellstart,int cellsize,HSSFSheet worksheet){
		for(int i=0;i<rowsize;i++){
				int rowNum = rowstart + i;
				//初始化行
				if(worksheet.getRow(rowNum)==null){
					worksheet.createRow(rowNum);
				}
				//初始化列
				HSSFRow row = worksheet.getRow(rowNum);
				
				for(int j=cellstart;j<cellsize;j++){
					//给表格增加对象
					if(row.getCell((short)j)==null)
					{
						row.createCell((short)j); 
					}
				}
		}
		return worksheet;
	}
	
	public static HSSFSheet setInitValueByStartRow(int rowstart,int rowsize,int maxRowConfig, int cellstart,int cellsize,HSSFSheet worksheet){
		HSSFRow templateRow = worksheet.getRow(rowstart);
		if(templateRow==null){
			return XlsUtil.setInitValue(rowstart, maxRowConfig, cellstart, cellsize, worksheet);
		}else{
			int line = 0;
			if(maxRowConfig<=rowsize){
				line = rowsize;
			}else{
				line = maxRowConfig;
			}
			for(int i=1;i<=line;i++){
				//初始化行
				if(worksheet.getRow(i)==null){
					worksheet.createRow(i);
				}
				//初始化列
				HSSFRow row = worksheet.getRow(i);
				for(int j=cellstart;j<cellsize;j++){
					HSSFCell _cell = templateRow.getCell((short)j);
					HSSFCell cell = row.getCell((short)j);
					//给表格增加对象
					if(cell==null){
						row.createCell((short)j);
						cell= row.getCell((short)j);
					}else{
//						continue;
						XlsUtil.setValue(cell, "");
					}
					if(_cell!=null && i<=rowsize)
						cell.setCellStyle(_cell.getCellStyle());
				}
			}
			return worksheet;
		}
	}
}


