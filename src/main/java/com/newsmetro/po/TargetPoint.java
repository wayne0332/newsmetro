package com.newsmetro.po;

import com.newsmetro.enumeration.TargetStatus;



/**
 * TargetPoint entity. @author MyEclipse Persistence Tools
 */

public class TargetPoint implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String name;
	private String url;
	private String absXpath;
	private String relXpath;
	private String regex;
	private String md5;
	private Boolean isRss;
	private TargetStatus status;

	// Constructors

	/** default constructor */
	public TargetPoint() {
	}

	/** minimal constructor */
	public TargetPoint(User user, String name, String url, TargetStatus status) {
		this.user = user;
		this.name = name;
		this.url = url;
		this.status = status;
	}

	/** full constructor */
	public TargetPoint(User user, String name, String url,
			String absXpath, String relXpath, String regex, String md5,
			Boolean isRss, TargetStatus status) {
		this.user = user;
		this.name = name;
		this.url = url;
		this.absXpath = absXpath;
		this.relXpath = relXpath;
		this.regex = regex;
		this.md5 = md5;
		this.isRss = isRss;
		this.status = status;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAbsXpath() {
		return this.absXpath;
	}

	public void setAbsXpath(String absXpath) {
		this.absXpath = absXpath;
	}

	public String getRelXpath() {
		return this.relXpath;
	}

	public void setRelXpath(String relXpath) {
		this.relXpath = relXpath;
	}

	public String getRegex() {
		return this.regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getMd5() {
		return this.md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Boolean getIsRss() {
		return this.isRss;
	}

	public void setIsRss(Boolean isRss) {
		this.isRss = isRss;
	}

	public com.newsmetro.enumeration.TargetStatus getStatus() {
		return status;
	}

	public void setStatus(TargetStatus status) {
		this.status = status;
	}


}