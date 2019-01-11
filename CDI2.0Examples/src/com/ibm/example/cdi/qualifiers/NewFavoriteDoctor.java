package com.ibm.example.cdi.qualifiers;

@Favorite
public class NewFavoriteDoctor extends FavoriteDoctor {

	public String getDoctor() {
		return "10th Doctor (David Tennant)";
	}
}
