package io.tacsio.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OddNumberExceptionHandler implements ExceptionMapper<OddNumberException> {

	@Override
	public Response toResponse(OddNumberException exception) {
		String msg = exception.getMessage();
		return Response.status(Status.BAD_REQUEST).entity(msg).build();
	}

}