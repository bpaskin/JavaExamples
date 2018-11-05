package com.ibm.spring.jpa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Table(name="perfusers")
@NamedQuery(name="User.findUser", query="SELECT u FROM User u where u.name =:name", lockMode=LockModeType.PESSIMISTIC_READ)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@PositiveOrZero(message="ID must be positive")
	private Integer id;

	@Size(min=3, message="A Name must be entered and be at least 3 characters in length")
	private String name;

	public User() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}