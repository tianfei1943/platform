package com.smf.platform.common.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageResponse<T> implements Serializable {

	private static final long serialVersionUID = -8420904956564762415L;

	// 总记录数
	private int total = 0;

	// 查询结果
	private List<T> result = null;

	public PageResponse() {
		super();
		this.result = new ArrayList<T>();
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
