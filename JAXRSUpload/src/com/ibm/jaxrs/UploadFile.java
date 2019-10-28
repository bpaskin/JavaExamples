package com.ibm.jaxrs;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("upload")
public class UploadFile {

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("file")
	public Response upload (@FormParam("file") File file, @FormParam("name") String name) {
		System.out.println("Name:" + name);
		System.out.println("FileName:" + file.getName());
		return Response.ok().build();
	}
}
