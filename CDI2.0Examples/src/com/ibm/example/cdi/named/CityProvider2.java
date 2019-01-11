package com.ibm.example.cdi.named;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

public class CityProvider2 {
	@Inject private Instance<City> instance;
	
	public City getCity(String name) {
		for (City city : instance) {
			if (city.getClass().getSimpleName().contains(name)) {
				return city;
			}
		}
		
		throw new IllegalStateException("Cannot find instance of " + name);
	}
	
	public City getCity(Class<?> clazz) {
		for (City city : instance)  {
			if (city.getClass().equals(clazz)) {
				return city;
			}
		}
		
		throw new IllegalStateException("Cannot find instance of " + clazz.getCanonicalName());
	}
	
	public List<String> getAllCities() {
		List<String> cities = new ArrayList<String>();
		for (City city: instance) {
			cities.add(city.getEnglishName());
		}
		return cities;
	}
}
