package io.tacsio;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

@ApplicationScoped
public class BookService {
	
	public void validateBook(@Valid Book book) {
		//some business logic
	}
}