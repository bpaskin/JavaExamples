package com.ibm.microprofile.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Cacheable
@Table(name="ARTISTS")
@NamedQuery(name="Artist.findAll", query="SELECT a FROM Artist a")
public class Artist implements Serializable {
	private static final long serialVersionUID = -4707339119186512182L;
	
	@Id
	@Column(name="NAME")
	private String name;

	public Artist() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}