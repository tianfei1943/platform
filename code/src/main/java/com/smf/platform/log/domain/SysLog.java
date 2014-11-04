package com.smf.platform.log.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "SMF_T_SYS_LOG")
@SuppressWarnings("serial")
public class SysLog implements Serializable {

	private Long id;
	private String systemId;
	private String username;
	private String remoteAddress;
	private String logName;
	private String logType;
	private String message;	
	private String throwableInformation;
	private Date logTime;
	private int logLevel;
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the username
	 */
	@Column(length = 50)
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	/**
	 * @return the remoteAddress
	 */
	@Column(length = 50)
	public String getRemoteAddress() {
		return remoteAddress;
	}
	/**
	 * @param remoteAddress the remoteAddress to set
	 */
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	
	/**
	 * @return the throwableInformation
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getThrowableInformation() {
		return throwableInformation;
	}
	/**
	 * @param throwableInformation the throwableInformation to set
	 */
	public void setThrowableInformation(String throwableInformation) {
		this.throwableInformation = throwableInformation;
	}
	/**
	 * @return the logTime
	 */
	public Date getLogTime() {
		return logTime;
	}
	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	/**
	 * @return the logLevel
	 */
	public int getLogLevel() {
		return logLevel;
	}
	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}
	
	/**
	 * @return the logName
	 */
	public String getLogName() {
		return logName;
	}
	/**
	 * @param logName the logName to set
	 */
	public void setLogName(String logName) {
		this.logName = logName;
	}
	/**
	 * @return the message
	 */
	@Column(length = 4000)
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the logType
	 */
	@Column(length = 50)
	public String getLogType() {
		return logType;
	}
	/**
	 * @param logType the logType to set
	 */
	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	public String getSystemId() {
		return systemId;
	}
	
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
}
