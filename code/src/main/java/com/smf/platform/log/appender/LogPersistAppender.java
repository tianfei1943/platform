package com.smf.platform.log.appender;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import com.smf.platform.framework.SystemConfig;
import com.smf.platform.log.LogMessage;
import com.smf.platform.log.LogOwnerProvider;
import com.smf.platform.log.domain.SysLog;

/**
 * 
 */
public class LogPersistAppender extends AppenderSkeleton {

	public LogPersistAppender() {
		this.setName(this.getClass().getName());
	}
	
	private LogOwnerProvider logOwnerProvider;

	@PersistenceContext(unitName="persistenceUnit")
	private EntityManager em;


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
	 */
	@Override
	protected void append(LoggingEvent event) {
		Object message = event.getMessage();
		if (!(message instanceof LogMessage))
			return;
		
		ThrowableInformation ti = event.getThrowableInformation();
		Throwable throwable = null;
		if (ti != null)
			throwable = ti.getThrowable();
		
		LogMessage smfMessage = (LogMessage) message;
		
		SysLog log = new SysLog();
		log.setLogName(event.getLoggerName());
		if (smfMessage.getMessage() != null) {
			String msg = smfMessage.getMessage().toString();
			if(msg.length() > 1000)
				msg = msg.substring(0, 1000);
			log.setMessage(msg);
		}
		log.setLogType(smfMessage.getLogType());

		if (smfMessage.hasUserName()) {
			log.setUsername(smfMessage.getUserName());
			log.setRemoteAddress(smfMessage.getRemoteAddress());
		} else {
			if(logOwnerProvider != null) {
				log.setUsername(logOwnerProvider.getUsername());
				log.setRemoteAddress(logOwnerProvider.getRemoteAddress());
			}
		}
		
		if(throwable != null) {
			StringWriter sw = new StringWriter();
			throwable.printStackTrace(new PrintWriter(sw));
			log.setThrowableInformation(sw.toString());
		}

		log.setLogTime(new Date());
		log.setLogLevel(event.getLevel().toInt());
		log.setSystemId(SystemConfig.getSystemId());

		if(em != null)
			em.persist(log);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.AppenderSkeleton#close()
	 */
	@Override
	public void close() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.AppenderSkeleton#requiresLayout()
	 */
	@Override
	public boolean requiresLayout() {
		return false;
	}

	/**
	 * @return the logOwnerProvider
	 */
	public LogOwnerProvider getLogOwnerProvider() {
		return logOwnerProvider;
	}

	/**
	 * @param logOwnerProvider the logOwnerProvider to set
	 */
	public void setLogOwnerProvider(LogOwnerProvider logOwnerProvider) {
		this.logOwnerProvider = logOwnerProvider;
	}

}
