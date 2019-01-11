package com.ibm.example.cdi.named;

import javax.inject.Named;

@Named("Cologne")
public class Cologne implements City {
	
    private String englishName = "Cologne";
    private String localName = "Köln";
    private int population = 1024373;

	@Override
	public String getEnglishName() {
		return this.englishName;
	}

	@Override
	public String getLocalName() {
		return this.localName;
	}

	@Override
	public int getPopulation() {
		return this.population;
	}
}
