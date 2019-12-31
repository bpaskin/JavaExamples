package com.ibm.rest.model;

import java.io.Serializable;

public class RandomResponse implements Serializable {
	private static final long serialVersionUID = 1404955328902273329L;
	private int randomNumber;
	private boolean isMoonFull;
	public int getRandomNumber() {
		return randomNumber;
	}
	public void setRandomNumber(int randomNumber) {
		this.randomNumber = randomNumber;
	}
	public boolean isMoonFull() {
		return isMoonFull;
	}
	public void setMoonFull(boolean isMoonFull) {
		this.isMoonFull = isMoonFull;
	}
	
}
