package com.smf.platform.framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SystemConfig {
	
	public static final String SYSTEMID = "SystemId";
	// 系统页面布局方式
	public static final String LAYOUT = "Layout";
	// 系统页面布局方式的进入页面
	public static final String LAYOUTHOME = "LayoutHome";
	// 系统名称
	public static final String TITLE = "SystemTitle";
	// 首页的路径
	public static final String HOMEURL = "HomeUrl";
	// 公司名称
	public static final String COMPANYNAME = "CompanyName";
	// 登录页面的标题图片路径
	public static final String LOGINIMAGE = "LoginImage";
	// 登录页面的背景图片路径
	public static final String LOGINIMAGEBG = "LoginImageBg";
	// 登录后首页的标题图片路径
	public static final String TITLEIMAGE = "TitleImage";

	// 系统ID
	private static String systemId;
	
	private static Properties props = null;

	static {
		props = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		try {
			props.load(new FileInputStream(path + "/systemconfig.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		systemId = props.getProperty(SYSTEMID);
	}

	/**
	 * 获取系统Id
	 * @return 系统Id
	 */
	public static String getSystemId() {
		return systemId;
	}
	
	public static String getValue(String propName) {
		if (propName.equals(TITLEIMAGE)) {
			return props.getProperty(propName + props.getProperty(LAYOUT));
		} else if (propName.equals(LAYOUTHOME)) {
			return props.getProperty(propName + props.getProperty(LAYOUT));
		} else {
			return props.getProperty(propName);
		}
	}
}
