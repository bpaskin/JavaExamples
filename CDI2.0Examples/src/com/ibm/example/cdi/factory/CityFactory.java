package com.ibm.example.cdi.factory;

public class CityFactory {
	
    public static City getCity(String name) {
        City city = null;
    
        switch (name) {
             case "Rome": city = new Rome(); break;
             case "Cologne": city = new Cologne(); break;
             default: 
                  throw new IllegalStateException("Could not find class based on the name '" + name +"'");
        }
        return city;
   }
}
