package com.smf.platform.log.service;

/**
 *
 *	
 */
public interface PFLogService {

	public void debug(String name, String logType, Object message);
	
	public void debug(String name, String logType, Object message, Throwable t);
	
	public void debug(String name, String logType, Throwable t);
	
	public void error(String name, String logType, Object message);
	
	public void error(String name, String logType, Object message, Throwable t);
	
	public void error(String name, String logType, Throwable t);
	
	public void fatal(String name, String logType, Object message);
	
	public void fatal(String name, String logType, Object message, Throwable t);
	
	public void fatal(String name, String logType, Throwable t);
	
	public void info(String name, String logType, Object message);
	
	public void info(String name, String logType, Object message, Throwable t);
	
	public void info(String name, String logType, Throwable t);
	
	public void warn(String name, String logType, Object message);
	
	public void warn(String name, String logType, Object message, Throwable t);
	
	public void warn(String name, String logType, Throwable t);
	
	public void debug(String name, String logType, Object message, String userName, String remoteAddress);
	
	public void debug(String name, String logType, Object message, String userName, String remoteAddress, Throwable t);
	
	public void debug(String name, String logType, String userName, String remoteAddress, Throwable t);
	
	public void error(String name, String logType, Object message, String userName, String remoteAddress);
	
	public void error(String name, String logType, Object message, String userName, String remoteAddress, Throwable t);
	
	public void error(String name, String logType, String userName, String remoteAddress, Throwable t);
	
	public void fatal(String name, String logType, Object message, String userName, String remoteAddress);
	
	public void fatal(String name, String logType, Object message, String userName, String remoteAddress, Throwable t);
	
	public void fatal(String name, String logType, String userName, String remoteAddress, Throwable t);
	
	public void info(String name, String logType, Object message, String userName, String remoteAddress);
	
	public void info(String name, String logType, Object message, String userName, String remoteAddress, Throwable t);
	
	public void info(String name, String logType, String userName, String remoteAddress, Throwable t);
	
	public void warn(String name, String logType, Object message, String userName, String remoteAddress);
	
	public void warn(String name, String logType, Object message, String userName, String remoteAddress, Throwable t);
	
	public void warn(String name, String logType, String userName, String remoteAddress, Throwable t);
}
