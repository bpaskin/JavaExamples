package com.ibm.jee.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Table(name="perfusers")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@NamedQuery(name="User.deleteAll", query="DELETE FROM User")
@NamedQuery(name="User.findUser", query="SELECT u FROM User u WHERE u.name = :name")

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@PositiveOrZero(message="ID must be positive")
	private int id;

	@Size(min=3, message="A Name must be entered and be at least 3 characters in length")
	private String name;

	public User() {
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