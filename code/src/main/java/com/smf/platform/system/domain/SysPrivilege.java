package com.smf.platform.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 */
@Entity
@Table(name = "SMF_T_SYS_PRIVILEGE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysPrivilege implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7469442678591235406L;
	/**
	 * 
	 * /**
	 * 
	 */
	private Long id;
	private String systemId;
	private String privilegeCode;
	private String privilegeName;
	private String privilegeType;
	private String valueConstraint;
	private String opClass;
	private String opMethod;
	private Boolean isLog = Boolean.FALSE;
	@Transient
	private Set<SysRole> roles = new HashSet<SysRole>();
	private List<SysResource> resources = new ArrayList<SysResource>();

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the privilegeCode
	 */
	@Column(unique = true, nullable = false)
	public String getPrivilegeCode() {
		return privilegeCode;
	}

	/**
	 * @param privilegeCode
	 *            the privilegeCode to set
	 */
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	/**
	 * @return the privilegeName
	 */
	public String getPrivilegeName() {
		return privilegeName;
	}

	/**
	 * @param privilegeName
	 *            the privilegeName to set
	 */
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	/**
	 * @return the roles
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@JoinTable(name = "SMF_T_SYS_ROLEPRIVILEGES", joinColumns = { @JoinColumn(name = "privilegeId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
	public Set<SysRole> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	/**
	 * @return the opClass
	 */
	public String getOpClass() {
		return opClass;
	}

	/**
	 * @param opClass
	 *            the opClass to set
	 */
	public void setOpClass(String opClass) {
		this.opClass = opClass;
	}

	/**
	 * @return the opMethod
	 */
	public String getOpMethod() {
		return opMethod;
	}

	/**
	 * @param opMethod
	 *            the opMethod to set
	 */
	public void setOpMethod(String opMethod) {
		this.opMethod = opMethod;
	}

	/**
	 * @return the resources
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@JoinTable(name = "SMF_T_SYS_PRIVILEGERESOURCES", joinColumns = { @JoinColumn(name = "privilegeId") }, inverseJoinColumns = { @JoinColumn(name = "resourceId") })
	@OrderBy("sortOrder asc")
	public List<SysResource> getResources() {
		return resources;
	}

	/**
	 * @param resources
	 *            the resources to set
	 */
	public void setResources(List<SysResource> resources) {
		this.resources = resources;
	}

	/**
	 * @return the privilegeType
	 */
	public String getPrivilegeType() {
		return privilegeType;
	}

	/**
	 * @param privilegeType the privilegeType to set
	 */
	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if(o instanceof SysPrivilege){			
			SysPrivilege an = (SysPrivilege)o;
			return EqualsBuilder.reflectionEquals(an.getPrivilegeCode(), getPrivilegeCode());
		}else {
			return false;
		}
	}
	
	public String toString() {
		return privilegeName;
	}

	/**
	 * @return the isLog
	 */
	public Boolean getIsLog() {
		return isLog;
	}

	/**
	 * @param isLog the isLog to set
	 */
	public void setIsLog(Boolean isLog) {
		this.isLog = isLog;
	}

	public String getValueConstraint() {
		return valueConstraint;
	}

	public void setValueConstraint(String valueConstraint) {
		this.valueConstraint = valueConstraint;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
}
