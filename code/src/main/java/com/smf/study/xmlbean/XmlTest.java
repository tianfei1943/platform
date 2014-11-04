package com.smf.study.xmlbean;

import org.apache.xmlbeans.impl.tool.SchemaCompiler;

public class XmlTest{
	
	/*
	 * 具体用法参照 http://youlong05.iteye.com/blog/87324
	 */
	//private static final Logger logger = LoggerFactory.getLogger(XmlTest.class);

	public static void main(String[] args){
		String[] args2= new String[]{
				"-out",
				"c:/xmlCustomer.jar",
				"C:\\projects\\platform\\src\\main\\java\\com\\smf\\study\\xmlbean\\customer.xsd",
				"-compiler",
				"C:\\java\\jdk1.6.0_30\\bin\\javac"
		};
		SchemaCompiler.main(args2);
	}
}
