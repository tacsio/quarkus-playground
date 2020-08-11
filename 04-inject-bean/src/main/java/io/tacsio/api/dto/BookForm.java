package io.tacsio.api.dto;

import javax.enterprise.inject.spi.CDI;
import javax.validation.constraints.NotBlank;

import io.tacsio.entity.Book;
import io.tacsio.service.BookRemoteService;

public class BookForm {
	@NotBlank
	public String title;

	@NotBlank
	public String author;

	@NotBlank
	public String isbn;

	public Book toModel() {
		BookRemoteService service = CDI.current().select(BookRemoteService.class).get();
		
		if (!service.remoteValidation(this.isbn)) {
			throw new IllegalArgumentException();
		}

		return new Book(title, author, isbn);
	}

}