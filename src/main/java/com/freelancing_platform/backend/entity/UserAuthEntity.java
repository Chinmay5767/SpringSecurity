package com.freelancing_platform.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users_auth")

public class UserAuthEntity {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id")
private Long id;

@Column(name="username")
private String username;

@Column(name="emailId")
private String emailID;

@Column(name="password")
private String password;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getEmailID() {
	return emailID;
}

public void setEmailID(String emailID) {
	this.emailID = emailID;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}





	
}
