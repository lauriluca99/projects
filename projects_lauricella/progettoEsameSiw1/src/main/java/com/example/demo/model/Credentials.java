package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

@Entity
public class Credentials {
	
	public static final String DEFAULT_ROLE= "DEFAULT";
	public static final String ADMIN_ROLE= "ADMIN";

	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false, length=100)
	private String username;
	
	@Column(nullable=false, length=100)
	private String password;	
	
	@Column(nullable=false, length=10) 
	private String role;
	
	@OneToOne(cascade=CascadeType.ALL)
	private User user;
	
	@Column(updatable=false, nullable=false)
	private LocalDateTime creationTimestamp;
	
	
	
	public Credentials () {
	}
	
	
	
	
	public Credentials (String username, String password) {
		this();
		this.username=username;
		this.password=password;
	}


	@PrePersist
	protected void onPersist() {
		this.creationTimestamp=LocalDateTime.now();
	}




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




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getRole() {
		return role;
	}




	public void setRole(String role) {
		this.role = role;
	}




	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}




	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}




	public void setCreationTimestamp(LocalDateTime creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}




	public static String getDefaultRole() {
		return DEFAULT_ROLE;
	}




	public static String getAdminRole() {
		return ADMIN_ROLE;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credentials other = (Credentials) obj;
		if (creationTimestamp == null) {
			if (other.creationTimestamp != null)
				return false;
		} else if (!creationTimestamp.equals(other.creationTimestamp))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}




	@Override
	public String toString() {
		return "Credentials [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", user=" + user + ", creationTimestamp=" + creationTimestamp + "]";
	}

	
}