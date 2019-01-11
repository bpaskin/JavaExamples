package com.ibm.example.cdi.qualifiers;

import javax.enterprise.inject.Default;

// Default is not needed, as it is implied, but added for more clarity

@Default
public class FavoriteDoctor {

	public String getDoctor() {
		return "3rd Doctor (Jon Pertwee)";
	}
}
