package com.ibm.example.jgroups.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;

// using EclipseLink extension @Cache instead of JPA @Cacheable
// https://www.eclipse.org/eclipselink/documentation/2.7/jpa/extensions/annotations_ref.htm#CHDBBIHE
@Cache 
@Entity
@Table(name="perfusers")
@NamedQuery(name="Perfuser.findAll", query="SELECT p FROM Perfuser p")
public class Perfuser implements Serializable {
	private static final long serialVersionUID = -1336070521180131705L;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	public Perfuser() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}