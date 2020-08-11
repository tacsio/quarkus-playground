package io.tacsio.api;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.tacsio.api.dto.BookForm;
import io.tacsio.api.dto.BookResponse;
import io.tacsio.entity.Book;
import io.tacsio.service.BookService;

@Path("/books")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    private BookService bookService;

    @POST
    public Response create(@Valid BookForm form) {
        Book book = form.toModel();
        bookService.save(book);

        return Response
            .status(Status.CREATED)
            .entity(new BookResponse(book))
            .build();
    }

}