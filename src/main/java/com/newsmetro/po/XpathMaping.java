package com.newsmetro.po;

import javax.persistence.*;

/**
 * XpathMaping entity. @author MyEclipse Persistence Tools
 */
@Entity
//@Table(name = "xpath_maping", catalog = "news_metro")
@Table(name = "xpath_maping", catalog = "gklifg")
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
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "url", length = 512)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "xpath")
	public String getXpath() {
		return this.xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

}