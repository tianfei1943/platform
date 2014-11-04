package com.smf.platform.common.autoGrid;

import java.util.List;

public class AutoGridResponse<T> {

	private MetaData metaData;
	// 返回数据的总记录数
	private int totalCount = 0;
	private List<T> results;
	
	public MetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	
}
