package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(nullable=false, length=100)
	private String firstname;
	
	@Column(nullable=false, length=100)
	private String lastname;
	
	@Column(updatable=false, nullable=false)
	private LocalDateTime creationTimestamp;
	
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy= "owner")
	private List<Project> ownedProjects;
	
	@ManyToMany(mappedBy = "members")
	private List<Project> visibleProjects;
	
	public User() {
		this.ownedProjects= new ArrayList<>();
		this.visibleProjects= new ArrayList<>();
	}

	
	public User(String username, String password, String nome,String cognome) {
		this();
		this.firstname=nome;
		this.lastname=cognome;
		
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


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}


	public void setCreationTimestamp(LocalDateTime creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}


	public List<Project> getOwnedProjects() {
		return ownedProjects;
	}


	public void setOwnedProjects(List<Project> ownedProjects) {
		this.ownedProjects = ownedProjects;
	}


	public List<Project> getVisibleProjects() {
		return visibleProjects;
	}


	public void setVisibleProjects(List<Project> visibleProjects) {
		this.visibleProjects = visibleProjects;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((ownedProjects == null) ? 0 : ownedProjects.hashCode());
		result = prime * result + ((visibleProjects == null) ? 0 : visibleProjects.hashCode());
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
		User other = (User) obj;
		if (creationTimestamp == null) {
			if (other.creationTimestamp != null)
				return false;
		} else if (!creationTimestamp.equals(other.creationTimestamp))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (ownedProjects == null) {
			if (other.ownedProjects != null)
				return false;
		} else if (!ownedProjects.equals(other.ownedProjects))
			return false;
		if (visibleProjects == null) {
			if (other.visibleProjects != null)
				return false;
		} else if (!visibleProjects.equals(other.visibleProjects))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", creationTimestamp="
				+ creationTimestamp + ", ownedProjects=" + ownedProjects + ", visibleProjects=" + visibleProjects + "]";
	}


	}