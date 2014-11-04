package com.smf.platform.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.smf.platform.framework.SystemConfig;
import com.smf.platform.security.SmfSecurityContextHolder;
import com.smf.platform.service.impl.SmfBaseServiceImpl;
import com.smf.platform.system.domain.MyMenuConfig;
import com.smf.platform.system.domain.SysParameterValue;
import com.smf.platform.system.domain.SysResource;
import com.smf.platform.system.domain.SysUser;
import com.smf.platform.system.service.api.MyMenuConfigService;
import com.smf.platform.system.service.api.SysParameterValueService;
import com.smf.platform.system.service.api.SysResourceService;

@SuppressWarnings("unchecked")
@Component("myMenuConfigService")
public class MyMenuConfigServiceImpl extends SmfBaseServiceImpl<MyMenuConfig> implements MyMenuConfigService {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	@Qualifier("sysParameterValueService")
	private SysParameterValueService sysParameterValueService;
	
	@Autowired
	@Qualifier("sysResourceService")
	private SysResourceService sysResourceService;
	
	@Override
	public void save(MyMenuConfig homeurl) {
		// 保存之前需要设置系统ID
		homeurl.setSystemId(SystemConfig.getSystemId());
		this.dao.save(homeurl);
	}
	
	@Override
	public void saveOrUpdate(MyMenuConfig homeurl) {
		if(homeurl.getId() != null){
			this.dao.update(homeurl);
		}else{
			// 保存之前需要设置系统ID
			homeurl.setSystemId(SystemConfig.getSystemId());
			this.dao.save(homeurl);
		}
	}

	@Override
	public MyMenuConfig getMyMenuConfig(String usercode) {
		String sql = "from MyMenuConfig s where s.usercode = '" + usercode + "' and s.systemId='" + SystemConfig.getSystemId() + "'" ;
		List<MyMenuConfig> listMyMenuConfig = this.dao.query(sql);
		if(listMyMenuConfig != null && listMyMenuConfig.size()>0){
			return listMyMenuConfig.get(0);
		}
		return null;
	}

	@Override
	public List<SysResource> getUserDefinedSysResource() {
		SysUser loginUser = SmfSecurityContextHolder.getCurrentUser();
		MyMenuConfig myMenuConfig = this.getMyMenuConfig(loginUser.getUserCode());
		String resourceIds="";
		if(myMenuConfig != null){
			resourceIds = myMenuConfig.getResourceIds();
		}else{
			resourceIds = this.getDefalutShortCastEntranceSet();
		}
		
		String[] resourceArr = resourceIds.split(",");
		List<SysResource> listResource = new ArrayList<SysResource>();
		for(int i=0;i<resourceArr.length;i++){
			SysResource sysResource = sysResourceService.get(Long.parseLong(resourceArr[i]));
			listResource.add(sysResource);
		}
		
		return listResource;
	}
	
	@Override
	public String getDefalutShortCastEntranceSet(){
		String defaltShortCastEntranceSet = "";
		// TODO 需定义为常量
		List<SysParameterValue> listSysParameterValue =sysParameterValueService.list("defaultShortcutEntranceURL"); 
		if(null != listSysParameterValue && listSysParameterValue.size()>0){
			defaltShortCastEntranceSet = listSysParameterValue.get(0).getValue();
		}
		if(defaltShortCastEntranceSet.length() == 0){
			log.info("快捷入口默认设置为空");
		}
		return defaltShortCastEntranceSet;
	}

}
