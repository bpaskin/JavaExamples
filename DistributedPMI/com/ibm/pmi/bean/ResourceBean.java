package com.ibm.pmi.bean;

import java.io.Serializable;

public class ResourceBean implements Serializable {
	private static final long serialVersionUID = 7629659768421241241L;
	protected String resourceName;
	protected String propertyName;
	protected String description;
	protected long   highWaterMark;
	protected long   lowWaterMark;
	protected long   currentMark;
	protected long   minTime;
	protected long   maxTime;
	protected long   totalTime;
	protected long   count;
	protected long   timestamp;
	
	
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public long getMinTime() {
		return minTime;
	}
	public void setMinTime(long minTime) {
		this.minTime = minTime;
	}
	public long getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}
	public long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getHighWaterMark() {
		return highWaterMark;
	}
	public void setHighWaterMark(long highWaterMark) {
		this.highWaterMark = highWaterMark;
	}
	public long getLowWaterMark() {
		return lowWaterMark;
	}
	public void setLowWaterMark(long lowWaterMark) {
		this.lowWaterMark = lowWaterMark;
	}
	public long getCurrentMark() {
		return currentMark;
	}
	public void setCurrentMark(long currentMark) {
		this.currentMark = currentMark;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
