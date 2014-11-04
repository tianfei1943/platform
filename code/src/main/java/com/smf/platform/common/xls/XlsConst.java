/**
 * 
 */
package com.smf.platform.common.xls;

/**
 * @author gu
 *
 */
public class XlsConst {
	/**
	 * 模板下载路径目录
	 */
	public final static String REPORT_DIR = "/WEB-INF/report/";
	
	 public static class DataType {
		    public static final String ARRAY_TYPE   = "array";
		    public static final String DOMAIN_TYPE   = "domain";
		    public static final String MAP_TYPE   = "map";
	 }
	 
	 public static class XlsTemplateSymbol{
		 public static final String TERMINAL_SYMBOL  = "#END";
		 public static final String BASICTYPE_BEGIN = "~";
		 public static final String PROPERTY_BEGIN = "$";
	 }
	 
	 public static final String SPLIT_SYMBOL = "=";
	 
	 public static final String SPLIT_SYMBOL_DOT = "\\.";
	 
	 public static final String SPLIT_SYMBOL_STATIC_TITLE = "staticTitle";
	 
	 public static final String SPLIT_SYMBOL_TITLE_LOOP = "*";
}
