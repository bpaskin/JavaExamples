package com.ibm.example.jaxrs.validation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.example.validation.BookInfo;

@Path("book")
public class BookService {

	@POST
	@Path("get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @NotNull BookInfo getBook(@NotBlank String title) {
		return null;
	}
}
