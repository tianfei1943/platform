package com.smf.platform.system.compare;

import java.util.Comparator;

import com.smf.platform.system.domain.SysGroup;

public class SmfSysGroupCompare implements Comparator<SysGroup> {

	public int compare(SysGroup o1, SysGroup o2) {
		SysGroup sg1 = (SysGroup)o1;
		SysGroup sg2 = (SysGroup)o2;
		return sg1.getId().compareTo(sg2.getId());
	}

}
