package com.smf.platform.common.xls;
/**
 * @author gu
 * 
 */
public class XlsElement{
	private String property;//对象（包括自定义的类型，也包括java.lang.Double类型）
	private String clazzStr;//该对象表示类的全路径，比如java.lang.Double
	private XlsElement child;//孩子节点
	private XlsElement parent;//父节点
	private String formateStyle;//格式化的路径
	private String color; //xls的颜色

	public XlsElement getChild() {
		return child;
	}

	public void setChild(XlsElement child) {
		this.child = child;
	}

	public XlsElement getParent() {
		return parent;
	}

	public void setParent(XlsElement parent) {
		this.parent = parent;
	}

	public String getClazzStr() {
		return clazzStr;
	}

	public void setClazzStr(String clazzStr) {
		this.clazzStr = clazzStr;
	}

	public boolean hasChild() {
		return (this.getChild() != null);
	}

	public boolean hasParent() {
		return (this.getParent() != null);
	}

	/**
	 * @return the formateStyle
	 */
	public String getFormateStyle() {
		return formateStyle;
	}

	/**
	 * @param formateStyle the formateStyle to set
	 */
	public void setFormateStyle(String formateStyle) {
		this.formateStyle = formateStyle;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

}
