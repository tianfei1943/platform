package com.smf.platform.system.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author  
 * @date
 * 
 */
@Entity
@Table(name = "SMF_T_SYS_PARAMETERTYPE")
public class SysParameterType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4692803494591815232L;
	// 主键
	private java.lang.Long id;
	private String systemId;
	// 参数编码
	private java.lang.String parameterCode;
	// 参数类型名
	private java.lang.String parameterType;

    /**
    * 
    * @return java.lang.Long
    */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public java.lang.Long getId(){
        return id;
    }

	/**
	 * @param id
	 * 
	 */
    public void setId(java.lang.Long id){
        this.id = id;
    }    

    /**
    * 
    * @return java.lang.String
    */
	public java.lang.String getParameterType(){
        return parameterType;
    }

	/**
	 * @param parameterType
	 * 
	 */
    public void setParameterType(java.lang.String parameterType){
        this.parameterType = parameterType;
    }

	/**
	 * @return the parameterCode
	 */
	public java.lang.String getParameterCode() {
		return parameterCode;
	}

	/**
	 * @param parameterCode the parameterCode to set
	 */
	public void setParameterCode(java.lang.String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
 
}