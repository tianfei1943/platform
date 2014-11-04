package com.smf.platform.common;

/**
 * @author robbie
 *
 */
public class SmfConst {
	
	// 权限前缀
	public static final String PREFIX_PRIV = "PRIV_";
	
	// 权限类型
	public static final String PRIV_RESOURCE_TYPE_MENU = "menu";//菜单
	public static final String PRIV_RESOURCE_TYPE_URL = "url";//url
	public static final String PRIV_RESOURCE_TYPE_PROP = "boProp";//业务对象属性
	public static final String PRIV_RESOURCE_TYPE_CLASS = "bussinessObjects"; // 业务对象
	
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_INACTIVE = "I";
	
	//删除状态
	public static final int STATUS_SHOW = 1;
	public static final int STATUS_HIDDEN = 0;
	
	// 暂不适用
	// public static final String PRIV_RESOURCE_TYPE_TAG = "tag";	
	public static final char roleStatus_on = '1';
	public static final char roleStatus_off = '0';
	
	public static final String ACTIVITI_START_EVENT = "startEvent";

}
