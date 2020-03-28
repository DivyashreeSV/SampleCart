package com.ipl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LOGIN")
public class Login {
	
	@Id
	@Column(name="LOGIN_ID")
	private Integer login_id;
	
	@Column(name="PASSWORD")
	private String password;
	
	public Login() {
	}

	public Integer getLogin_id() {
		return login_id;
	}

	public String getPassword() {
		return password;
	}
	
}