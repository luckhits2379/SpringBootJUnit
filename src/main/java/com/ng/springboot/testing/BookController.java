package com.ng.springboot.testing;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBook(@PathVariable int id) {

		Optional<Book> optionalBook = bookService.getBookById(id);

		if (optionalBook.isEmpty()) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(optionalBook.get());

	}

	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {

		List<Book> bookList = bookService.getAllBooks();

		if (bookList.isEmpty()) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(bookList);

	}

	@PostMapping
	public ResponseEntity<Book> saveBook(@Valid @RequestBody Book book, BindingResult result) {

		if (result.hasErrors()) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		book = bookService.createBook(book);

		return ResponseEntity.status(HttpStatus.CREATED).body(book);

	}

	@PutMapping
	public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book, BindingResult result) {

		if (result.hasErrors()) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (book.getBookId() < 1) {

			return ResponseEntity.notFound().build();
		}

		Optional<Book> optionalBook = bookService.getBookById(book.getBookId());

		if (optionalBook.isEmpty()) {

			return ResponseEntity.notFound().build();
		}

		optionalBook.get().setBookName(book.getBookName());

		Book updatedBook = bookService.updateBook(optionalBook.get());

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedBook);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable int id) {

		if (id < 1) {

			return ResponseEntity.notFound().build();
		}

		Optional<Book> optionalBook = bookService.getBookById(id);

		if (optionalBook.isEmpty()) {

			return ResponseEntity.notFound().build();
		}

		bookService.deleteBook(id);

		return ResponseEntity.status(HttpStatus.ACCEPTED).build();

	}

}
