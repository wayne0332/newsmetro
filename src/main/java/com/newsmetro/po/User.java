package com.newsmetro.po;

import com.newsmetro.enumeration.SexEnum;
import com.newsmetro.enumeration.UserStatus;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
//@Table(name = "user", catalog = "news_metro")
@Table(name = "user", catalog = "gklifg")
public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String password;
	private String email;
	private SexEnum sex;
	private String photo;
	private String talking;
	private UserStatus status;
	private String code;
	private Date registerDate;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(Integer id, String name, String password, String email,
			SexEnum sex, String photo, String talking, UserStatus status, String code) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.sex = sex;
		this.photo = photo;
		this.talking = talking;
		this.status = status;
		this.code = code;
	}

	/** full constructor */
	public User(Integer id, String name, String password, String email,
			SexEnum sex, String photo, String talking, UserStatus status,
			String code, Date registerDate) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.sex = sex;
		this.photo = photo;
		this.talking = talking;
		this.status = status;
		this.code = code;
		this.registerDate = registerDate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password", nullable = false, length = 45)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email", nullable = false, length = 45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "sex", nullable = false, length = 45)
	@Enumerated(EnumType.STRING)
	public com.newsmetro.enumeration.SexEnum getSex() {
		return this.sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

	@Column(name = "photo", nullable = false)
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name = "talking", nullable = false)
	public String getTalking() {
		return this.talking;
	}

	public void setTalking(String talking) {
		this.talking = talking;
	}

	@Column(name = "status", nullable = false, length = 45)
	@Enumerated(EnumType.STRING)
	public com.newsmetro.enumeration.UserStatus getStatus() {
		return this.status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	@Column(name = "code", nullable = false)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "registerDate", length = 10)
	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

}