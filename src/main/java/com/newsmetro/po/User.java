package com.newsmetro.po;

import com.newsmetro.enumeration.SexEnum;
import com.newsmetro.enumeration.UserStatus;

import java.util.Date;


/**
 * User entity. @author MyEclipse Persistence Tools
 */
public class User implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String password;
	private String email;
	private SexEnum sex;
	private String photo;
	private String talking;
	private UserStatus status;
	private String code;
	private Date registerDate;


	/** default constructor */
	public User() {
	}

	// Property accessors


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public com.newsmetro.enumeration.SexEnum getSex() {
		return this.sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getTalking() {
		return this.talking;
	}

	public void setTalking(String talking) {
		this.talking = talking;
	}

	public com.newsmetro.enumeration.UserStatus getStatus() {
		return this.status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

}