package com.ng.springboot.testing;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;

@Service
@Setter
public class BookService {

	@Autowired
	BookRepository bookRepository;

	public List<Book> getAllBooks() {

		return bookRepository.findAll();
	}

	public Optional<Book> getBookById(int bookId) {

		return bookRepository.findById(bookId);
	}

	public Book createBook(Book book) {

		return bookRepository.save(book);
	}

	public Book updateBook(Book book) {

		return bookRepository.save(book);
	}

	public void deleteBook(int bookId) {

		bookRepository.deleteById(bookId);
	}
}
