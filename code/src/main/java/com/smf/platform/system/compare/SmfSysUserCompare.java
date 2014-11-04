package com.smf.platform.system.compare;

import java.util.Comparator;

import com.smf.platform.system.domain.SysUser;

public class SmfSysUserCompare implements Comparator<SysUser>{
	public int compare(SysUser o1, SysUser o2) {
		SysUser sg1 = (SysUser)o1;
		SysUser sg2 = (SysUser)o2;
		return sg1.getId().compareTo(sg2.getId());
	}
}
