package io.tacsio.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<IllegalArgumentException> {

	@Override
	public Response toResponse(IllegalArgumentException exception) {
		String msg = "I don't like handle these *o*";
		return Response.status(Status.BAD_REQUEST).entity(msg).build();
	}

}