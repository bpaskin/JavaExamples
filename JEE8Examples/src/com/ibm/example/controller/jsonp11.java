package com.ibm.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPatchBuilder;
import javax.json.JsonPointer;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.example.jsonb.Book;

@WebServlet("/jsonp")
public class jsonp11 extends HttpServlet {
	private static final long serialVersionUID = 6922981066498585010L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		Book book = new Book();
		List<String> authors = new ArrayList<String>();
		authors.add("Didrik Søderlind");
		book.setAuthor(authors);
		book.setTitle("Lords of Chaos");
		book.setISBN("0-922915-48-2");
		book.setCost(new BigDecimal(18.95));
		LocalDate ld = LocalDate.of(1998, 11, 1);
		book.setDate(ld);
		String bookJson = JsonbBuilder.create().toJson(book);
		
		out.println("JSON before: " + bookJson);	
		out.println("");
		out.println("Pointer:");
		JsonObject jsonObject = Json.createReader(new StringReader(bookJson)).readObject();
		JsonPointer pointer = Json.createPointer("/author/0");
		jsonObject = pointer.add(jsonObject, Json.createValue("Brian Paskin"));
		out.println("JSON add: " + jsonObject.toString());	
		
		pointer = Json.createPointer("/author/0");
		jsonObject = pointer.replace(jsonObject, Json.createValue("Michael Moynihan"));
		out.println("JSON replace: " + jsonObject.toString());	
		
		out.println("");
		out.println("Patch:");
		
		JsonPatchBuilder patch = Json.createPatchBuilder();
		jsonObject = patch.move("/author/1", "/author/0").replace("/book title", "Lords of Chaos: The Bloody Rise of the Satanic Metal Underground").build().apply(jsonObject);

		out.println(jsonObject.toString());
		out.println("");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
