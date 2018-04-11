package com.ibm.microprofile.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Cacheable
@Table(name="RELEASES")
@NamedQueries(value= {
		@NamedQuery(name="Release.findAll", query="SELECT r FROM Release r"),
		@NamedQuery(name="Release.findAllByArtist", query="SELECT r FROM Release r WHERE r.artistBean.name = :artist")
})
public class Release implements Serializable {
	private static final long serialVersionUID = 168608371529964622L;

	@EmbeddedId
	private ReleasePK id;

	@Column(name="YEAR")
	private int year;

	//uni-directional many-to-one association to Artist
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ARTIST")
	private Artist artistBean;

	public Release() {
	}

	public ReleasePK getId() {
		return this.id;
	}

	public void setId(ReleasePK id) {
		this.id = id;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Artist getArtistBean() {
		return this.artistBean;
	}

	public void setArtistBean(Artist artistBean) {
		this.artistBean = artistBean;
	}

}