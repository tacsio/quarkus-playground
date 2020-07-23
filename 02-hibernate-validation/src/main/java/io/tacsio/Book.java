
package io.tacsio;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Book {

	@NotBlank(message = "Title may not be blank")
	public String title;

	@NotBlank(message = "Author may not be blank")
	public String author;

	@Min(message = "{Book.min}", value = 1)
	public double pages;

}