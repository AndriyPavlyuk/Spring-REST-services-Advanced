package it.discovery.exception;

public class BookNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 3413812624267549804L;

	public BookNotFoundException(int bookId) {
        super("Book " + bookId + " not found");
    }
}
