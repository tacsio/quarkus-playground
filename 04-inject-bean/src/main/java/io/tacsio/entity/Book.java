package io.tacsio.entity;

public class Book {

	private String title;

	private String author;
	
	private String isbn;
	
	private Double price;

	public Book(String title, String author, String isbn) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
	}

	@Override
	public String toString() {
		return "Book [author=" + author + ", isbn=" + isbn + ", price=" + price + ", title=" + title + "]";
	}

	public String getTitle() {
		return title;
	}

	public Double getPrice() {
		return price;
	}

	public String getAuthor() {
		return author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}