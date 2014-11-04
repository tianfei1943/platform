package com.smf.platform.system.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author
 * @since
 * 
 */
@Entity
@Table(name = "SMF_T_SYS_PARAMETERVALUE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysParameterValue implements Serializable {

	private static final long serialVersionUID = -4623789488517909241L;
	// 主键
	private Long id;
	private String systemId;
	// 参数类型
	private SysParameterType type = new SysParameterType();
	// 参数名称
	private String name;
	// 参数描述
	private String description;
	// 是否用户可以定制该参数，暂不使用
	private String userCustom;
	// 是否系统默认值，暂不使用
	private String isDefaultValue;
	// 参数值
	private String value;

	private Integer sortNum;

	private SysParameterValue parentSysParameterValue;
	// 用于分组字段，有type.id和parentSysParameterValue.id组成
	@SuppressWarnings("unused")
	private String groupValue;

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
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
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Column(unique = false, nullable = true)
	public String getUserCustom() {
		return userCustom;
	}

	/**
	 * 
	 * @param name
	 *            the userCustom to set
	 */
	public void setUserCustom(String userCustom) {
		this.userCustom = userCustom;
	}

	/**
	 * 
	 * @return type
	 */
	@ManyToOne
	@JoinColumn(name = "typeId")
	public SysParameterType getType() {
		return type;
	}

	/**
	 * 
	 * @param name
	 *            the type to set
	 */
	public void setType(SysParameterType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIsDefaultValue() {
		return isDefaultValue;
	}

	public void setIsDefaultValue(String isDefaultValue) {
		this.isDefaultValue = isDefaultValue;
	}

	@Column(unique = false, nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean equals(Object obj) {
		if (obj instanceof SysParameterValue) {
			SysParameterValue entity = (SysParameterValue) obj;
			return this.id.longValue() == entity.getId().longValue();
		} else {
			return false;
		}
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	public SysParameterValue getParentSysParameterValue() {
		return parentSysParameterValue;
	}

	public void setParentSysParameterValue(
			SysParameterValue parentSysParameterValue) {
		this.parentSysParameterValue = parentSysParameterValue;
	}

	@Transient
	public String getGroupValue() {
		if (this.getParentSysParameterValue() != null) {
			return this.getType().getParameterType() + "-" + this.getParentSysParameterValue().getName();	
		} else {
			return this.getType().getParameterType();
		}		
	}

	public void setGroupValue(String groupValue) {
		this.groupValue = groupValue;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}
