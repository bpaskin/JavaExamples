package com.ibm.example.vertx.beans;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String name;
	private List<UserOccupation> occupations;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<UserOccupation> getOccupations() {
		return occupations;
	}
	public void setOccupations(List<UserOccupation> occupations) {
		this.occupations = occupations;
	}
	
	public void setOccupationUtil(String ...occupation) {
		occupations = new ArrayList<>();
		
		for (String s: occupation) {
			UserOccupation userOccupation = new UserOccupation();
			userOccupation.setOccupation(s);
			occupations.add(userOccupation);
		}
		
	}
}
