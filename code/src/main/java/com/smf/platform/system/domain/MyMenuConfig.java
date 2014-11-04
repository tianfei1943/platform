package com.smf.platform.system.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "smf_t_sys_mymenuconfig")
public class MyMenuConfig implements Comparable<Object>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7169259755676042114L;

	private Long id;
	
	private String systemId;
	
	private String usercode;
	
	private String resourceIds;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String toString(){
		return this.getId().toString();
	}

	@Override
	public int compareTo(Object arg0) {
		if(arg0 instanceof MyMenuConfig){
			return this.getId().compareTo(((MyMenuConfig)arg0).getId());
		}
		return 0;
	}
}
