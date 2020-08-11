package io.tacsio.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookRemoteService {

	/**
	 * suppose this is a service that remotely validates the book data
	 * 
	 * @param book
	 * @return
	 */
	public boolean remoteValidation(String isbn) {
		return isbn != null && !isbn.isEmpty() && !isbn.equals("invalid");
	}

	public double searchPrice(String isbn) {
		//do stuff
		return 50;
	}
}