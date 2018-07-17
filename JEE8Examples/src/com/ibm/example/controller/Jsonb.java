package com.ibm.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyOrderStrategy;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.example.jsonb.Book;

@WebServlet("/jsonb")
public class Jsonb extends HttpServlet {
	private static final long serialVersionUID = 6420793735294842651L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		// null book first
		Book book = new Book();
		String bookJson = JsonbBuilder.create().toJson(book);
		out.println("Book (null): " + bookJson);
		
		// book with data
		List<String> authors = new ArrayList<String>();
		authors.add("Didrik Søderlind");
		authors.add("Michael Moynihan");
		book.setAuthor(authors);
		book.setTitle("Lords of Chaos");
		book.setISBN("0-922915-48-2");
		book.setCost(new BigDecimal(18.95));
		LocalDate ld = LocalDate.of(1998, 11, 1);
		book.setDate(ld);
		bookJson = JsonbBuilder.create().toJson(book);
		out.println("Book (single): " + bookJson);
		
		// using config, will only work if class is not annotated.
		JsonbConfig config = new JsonbConfig().withPropertyOrderStrategy(PropertyOrderStrategy.REVERSE);
		bookJson = JsonbBuilder.create(config).toJson(book);
		out.println("Book (config): " + bookJson);
		
		book = new Book("Lords of Chaos");
		book.setAuthor(authors);
		book.setISBN("0-922915-48-2");
		book.setCost(new BigDecimal(18.95));
		book.setDate(ld);
		bookJson = JsonbBuilder.create().toJson(book);
		out.println("Book (creator): " + bookJson);
		
		// JSON to Object
		out.println();
		out.println("From JSON to Object");
		config = new JsonbConfig().withDateFormat("dd.MM.yyyy", null);
		book = JsonbBuilder.create(config).fromJson(bookJson, Book.class);
		out.println("Title: " + book.getTitle());
		for (int i = 0; i < book.getAuthor().size(); i++) {
			out.println("Author: " + book.getAuthor().get(i));
		}
		out.println("Date: " + book.getDate());
		out.println("Cost: " + book.getCost());
		out.println("ISBN: " + book.getISBN());
	}
}
