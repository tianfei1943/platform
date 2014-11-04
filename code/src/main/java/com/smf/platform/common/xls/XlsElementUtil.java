package com.smf.platform.common.xls;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author gu
 *
 */
public class XlsElementUtil {
	
	synchronized static public XlsElement genXlsElementTreeData(HSSFCell cell,XlsElement xlsElementParent){
		String cellValue = cell.getStringCellValue().trim();
		XlsElement xlsElement = new XlsElement();
		xlsElement.setParent(xlsElementParent);
		cellValue = cellValue.substring(1);
		String[] str = cellValue.split(XlsConst.SPLIT_SYMBOL);
		if(str.length==1){
			xlsElement.setProperty(str[0]);//设置Property
		}else if(str.length==2){
			xlsElement.setProperty(str[0]);//设置Property
			xlsElement.setClazzStr(str[1]);//设置Property的类型（类的全路径）
		}else if(str.length==3){
			xlsElement.setProperty(str[0]);//设置Property
			xlsElement.setClazzStr(str[1]);//设置Property的类型（类的全路径）
			xlsElement.setFormateStyle(str[2]);//设置格式化的格式
		}else{
			xlsElement.setProperty(str[0]);//设置Property
			xlsElement.setClazzStr(str[1]);//设置Property的类型（类的全路径）
			xlsElement.setFormateStyle(str[2]);//设置格式化的格式
			xlsElement.setColor(str[3]);//设置颜色
		}
		if(!xlsElementParent.hasChild())
			xlsElementParent.setChild(xlsElement);
		else
			XlsElementUtil.genXlsElementTreeData(cell, xlsElementParent.getChild());
		return xlsElementParent;
	}

	synchronized static public String getNewSplitString(String[] strs,String splirStr){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i]);
			if(i!=strs.length-1)
				sb.append(splirStr);
		}
		return sb.toString();
	}
}
