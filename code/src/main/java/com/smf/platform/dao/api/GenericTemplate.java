package com.smf.platform.dao.api;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.object.SqlFunction;

public interface GenericTemplate {
	public abstract DataSource getDataSource();

	public abstract SimpleJdbcDaoSupport getDaoSupport();

	public abstract void setDaoSupport(SimpleJdbcDaoSupport daoSupport);

	public abstract SimpleJdbcTemplate getSimpleTemplate();

	public abstract JdbcTemplate getTemplate();

	public abstract NamedParameterJdbcTemplate getNameTemplate();

	public abstract SqlFunction getSqlFunction(String sql, int[] types);

	public abstract SqlFunction getSqlFunction(String sql);

	public abstract int getResultFromSqlFunction(String sql);

	public abstract int getResultFromSqlFunction(String sql,
			Object[] parameters, int[] types);

	public abstract Object getGenericResultFromSqlFunction(String sql);

	public abstract Object getGenericResultFromSqlFunction(String sql,
			Object[] parameters, int[] types);

	public abstract int getPageCount(String sql, int pageSize);

	public abstract int getPageCount(String sql, Object[] parameters,
			int pageSize);

	public abstract int getPageCount(String sql, Object[] parameters,
			int[] types, int pageSize);

	public abstract SqlParameterSource getSqlPara(Object o);

//	public abstract SqlParameterSource getSqlMapPara(Map<?, ?> m);
}
