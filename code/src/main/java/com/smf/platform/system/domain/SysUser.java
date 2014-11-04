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
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * 
 */
@Entity
@Table(name = "SMF_T_SYS_USER")
public class SysUser implements Serializable, Comparable<Object>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2125624420319264919L;
	/**
	 * 
	 */
	private Long id;
	private String systemId;
	private String userCode;
	private String userName;
	private String password;
	private String email;
	private String description;
	private String telephone;
	private String cellphone;
	private Set<SysGroup> groups = new HashSet<SysGroup>();
	private Set<SysRole> roles = new HashSet<SysRole>();
	// 是否删除标记，A(Active)表示未删除，I(Inactive)表示已删除
	private String status;

	/**
	 * @return the roles
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@JoinTable(name = "SMF_T_SYS_USERROLES", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
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
	 * @return the userCode
	 */
	@Column(unique = true, nullable = false)
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode
	 *            the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the groups
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@JoinTable(name = "SMF_T_SYS_USERGROUPS", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "groupId") })
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

	public boolean equals(Object obj) {
		if (!(obj instanceof SysUser))
			return false;
		SysUser an = (SysUser)obj;
		return EqualsBuilder.reflectionEquals(this.getUserCode(), an.getUserCode());
	}
	
	public String toString() {
		return userName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 * the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 * the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public int compareTo(Object arg0) {
		if (arg0 instanceof SysUser) {
		return this.getUserCode().compareTo(((SysUser)arg0).getUserCode());
		} else {
			return 0;
		}
	}

}
