package com.smf.platform.log.service.impl;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import com.smf.platform.log.LogMessage;
import com.smf.platform.log.service.PFLogService;



/**
 *
 *	
 */
public class PFLogServiceImpl implements PFLogService {

	private Appender appender;
	
	/**
	 * @return the appender
	 */
	public Appender getAppender() {
		return appender;
	}

	/**
	 * @param appender the appender to set
	 */
	public void setAppender(Appender appender) {
		this.appender = appender;
	}
	
	private Logger getLogger(String name) {
		Logger logger = Logger.getLogger(name);
		if(appender != null) {
			logger.addAppender(appender);
		}
		return logger;
	}

	public void debug(String name, String logType, Object message) {
		Logger logger = getLogger(name);
		if(logger.isDebugEnabled())
			logger.debug(new LogMessage(logType, message));
	}

	public void debug(String name, String logType, Object message, Throwable t) {
		Logger logger = getLogger(name);
		if(logger.isDebugEnabled())
			logger.debug(new LogMessage(logType, message), t);		
	}
	
	public void debug(String name, String logType, Throwable t) {
		Logger logger = getLogger(name);
		if(logger.isDebugEnabled())
			logger.debug(new LogMessage(logType, null), t);		
	}

	public void error(String name, String logType, Object message) {
		getLogger(name).error(new LogMessage(logType, message));		
	}

	public void error(String name, String logType, Object message, Throwable t) {
		getLogger(name).error(new LogMessage(logType, message), t);	
	}
	
	public void error(String name, String logType, Throwable t) {
		getLogger(name).error(new LogMessage(logType, null), t);	
	}

	public void fatal(String name, String logType, Object message) {
		getLogger(name).fatal(new LogMessage(logType, message));		
	}

	public void fatal(String name, String logType, Object message, Throwable t) {
		getLogger(name).fatal(new LogMessage(logType, message), t);		
	}
	
	public void fatal(String name, String logType, Throwable t) {
		getLogger(name).fatal(new LogMessage(logType, null), t);		
	}

	public void info(String name, String logType, Object message) {
		Logger logger = getLogger(name);
		if(logger.isInfoEnabled())
			logger.info(new LogMessage(logType, message));		
	}

	public void info(String name, String logType, Object message, Throwable t) {
		Logger logger = getLogger(name);
		if(logger.isInfoEnabled())
			logger.info(new LogMessage(logType, message), t);		
	}
	
	public void info(String name, String logType, Throwable t) {
		Logger logger = getLogger(name);
		if(logger.isInfoEnabled())
			logger.info(new LogMessage(logType, null), t);		
	}

	public void warn(String name, String logType, Object message) {
		getLogger(name).warn(new LogMessage(logType, message));		
	}

	public void warn(String name, String logType, Object message, Throwable t) {
		getLogger(name).warn(new LogMessage(logType, message), t);		
	}
	
	public void warn(String name, String logType, Throwable t) {
		getLogger(name).warn(new LogMessage(logType, null), t);		
	}
	
	public void debug(String name, String logType, Object message, String userName, String remoteAddress) {
		Logger logger = getLogger(name);
		if(logger.isDebugEnabled())
			logger.debug(new LogMessage(logType, message, userName, remoteAddress));
	}

	public void debug(String name, String logType, Object message, String userName, String remoteAddress, Throwable t) {
		Logger logger = getLogger(name);
		if(logger.isDebugEnabled())
			logger.debug(new LogMessage(logType, message, userName, remoteAddress), t);		
	}
	
	public void debug(String name, String logType, String userName, String remoteAddress, Throwable t) {
		Logger logger = getLogger(name);
		if(logger.isDebugEnabled())
			logger.debug(new LogMessage(logType, null, userName, remoteAddress), t);		
	}

	public void error(String name, String logType, Object message, String userName, String remoteAddress) {
		getLogger(name).error(new LogMessage(logType, message, userName, remoteAddress));		
	}

	public void error(String name, String logType, Object message, String userName, String remoteAddress, Throwable t) {
		getLogger(name).error(new LogMessage(logType, message, userName, remoteAddress), t);	
	}
	
	public void error(String name, String logType, String userName, String remoteAddress, Throwable t) {
		getLogger(name).error(new LogMessage(logType, null, userName, remoteAddress), t);	
	}

	public void fatal(String name, String logType, Object message, String userName, String remoteAddress) {
		getLogger(name).fatal(new LogMessage(logType, message, userName, remoteAddress));		
	}

	public void fatal(String name, String logType, Object message, String userName, String remoteAddress, Throwable t) {
		getLogger(name).fatal(new LogMessage(logType, message, userName, remoteAddress), t);		
	}
	
	public void fatal(String name, String logType, String userName, String remoteAddress, Throwable t) {
		getLogger(name).fatal(new LogMessage(logType, null, userName, remoteAddress), t);		
	}
	
	public void info(String name, String logType, Object message, String userName, String remoteAddress) {
		Logger logger = getLogger(name);
		if(logger.isInfoEnabled())
			logger.info(new LogMessage(logType, message, userName, remoteAddress));		
	}

	public void info(String name, String logType, Object message, String userName, String remoteAddress, Throwable t) {
		Logger logger = getLogger(name);
		if(logger.isInfoEnabled())
			logger.info(new LogMessage(logType, message, userName, remoteAddress), t);		
	}
	
	public void info(String name, String logType, String userName, String remoteAddress, Throwable t) {
		Logger logger = getLogger(name);
		if(logger.isInfoEnabled())
			logger.info(new LogMessage(logType, null, userName, remoteAddress), t);		
	}

	public void warn(String name, String logType, Object message, String userName, String remoteAddress) {
		getLogger(name).warn(new LogMessage(logType, message, userName, remoteAddress));		
	}

	public void warn(String name, String logType, Object message, String userName, String remoteAddress, Throwable t) {
		getLogger(name).warn(new LogMessage(logType, message, userName, remoteAddress), t);		
	}
	
	public void warn(String name, String logType, String userName, String remoteAddress, Throwable t) {
		getLogger(name).warn(new LogMessage(logType, null, userName, remoteAddress), t);		
	}

}
