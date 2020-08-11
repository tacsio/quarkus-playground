package io.tacsio.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.tacsio.entity.Book;

@ApplicationScoped
public class BookService {
	
	@Inject
	private BookRemoteService remoteService;

	public void save(Book book) {

		double price = remoteService.searchPrice(book.getIsbn());
		book.setPrice(price);

		System.out.println("Book saved!");
	}
}