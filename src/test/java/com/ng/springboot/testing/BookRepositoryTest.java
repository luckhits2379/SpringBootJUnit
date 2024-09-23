package com.ng.springboot.testing;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@EntityScan(basePackages = "com.ng.springboot.testing")
class BookRepositoryTest {

	@Autowired
	BookRepository bookRepository;

	static Book book1, book2, book3, book4;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		book1 = Book.builder().bookId(1).bookName("Book1").build();
		book2 = Book.builder().bookId(2).bookName("Book2").build();
		book3 = Book.builder().bookId(3).bookName("Book3").build();
		book4 = Book.builder().bookId(4).bookName("Book4").build();
	}

	@Test
	public void bookRepository_save_success() {

		// Act
		Book savedbook = bookRepository.save(book1);

		// Assert
		Assertions.assertThat(savedbook).isNotNull();
		Assertions.assertThat(savedbook.getBookName()).isEqualTo("Book1");
		Assertions.assertThat(savedbook.getBookId()).isGreaterThan(0);
	}

	@Test
	public void bookRepository_findById_success() {

		// Arrange
		bookRepository.save(book1);

		// Act
		Optional<Book> optionalBook = bookRepository.findById(1);

		// Assert
		Assertions.assertThat(optionalBook).isNotEmpty();
		Assertions.assertThat(optionalBook.get().getBookName()).isEqualTo("Book1");
		Assertions.assertThat(optionalBook.get().getBookId()).isEqualTo(1);
	}

	@Test
	public void bookRepository_getAllBooks_success() {

		// Arrange
		bookRepository.save(book1);
		bookRepository.save(book2);
		bookRepository.save(book3);
		bookRepository.save(book4);

		// Act
		List<Book> bookList = bookRepository.findAll();

		// Assert
		Assertions.assertThat(bookList).isNotEmpty();
		Assertions.assertThat(bookList.size()).isEqualTo(4);

	}

	@Test
	public void bookRepository_updateBook_success() {

		// Arrange
		bookRepository.save(book3);

		Optional<Book> bookToUpdate = bookRepository.findById(3);
		Assertions.assertThat(bookToUpdate).isNotEmpty();

		bookToUpdate.get().setBookName("updatedBook3");

		// Act
		Book savedbook = bookRepository.save(bookToUpdate.get());

		// Assert
		Assertions.assertThat(savedbook).isNotNull();
		Assertions.assertThat(savedbook.getBookId()).isEqualTo(3);
		Assertions.assertThat(savedbook.getBookName()).isEqualTo("updatedBook3");

	}

	@Test
	public void bookRepository_deleteBook_success() {

		// Arrange
		bookRepository.save(book4);

		// Act
		bookRepository.deleteById(4);
		Optional<Book> optionalBook = bookRepository.findById(4);

		// Assert
		Assertions.assertThat(optionalBook).isEmpty();

	}

}
