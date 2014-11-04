package com.smf.platform.system.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * 
 */
@SuppressWarnings("rawtypes")
@Entity
@Table(name = "SMF_T_SYS_GROUP")
public class SysGroup implements Serializable , Comparable{

	private static final long serialVersionUID = 3693082601521613683L;

	private Long id;
	private String systemId;
	private String groupCode;
	private String groupName;
	private Set<SysGroup> parents = new TreeSet<SysGroup>();
	private Set<SysGroup> children = new TreeSet<SysGroup>();
	private Set<SysUser> users = new TreeSet<SysUser>();
	private Set<SysRole> roles = new HashSet<SysRole>();

	/**
	 * @return the groupName
	 */
	@Column(unique = true, nullable = false)
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the parents
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@Sort(type = SortType.COMPARATOR, comparator = com.smf.platform.system.compare.SmfSysGroupCompare.class)
	@JoinTable(name = "SMF_T_SYS_GROUPRELATION", joinColumns = { @JoinColumn(name = "childId") }, inverseJoinColumns = { @JoinColumn(name = "parantId") })
	public Set<SysGroup> getParents() {
		return parents;
	}

	/**
	 * @param parents the parents to set
	 */
	public void setParents(Set<SysGroup> parents) {
		this.parents = parents;
	}
	
	/**
	 * @return the children
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@Sort(type = SortType.COMPARATOR, comparator = com.smf.platform.system.compare.SmfSysGroupCompare.class)
	@JoinTable(name = "SMF_T_SYS_GROUPRELATION", joinColumns = { @JoinColumn(name = "parantId") }, inverseJoinColumns = { @JoinColumn(name = "childId") })
	public Set<SysGroup> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(Set<SysGroup> children) {
		this.children = children;
	}

	/**
	 * @return the users
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@Sort(type = SortType.COMPARATOR, comparator = com.smf.platform.system.compare.SmfSysUserCompare.class)
	@JoinTable(name = "SMF_T_SYS_USERGROUPS", joinColumns = { @JoinColumn(name = "groupId") }, inverseJoinColumns = { @JoinColumn(name = "userId") })
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
	 * @return the groupCode
	 */
	@Column(unique = true, nullable = false)
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode
	 *            the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the roles
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@JoinTable(name = "SMF_T_SYS_GROUPROLES", joinColumns = { @JoinColumn(name = "groupId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
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
	
	public boolean equals(Object o) {
		if(o instanceof SysGroup){			
			SysGroup an = (SysGroup)o;
			return EqualsBuilder.reflectionEquals(an.getGroupCode(), getGroupCode());
		}else {
			return false;
		}
	}
	
	public String toString() {
		return groupName;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public int compareTo(Object arg0) {
		if(arg0 instanceof SysGroup){
			return this.groupCode.compareTo(((SysGroup)arg0).groupCode);
		}else{
			return 0;
		}
	}
}
