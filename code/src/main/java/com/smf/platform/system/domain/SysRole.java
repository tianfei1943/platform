package com.smf.platform.system.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * 
 */
@Entity
@Table(name = "SMF_T_SYS_ROLE")
public class SysRole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7571322723236270219L;
	/**
	 * 
	 * /**
	 * 
	 */
	private Long id;
	private String systemId;
	private String roleCode;
	private String roleName;
	private SysRole parent;
	private Set<SysPrivilege> privileges = new HashSet<SysPrivilege>();
	private Set<SysUser> users = new HashSet<SysUser>();
	private Set<SysGroup> groups = new HashSet<SysGroup>();

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
	 * @return the roleCode
	 */
	@Column(unique = true, nullable = false)
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @param roleCode
	 *            the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the privileges
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@JoinTable(name = "SMF_T_SYS_ROLEPRIVILEGES", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = { @JoinColumn(name = "privilegeId") })
	public Set<SysPrivilege> getPrivileges() {
		return privileges;
	}

	/**
	 * @param privileges
	 *            the privileges to set
	 */
	public void setPrivileges(Set<SysPrivilege> privileges) {
		this.privileges = privileges;
	}

	/**
	 * @return the users
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@JoinTable(name = "SMF_T_SYS_USERROLES", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = { @JoinColumn(name = "userId") })
	public Set<SysUser> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}

	/**
	 * @return the groups
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@JoinTable(name = "SMF_T_SYS_GROUPROLES", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = { @JoinColumn(name = "groupId") })
	public Set<SysGroup> getGroups() {
		return groups;
	}

	/**
	 * @param groups
	 *            the groups to set
	 */
	public void setGroups(Set<SysGroup> groups) {
		this.groups = groups;
	}

	/**
	 * @return the parent
	 */
	@ManyToOne(fetch = javax.persistence.FetchType.LAZY)
	@JoinColumn(name = "parentId")
	public SysRole getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(SysRole parent) {
		this.parent = parent;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if(!(o instanceof SysRole))
			return false;
		SysRole an = (SysRole)o;
		return EqualsBuilder.reflectionEquals(an.getRoleCode(), getRoleCode());
	}
	
	public String toString() {
		return roleName;
	}

}
