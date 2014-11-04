package com.smf.platform.common.autoGrid;

import java.util.List;


public class MetaData {

	private String id;
	// 指明返回总记录数对应的字段名
	private String totalProperty;
	// 指明返回数据对应的字段名
	private String root;
	private List<Field> fields;
	
	// 是否显示行号
	private boolean showRowNumber = false;
	// 是否显示checkBox列
	private boolean showCheckBox = false;
	
	public MetaData() {
		this.id = "1";
		this.root = "results";
		this.totalProperty = "totalCount";
	}
	
	public String getTotalProperty() {
		return totalProperty;
	}
	public void setTotalProperty(String totalProperty) {
		this.totalProperty = totalProperty;
	}
	
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public boolean isShowRowNumber() {
		return showRowNumber;
	}

	public void setShowRowNumber(boolean showRowNumber) {
		this.showRowNumber = showRowNumber;
	}

	public boolean isShowCheckBox() {
		return showCheckBox;
	}

	public void setShowCheckBox(boolean showCheckBox) {
		this.showCheckBox = showCheckBox;
	}

}
