package com.springtest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
	private String account_id;
	private String token;
	private Long gmt_create;
	private Long gmt_modified;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Long gmt_create) {
		this.gmt_create = gmt_create;
	}
	public Long getGmt_modified() {
		return gmt_modified;
	}
	public void setGmt_modified(Long gmt_modified) {
		this.gmt_modified = gmt_modified;
	}
	public User() {
		super();
	}
	public User(Integer id, String name, String account_id, String token, Long gmt_create, Long gmt_modified) {
		super();
		this.id = id;
		this.name = name;
		this.account_id = account_id;
		this.token = token;
		this.gmt_create = gmt_create;
		this.gmt_modified = gmt_modified;
	}

	
	
}
