package com.ibm.example.jsonb;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbNumberFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbNillable
//@JsonbPropertyOrder({"title", "author", "date", "cost", "isbn"})
public class Book {

	@JsonbProperty(value="book title",nillable=true)
	private String title;
	@JsonbProperty(nillable=true)
	private List<String> author;
	@JsonbNumberFormat("#00.00")
	@JsonbProperty(nillable=false)
	private BigDecimal cost;
	@JsonbProperty(value="release date",nillable=false)
	@JsonbDateFormat(value="dd.MM.yyyy",locale="America/New York")
	private LocalDate date;
	@JsonbProperty(value="isbn",nillable=false)
	private String ISBN;
	
	public Book() {
	}
	
	@JsonbCreator
    public Book(@JsonbProperty("title") String title) {
        this.title = title;
    }
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getAuthor() {
		return author;
	}
	public void setAuthor(List<String> author) {
		this.author = author;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
}
