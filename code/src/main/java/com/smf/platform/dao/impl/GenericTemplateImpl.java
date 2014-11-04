package com.smf.platform.dao.impl;

import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.object.SqlFunction;

import com.smf.platform.dao.api.GenericTemplate;


public class GenericTemplateImpl implements GenericTemplate{
	private SimpleJdbcDaoSupport daoSupport;

	public DataSource getDataSource(){
		return this.daoSupport.getDataSource();
	}
	
	public SimpleJdbcDaoSupport getDaoSupport() {
		return daoSupport;
	}
	
	public void setDaoSupport(SimpleJdbcDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}
	
	public SimpleJdbcTemplate getSimpleTemplate(){
		return this.daoSupport.getSimpleJdbcTemplate();
	}
	
	public JdbcTemplate getTemplate(){
		return this.daoSupport.getJdbcTemplate();
	}
	
	public NamedParameterJdbcTemplate getNameTemplate(){
		return new NamedParameterJdbcTemplate(this.getTemplate());
	}
	
	public SqlFunction getSqlFunction(String sql,int[] types){
		return new SqlFunction(this.getDataSource(),sql,types);
	}
	
	public SqlFunction getSqlFunction(String sql){
		return new SqlFunction(this.getDataSource(),sql);
	}
	
	public int getResultFromSqlFunction(String sql){
		return this.getResultFromSqlFunction(sql,null,null);
	}
	

	public int getResultFromSqlFunction(String sql,Object[] parameters,int[] types){
		SqlFunction function = this.getSqlFunction(sql,types);
		function.compile();
		if(parameters!=null){
			return function.run(parameters);
		}
		return function.run();
	}
	
	public Object getGenericResultFromSqlFunction(String sql){
		return this.getGenericResultFromSqlFunction(sql,null,null);
	}
	

	public Object getGenericResultFromSqlFunction(String sql,Object[] parameters,int[] types){
		SqlFunction function = this.getSqlFunction(sql,types);
		function.compile();
		if(parameters!=null){
			return function.runGeneric(parameters);
		}
		return function.runGeneric();
	}
	
	public int getPageCount(String sql,int pageSize){
		return this.getPageCount(sql,null,null,pageSize);
	}
	
	public int getPageCount(String sql,Object[] parameters,int pageSize){
		return this.getPageCount(sql, parameters, defaultType(parameters.length), pageSize);
	}
	
	private int[] defaultType(int len) {
		int [] types = new int[len];
		
		for (int i = 0; i < len; i++) {
			types[i] = Types.VARCHAR;
		}
		return types;
	}
	
	public int getPageCount(String sql,Object[] parameters,int[] types,int pageSize){
		int totalCount = this.getResultFromSqlFunction(sql,parameters,types);
		int pageCount = 0;
		if (totalCount == 0) {
			pageCount = 0;
		} else {
			if (totalCount < pageSize) {
				pageCount = 1;
			} else {
				pageCount = totalCount / pageSize;
				if (totalCount % pageSize != 0) {
					pageCount++;
				}
			}
		}
		return pageCount;
	}
	
	public SqlParameterSource getSqlPara(Object o){
		return new BeanPropertySqlParameterSource(o);
	}
	
//	public SqlParameterSource getSqlMapPara(Map<?,?> m){
//		return new MapSqlParameterSource(m);
//	}
}	
