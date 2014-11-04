package com.smf.platform.system.domain;

import java.io.Serializable;

public class RootMenus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5665340520431417439L;
	
	private Long Id;
	
	private String resourceName;
	
	private String resourceUrl;

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

}
