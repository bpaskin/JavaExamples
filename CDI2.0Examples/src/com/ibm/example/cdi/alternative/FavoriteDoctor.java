package com.ibm.example.cdi.alternative;

public class FavoriteDoctor implements Doctor {

	@Override
	public String favoriteDoctor() {
		return "Peter Davison";
	}

}
