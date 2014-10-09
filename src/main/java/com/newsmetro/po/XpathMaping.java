package com.newsmetro.po;

public class XpathMaping implements java.io.Serializable {

	// Fields

	private Integer id;
	private String url;
	private String xpath;

	// Constructors

	/** default constructor */
	public XpathMaping() {
	}

	/** full constructor */
	public XpathMaping(String url, String xpath) {
		this.url = url;
		this.xpath = xpath;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getXpath() {
		return this.xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

}