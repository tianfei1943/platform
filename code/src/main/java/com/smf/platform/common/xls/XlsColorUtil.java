/**
 * 
 */
package com.smf.platform.common.xls;

import org.apache.poi.hssf.util.HSSFColor;

/**
 * @author gu
 *
 */
public class XlsColorUtil {
	static public short getCorrespondingColorIndek(String color){
		if(color.equalsIgnoreCase("black")){
			return HSSFColor.BLACK.index;
		}else if(color.equalsIgnoreCase("red")){
			return HSSFColor.RED.index;
		}else if(color.equalsIgnoreCase("black")){
			return HSSFColor.BLUE.index;
		}
		return 0;
	}
}
