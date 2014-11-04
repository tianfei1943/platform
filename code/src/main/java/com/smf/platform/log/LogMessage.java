package com.smf.platform.log;

/**
 *
 *	
 */
public class LogMessage {

	private String logType;
	private Object message;
	private String userName;
	private String remoteAddress;
	
	private boolean hasUserName = false;
	
	public LogMessage(String logType, Object message) {
		this.logType = logType;
		this.message = message;
		this.userName = null;
		this.remoteAddress = null;
		this.hasUserName = false;
	}
	
	public LogMessage(String logType, Object message, String userName, String remoteAddress) {
		this.logType = logType;
		this.message = message;
		this.userName = userName;
		this.remoteAddress = remoteAddress;
		this.hasUserName = true;
	}
	
	/**
	 * @return the logType
	 */
	public String getLogType() {
		return logType;
	}

	/**
	 * @return the message
	 */
	public Object getMessage() {
		return message;
	}
	
	public String toString() {
		return logType + " - " + message;
	}

	public String getUserName() {
		return userName;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}
	
	public boolean hasUserName() {
		return this.hasUserName;
	}
	
}
