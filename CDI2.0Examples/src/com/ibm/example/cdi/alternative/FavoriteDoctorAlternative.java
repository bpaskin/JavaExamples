package com.ibm.example.cdi.alternative;

import javax.enterprise.inject.Alternative;

@Alternative
public class FavoriteDoctorAlternative implements Doctor {

	@Override
	public String favoriteDoctor() {
		return "Sylvestor McCoy";
	}

}
