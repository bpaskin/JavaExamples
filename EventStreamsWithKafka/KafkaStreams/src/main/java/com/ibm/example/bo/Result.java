package com.ibm.example.bo;

import java.io.Serializable;
import java.time.LocalTime;

public class Result implements Serializable {
	private static final long serialVersionUID = 3330574438490796840L;
	
	private String searchString;
	private String url;
	private LocalTime timesSeached;
	
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public LocalTime getTimesSeached() {
		return timesSeached;
	}
	public void setTimesSeached(LocalTime timesSeached) {
		this.timesSeached = timesSeached;
	}
}
