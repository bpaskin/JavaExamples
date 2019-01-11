package com.ibm.example.cdi.factory;

public class Rome implements City {
	
    private String englishName = "Rome";
    private String localName = "Roma";
    private int population = 2645907;

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
