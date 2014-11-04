package com.smf.platform.framework.dialect;

import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;

/**
 *	使Spring将HibernateException转换成DataAccessException
 */
@SuppressWarnings("serial")
public class SmfHibernateJpaDialect extends HibernateJpaDialect {

	public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
		if (ex instanceof HibernateException)
			return new JpaSystemException(new PersistenceException(ex));
		else
			return EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(ex);
	}
}
