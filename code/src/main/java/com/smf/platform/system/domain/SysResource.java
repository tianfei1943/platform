package com.smf.platform.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.smf.platform.util.ReflectionUtils;

/**
 * 
 */
@Entity
@Table(name = "SMF_T_SYS_RESOURCE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysResource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4278597450657562938L;
	/**
	 * 
	 */
	private Long id;
	private String systemId;
	private String resourceName;
	private String resourceType;
	private String resourceUrl;
	private String desciption;
	private Integer sortOrder;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId", unique = false)
	private SysResource parent;
	private String errorMessage;
	//为_self时，在当前窗口打开。
	//为_blank时，在新窗口打开。
	//为rightPanel时，在右边区域打开。
	private String openType;
	// 该属性仅对菜单类型的资源有效，表示是否为默认值，表示在点击父菜单时，默认打开的子菜单
	private boolean defaultOpen;
	// 是否删除标记，1表示未删除，0表示已删除
	private int status = 1;
	
	@Transient
	private Set<SysPrivilege> privileges = new HashSet<SysPrivilege>();
	private List<SysResource> children = new ArrayList<SysResource>();

	@OneToMany(fetch = javax.persistence.FetchType.LAZY)
	@JoinColumn(name = "parentId")
	//@OrderBy("sortOrder")
	public List<SysResource> getChildren() {
		return children;
	}

	public void setChildren(List<SysResource> children) {
		this.children = children;
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
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName
	 *            the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType
	 *            the resourceType to set
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return the resourceUrl
	 */
	@Column(length = 2048)
	public String getResourceUrl() {
		return resourceUrl;
	}

	/**
	 * @param resourceUrl
	 *            the resourceUrl to set
	 */
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	/**
	 * @return the privileges
	 */
	@ManyToMany(fetch = javax.persistence.FetchType.LAZY)
	@JoinTable(name = "SMF_T_SYS_PRIVILEGERESOURCES", joinColumns = { @JoinColumn(name = "resourceId") }, inverseJoinColumns = { @JoinColumn(name = "privilegeId") })
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
	 * @return the parent
	 */
	@ManyToOne(fetch = javax.persistence.FetchType.LAZY)
	@JoinColumn(name = "parentId")
	public SysResource getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(SysResource parent) {
		this.parent = parent;
	}

	/**
	 * @return the desciption
	 */
	public String getDesciption() {
		return desciption;
	}

	/**
	 * @param desciption the desciption to set
	 */
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	/**
	 * @return the sortOrder
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean equals(Object o) {
		if(!(o instanceof SysResource))
			return false;
		SysResource an = (SysResource)o;
		return EqualsBuilder.reflectionEquals(an.getId(), getId());
	}
	
	public String toString() {
		return resourceName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}
	
	public boolean isDefaultOpen() {
		return defaultOpen;
	}

	public void setDefaultOpen(boolean defaultOpen) {
		this.defaultOpen = defaultOpen;
	}

	/**
	 * 可访问该资源的授权名称字符串, 多个授权用','分隔.
	 * 因为资源的权限设定可能在父节点，因此资源的权限需访问其父节点的权限，直至根节点
	 */
	@Transient
	public String getAuthNames() {
		List<SysPrivilege> privilegeList = this.getParentPrivilege(this);
		String authNames = ReflectionUtils.convertElementPropertyToString(privilegeList, "privilegeCode", ",");
		return authNames;
	}
	
	private List<SysPrivilege> getParentPrivilege(SysResource sysResource) {
		Iterator<SysPrivilege> privilegeIter = sysResource.getPrivileges().iterator();
		List<SysPrivilege> privilegeList = new ArrayList<SysPrivilege>();
		while (privilegeIter.hasNext()) {
			privilegeList.add(privilegeIter.next());
		}
		if (sysResource.getParent() != null) {
			privilegeList.addAll(this.getParentPrivilege(sysResource.getParent()));
		}
		return privilegeList;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
