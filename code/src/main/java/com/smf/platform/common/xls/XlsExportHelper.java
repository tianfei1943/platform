/**
 * 
 */
package com.smf.platform.common.xls;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author gu
 *
 */
public class XlsExportHelper {

	/**
	 * 读取模板
	 * @param request
	 * @param response
	 * @param filePath
	 * @param sheetName
	 * @param reportXlsFileName
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	synchronized static public HSSFWorkbook loadXlsTemplate(HttpServletRequest request
			,HttpServletResponse response,String templateXls) throws IOException{
		String filePath = request.getRealPath(XlsConst.REPORT_DIR) + "\\" + templateXls;
		FileInputStream fis = new FileInputStream(filePath);//加载模板
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		return workbook;
	}
	

	/**
	 * 导出xls
	 * @param request
	 * @param response
	 * @param sheetName
	 * @param reportXlsFileName
	 * @param workbook
	 * @throws IOException
	 */
	synchronized static public void exportXls(HttpServletRequest request,HttpServletResponse response
			,String reportXlsFileName,HSSFWorkbook workbook) throws IOException{
		
		String xlsName = reportXlsFileName + ".xls";//导出的xls名称
		OutputStream ops = response.getOutputStream();
		String attachment = "attachment;  filename=" + xlsName;
		/* 设置response的contentType，用于下载 */
		response.setContentType("application/x-msdownload;charset=GB2312");
		/* 设置HTTP相应头，指向要下载的文件 */
		response.setHeader("Content-Disposition", new String(attachment
				.getBytes(), "ISO8859-1"));
		workbook.write(ops);
		ops.close();
	}
	
	/**
	 * 读取模板配置的参数(注意Domain和Array类型实现不一样，稍后修改为一致的版本)
	 * @param worksheet
	 * @param dataType @see com.dongyun.platform.component.xls.XlsConst.DataType
	 * @param startRow
	 * @param startColumn
	 * @param endColumn
	 * @return
	 */
	synchronized static public Map<Integer, Object> readTempleConfig(HSSFSheet worksheet,String dataType
			,short startRow,short startColumn,short endColumn,String domainCalssStr){
		Map<Integer,Object> map= new HashMap<Integer,Object>();
		if(dataType.equals(XlsConst.DataType.DOMAIN_TYPE)){
			for(short i=startColumn;i<=endColumn;i++){
				XlsElement xlsElementParent = new XlsElement();
				xlsElementParent.setClazzStr(domainCalssStr);
				xlsElementParent.setParent(null);
				
				{//判断是否需要读取
					HSSFRow row = worksheet.getRow(startRow);
					HSSFCell cell = row.getCell(i);
					if(cell==null || cell.getCellType()==HSSFCell.CELL_TYPE_BLANK 
							|| cell.getStringCellValue().trim().equals(XlsConst.XlsTemplateSymbol.TERMINAL_SYMBOL)){
						map.put(new Integer(i),null);
						continue;
					}
				}
				
				short j = startRow;
				//循环执行直到遇到定义的终结符结束
				while (true) {
					HSSFRow row = worksheet.getRow(j);
					HSSFCell cell = row.getCell(i);
					if(row!=null && cell!=null && cell.getCellType()!=HSSFCell.CELL_TYPE_BLANK){
						String cellValue = cell.getStringCellValue().trim();
						//如果遇到定义的终结符就continue掉，继续读取下一列
						if(cellValue.equals(XlsConst.XlsTemplateSymbol.TERMINAL_SYMBOL)){
							break;
						}	
						else 
						//如果读取到的是定义的Property开始标示
						if(cellValue.startsWith(XlsConst.XlsTemplateSymbol.PROPERTY_BEGIN)){
							xlsElementParent = XlsElementUtil.genXlsElementTreeData(cell, xlsElementParent);//递归设值
						}
					}
					j++;
				}
				//放到map里面去
				map.put(new Integer(i), xlsElementParent);
			}
		}else if(dataType.equals(XlsConst.DataType.ARRAY_TYPE) || dataType.equals(XlsConst.DataType.MAP_TYPE)){//类型是数组或者Map
			for(short i=startColumn;i<=endColumn;i++){
				XlsElement xlsElement = new XlsElement();
				{//判断是否需要读取
					HSSFRow row = worksheet.getRow(startRow);
					HSSFCell cell = row.getCell(i);
					if(cell==null || cell.getCellType()==HSSFCell.CELL_TYPE_BLANK 
							|| cell.getStringCellValue().trim().equals(XlsConst.XlsTemplateSymbol.TERMINAL_SYMBOL)){
						map.put(new Integer(i),null);
						continue;
					}
				}
				short j = startRow;
				//循环执行直到遇到定义的终结符结束
				while (true) {
					HSSFRow row = worksheet.getRow(j);
					HSSFCell cell = row.getCell(i);
					if(row!=null && cell!=null && cell.getCellType()!=HSSFCell.CELL_TYPE_BLANK){
						String cellValue = cell.getStringCellValue().trim();
						//如果遇到定义的终结符就continue掉，继续读取下一列
						if(cellValue.equals(XlsConst.XlsTemplateSymbol.TERMINAL_SYMBOL)){
							break;
						}	
						else 
						//如果读取到的是定义的Property开始标示
						if(cellValue.startsWith(XlsConst.XlsTemplateSymbol.PROPERTY_BEGIN)){
							cellValue = cellValue.substring(1);
							String[] strs = cellValue.split(XlsConst.SPLIT_SYMBOL);
							if(strs.length==1){
								xlsElement.setProperty(strs[0]);
							}else if(strs.length==2){
								xlsElement.setProperty(strs[0]);//设置Property
								xlsElement.setClazzStr(strs[1]);//设置Property的类型（类的全路径）
							}else if(strs.length==3){
								xlsElement.setProperty(strs[0]);//设置Property
								xlsElement.setClazzStr(strs[1]);//设置Property的类型（类的全路径）
								xlsElement.setFormateStyle(strs[2]);//设置格式化的格式
							}else{
								xlsElement.setProperty(strs[0]);//设置Property
								xlsElement.setClazzStr(strs[1]);//设置Property的类型（类的全路径）
								xlsElement.setFormateStyle(strs[2]);//设置格式化的格式
								xlsElement.setColor(strs[3]);//设置颜色
							}
						}
					}
					j++;
				}
				//放到map里面去
				map.put(new Integer(i), xlsElement);
			}
		}
		return map;
	}
}
