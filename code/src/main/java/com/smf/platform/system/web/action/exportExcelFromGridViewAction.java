package com.smf.platform.system.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.smf.platform.common.xls.XlsExportHelper;
import com.smf.platform.common.xls.XlsUtil;

/**
 * 从界面获取数据导出Action
 * @date 2012-07-24
 * @author qm_chen_qq
 * 
 */

@SuppressWarnings("serial")
@ParentPackage("smfjson-default")
public class exportExcelFromGridViewAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//excel导出参数(2012-07-20)
	private String rowStr;//行数据
	private String title;//表头
	private String columHeadStr;//列头
	private String unit;//单位信息
	private String columWidth="250";//列宽
	
	/**
	 * 导出XLS
	 * 
	 * @return
	 * @author qm_chen_qq
	 * @since 2012-07-20
	 */
	@Action(value = "xlsExport")
	public String xlsExport() {
		// 导出xls
		try {
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
			XlsExportHelper.exportXls(request, response, title, returnHSSFWorkbook(columHeadStr,rowStr,title,unit));
		} catch (IOException e) {
			Logger log = Logger.getLogger(this.getClass());
			log.error("导出数据发生异常", e);
		}
		return null;
	}
	/**
	 * 2012-07-20
	 * @param columHeadStr
	 * @param rowStr
	 * @param title
	 * @return excel对象
	 */
	private HSSFWorkbook returnHSSFWorkbook(String columHeadStr,String rowStr,String title,String unit){
		if(columHeadStr==null){
			columHeadStr="";
		}
		if(rowStr==null){
			rowStr="";
		}
		//表头数据
		String[] columNames=columHeadStr.split("&");
		//行数据
		String[] rows=rowStr.split("&");
		if(columWidth==null||columWidth.equals("")){
			columWidth="250";
		}else{
			if(Integer.parseInt(columWidth)<100){
				columWidth="250";
			}else if(Integer.parseInt(columWidth)>900){
				columWidth="500";
			}
		}
		//xls对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = XlsUtil.getSheet(workbook, null);
		//设置列宽
		for (int i = 0; i < columNames.length; i++) {
			sheet.setColumnWidth((short)i, (short)(columNames.length*((short)Integer.parseInt(columWidth))));
		}
		//设置字体（表头、列头）
		HSSFFont font = workbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 12);
		//设置字体（单元格）
		HSSFFont font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 10);
		int colHeaderSize=columNames.length;
		//创建表头
		short rowmum1 = 0;
		{
			HSSFRow row = XlsUtil.getRow(sheet, rowmum1);
			row.setHeightInPoints(30);
			HSSFCell cellHand = XlsUtil.getCell(row, (short) 0);
			XlsUtil.setValue(cellHand, title);
			Region region = XlsUtil.mergeRegion(sheet, rowmum1, rowmum1, (short) 0, (short) (colHeaderSize-2));
			HSSFCellStyle cellStyle1 = this.getTopCellStyle(workbook);
			cellStyle1.setFont(font);
			XlsUtil.setRegionStyle(sheet, region, cellStyle1);
			rowmum1++;
			{// 设置样式
				// row.setHeight((short)320);
			}
		}
		//判断是否有单位
		if(unit!=null&&!unit.equals("")){
			{
				HSSFRow row = XlsUtil.getRow(sheet, rowmum1);
				row.setHeightInPoints(30);
				HSSFCell cellHand = XlsUtil.getCell(row, (short) 0);
				XlsUtil.setValue(cellHand, unit);
				Region region = XlsUtil.mergeRegion(sheet, rowmum1, rowmum1, (short) 0, (short) (colHeaderSize-2));
				HSSFCellStyle cellStyle1 = this.getDefaultCellStyle(workbook);
				XlsUtil.setRegionStyle(sheet, region, cellStyle1);
				rowmum1++;
			}
		}
		//创建列头
		{
			HSSFRow row1 = XlsUtil.getRow(sheet, rowmum1);
			row1.setHeightInPoints(25);
			short column=0;
			for (int i = 1; i < columNames.length; i++) {
				HSSFCell cellPlant = XlsUtil.getCell(row1, column);
				XlsUtil.setValue(cellPlant, columNames[i]);
				HSSFCellStyle cellStyle = this.getDefaultCellStyle(workbook);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
				//cellStyle.setFillBackgroundColor((short)11);
				cellStyle.setFont(font);
				cellPlant.setCellStyle(cellStyle);
				column++;
			}
			rowmum1++;
		}
		//读取行数据
		{
			//循环行数据读取单元格数据
			for (int i = 0; i < rows.length; i++) {
				HSSFRow row1 = XlsUtil.getRow(sheet, rowmum1);
				HSSFCell cell = XlsUtil.getCell(row1, (short)0);
				//cell.setCellValue(i+1);//Id
				//读取单元格数据
				String[] cells=rows[i].split("@");
				short column=0;
				for (int j = 1; j < cells.length; j++) {
					HSSFCell cellPlant = XlsUtil.getCell(row1, column);
					XlsUtil.setValue(cellPlant, cells[j].equals("emptyStr")?"":cells[j]);
					HSSFCellStyle cellStyle = this.getDefaultCellStyle(workbook);
					cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					cellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
					cellStyle.setFont(font1);
					cellPlant.setCellStyle(cellStyle);
					column++;
				}
				rowmum1++;
			}
			
		}
		return workbook;
	}
	/**
	 * 2012-07-20
	 * @param workbook
	 * @return
	 */
	private HSSFCellStyle getDefaultCellStyle(HSSFWorkbook workbook) {
		return this.getCellStyle(workbook, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.VERTICAL_CENTER, HSSFCellStyle.ALIGN_LEFT, true,HSSFColor.GREY_25_PERCENT.index);
	}
	/**
	 * 2012-07-20
	 * @param workbook
	 * @return
	 */
	private HSSFCellStyle getTopCellStyle(HSSFWorkbook workbook) {
		return this.getCellStyle(workbook, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.VERTICAL_CENTER, HSSFCellStyle.ALIGN_CENTER, true,HSSFColor.GREY_25_PERCENT.index);
	}
	/**
	 * 2012-07-20
	 * @param workbook
	 * @return
	 */
	private HSSFCellStyle getDateCellStyle(HSSFWorkbook workbook) {
		return this.getCellStyle(workbook, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.VERTICAL_CENTER, HSSFCellStyle.ALIGN_RIGHT, true,HSSFColor.GREY_25_PERCENT.index);
	}
	/**
	 * 2012-07-20
	 * @param workbook
	 * @return
	 */
	private HSSFCellStyle getCellStyle(HSSFWorkbook workbook, short borderBottomStyle, short borderLeftStyle, short borderRightStyle, short borderTopStyle,
			short verticalAlignment, short horAlignment, boolean isWrap,short borderColor) {
		HSSFCellStyle cellStyle1 = workbook.createCellStyle();
		cellStyle1.setVerticalAlignment(verticalAlignment);
		cellStyle1.setBorderBottom(borderBottomStyle);
		cellStyle1.setBorderLeft(borderLeftStyle);
		cellStyle1.setBorderRight(borderRightStyle);
		cellStyle1.setBorderTop(borderTopStyle);
		cellStyle1.setTopBorderColor(borderColor);
		cellStyle1.setLeftBorderColor(borderColor);
		cellStyle1.setRightBorderColor(borderColor);
		cellStyle1.setBottomBorderColor(borderColor);
		cellStyle1.setAlignment(horAlignment);
		cellStyle1.setWrapText(isWrap);
		return cellStyle1;
	}
		
	public String getRowStr() {
		return rowStr;
	}

	public void setRowStr(String rowStr) {
		this.rowStr = rowStr;
	}

	public String getColumHeadStr() {
		return columHeadStr;
	}

	public void setColumHeadStr(String columHeadStr) {
		this.columHeadStr = columHeadStr;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getColumWidth() {
		return columWidth;
	}
	
	public void setColumWidth(String columWidth) {
		this.columWidth = columWidth;
	}
}
