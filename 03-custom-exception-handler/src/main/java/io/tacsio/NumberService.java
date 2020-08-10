package io.tacsio;

import javax.enterprise.context.ApplicationScoped;

import io.tacsio.exceptions.OddNumberException;

@ApplicationScoped
public class NumberService {

	public String someBusinessLogic(Integer id) {

		if(id % 5 == 0) {
			throw new IllegalArgumentException();
		}

		if (id % 2 != 0) {
			throw new OddNumberException();
		}

		return "Yay!";
	}
}