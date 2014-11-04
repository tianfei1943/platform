package com.smf.platform.system.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Node implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9175440663117574715L;
	private String id;//节点的 id
	private String text;//节点的文字
	private Boolean checked;//节点是否被选中
	private String attributes;//节点自定义属性
	private String target;//被点击目标的 DOM 对象
	private String state;
	private String iconCls;
	private Set<Node> parents = new HashSet<Node>();
	private Set<Node> children = new HashSet<Node>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Set<Node> getParents() {
		return parents;
	}
	public void setParents(Set<Node> parents) {
		this.parents = parents;
	}
	public Set<Node> getChildren() {
		return children;
	}
	public void setChildren(Set<Node> children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
}
