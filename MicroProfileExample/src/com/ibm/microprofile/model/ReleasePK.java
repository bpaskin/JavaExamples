package com.ibm.microprofile.model;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class ReleasePK implements Serializable {
	private static final long serialVersionUID = -1461673250328484746L;

	@Column(name="NAME")
	private String name;

	@Column(name="ARTIST", insertable=false, updatable=false)
	private String artist;

	public ReleasePK() {
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArtist() {
		return this.artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ReleasePK)) {
			return false;
		}
		ReleasePK castOther = (ReleasePK)other;
		return 
			this.name.equals(castOther.name)
			&& this.artist.equals(castOther.artist);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.name.hashCode();
		hash = hash * prime + this.artist.hashCode();
		
		return hash;
	}
}