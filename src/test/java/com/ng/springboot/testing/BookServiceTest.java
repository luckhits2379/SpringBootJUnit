package com.ng.springboot.testing;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	BookRepository bookRepository;

	@InjectMocks
	BookService bookService;

	@Test
	public void createBook_success() {

		Book book = Book.builder().bookId(1).bookName("book1").build();

		when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

		Book savedBook = bookService.createBook(book);

		Assertions.assertThat(savedBook).isNotNull();
		Assertions.assertThat(savedBook.getBookId()).isEqualTo(1);
		Assertions.assertThat(savedBook.getBookName()).isEqualTo("book1");

	}

	@Test
	public void getAllBooks_success() {

		@SuppressWarnings("unchecked")
		List<Book> allBookList = Mockito.mock(List.class);

		when(bookRepository.findAll()).thenReturn(allBookList);

		List<Book> returnedBookList = bookService.getAllBooks();

		Assertions.assertThat(returnedBookList).isNotNull();
		Assertions.assertThat(returnedBookList).isEqualTo(returnedBookList);

	}

	@Test
	public void getBookById_success() {

		Book book = Book.builder().bookId(1).bookName("book1").build();

		when(bookRepository.findById(1)).thenReturn(Optional.of(book));

		Optional<Book> OptionalBook = bookService.getBookById(1);

		Assertions.assertThat(OptionalBook).isNotEmpty();
		Assertions.assertThat(OptionalBook.get().getBookId()).isEqualTo(1);
		Assertions.assertThat(OptionalBook.get().getBookName()).isEqualTo("book1");

	}

	@Test
	public void updateBook_success() {

		Book book = Book.builder().bookId(1).bookName("book1").build();

		when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

		Book updatedBook = bookService.updateBook(book);

		Assertions.assertThat(updatedBook).isNotNull();
		Assertions.assertThat(updatedBook.getBookId()).isEqualTo(1);
		Assertions.assertThat(updatedBook.getBookName()).isEqualTo("book1");

	}

	@Test
	public void deleteBook_success() {

		Book book = Book.builder().bookId(1).bookName("book1").build();

		// doNothing().when(bookRepository).delete(book);

		assertAll(() -> bookService.deleteBook(book.getBookId()));

	}

}
