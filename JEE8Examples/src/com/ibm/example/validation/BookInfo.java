package com.ibm.example.validation;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;

public class BookInfo {
	
	@NotBlank(message="a valid title is required")
	private String title;
	
	@Email(message="email address must be valid")
	private String email;
	
	@PastOrPresent(message="date must not be in the future")
	private LocalDate releaseDate;

	@PositiveOrZero(message="number of copies cannot be negative")
	private int copiesSold;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getCopiesSold() {
		return copiesSold;
	}

	public void setCopiesSold(int copiesSold) {
		this.copiesSold = copiesSold;
	}
	
}
