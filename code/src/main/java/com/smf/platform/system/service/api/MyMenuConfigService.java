package com.smf.platform.system.service.api;

import java.util.List;

import com.smf.platform.service.api.SmfBaseService;
import com.smf.platform.system.domain.MyMenuConfig;
import com.smf.platform.system.domain.SysResource;

public interface MyMenuConfigService extends SmfBaseService<MyMenuConfig> {
	
	public void  save(MyMenuConfig myMenuConfig);
	
	/** 保存或者更新自定义菜单
	 * @param myMenuConfig
	 */
	public void  saveOrUpdate(MyMenuConfig myMenuConfig);
	
	/** 获取myMenuConfig
	 * @param usercode
	 * @return
	 */
	public MyMenuConfig getMyMenuConfig(String usercode);
	
	/** 获取用户自定义快捷菜单
	 * @return
	 */
	public List<SysResource> getUserDefinedSysResource();
	
	/** 获取系统默认的快捷菜单设置
	 * @return
	 */
	public String getDefalutShortCastEntranceSet();
}
