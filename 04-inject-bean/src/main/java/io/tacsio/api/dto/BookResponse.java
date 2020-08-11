package io.tacsio.api.dto;

import io.tacsio.entity.Book;

public class BookResponse {

	private String response;

	public BookResponse(Book book) {
		response = String.format("Book: %s of %s. Price: $ %s", book.getTitle(), book.getAuthor(), book.getPrice());
	}

	@Override
	public String toString() {
		return response;
	}

}