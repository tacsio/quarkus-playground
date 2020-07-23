package io.tacsio;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.core.HttpHeaders;

@Path("/books")
public class BookResource {

	@Inject
	Validator validator;

	@Inject
	BookService bookService;

	@Inject
	MessageHelper messages;

	@Path("/manual-validation")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Result manualValidation(Book book, @Context HttpHeaders headers) {

		Set<ConstraintViolation<Book>> violations = validator.validate(book);

		if (violations.isEmpty()) {
			return new Result(messages.getMessage("BookResource.manual.valid", headers));
		} else {
			return new Result(violations);
		}
	}

	@Path("/endpoint-validation")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Result endpointValidation(@Valid Book book) {
		return new Result("Book is valid! It was validated by endpoint validation.");
	}

	@Path("/service-validation")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Result serviceMethodValidation(Book book) {

		try {
			bookService.validateBook(book);
			return new Result("Book is valid! It was validated by service validation.");
		} catch (ConstraintViolationException e) {
			return new Result(e.getConstraintViolations());
		}
	}
}