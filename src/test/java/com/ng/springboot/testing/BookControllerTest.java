package com.ng.springboot.testing;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	BookService bookService;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	public void init() throws Exception {
	}

	@Test //@formatter:off
	void testGetBook() throws Exception {

		Book book = Book.builder().bookId(1).bookName("book1").build();

		when(bookService.getBookById(1)).thenReturn(Optional.of(book));

		ResultActions response = mockMvc.perform(get("/book/1")).andDo(MockMvcResultHandlers.print()); // this will print req res
																										
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookId", CoreMatchers.is(book.getBookId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookName", CoreMatchers.is(book.getBookName())));

	}

	@Test //@formatter:Off
	void testGetAllBooks() throws Exception {

		List<Book> bookList = List.of(new Book(1, "book1"), new Book(2, "book2"));

		when(bookService.getAllBooks()).thenReturn(bookList);

		ResultActions response = mockMvc.perform(
												get("/book")
												).andDo(MockMvcResultHandlers.print()); // this will print
																										

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(bookList.size())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].bookId", CoreMatchers.is(bookList.get(0).getBookId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].bookName", CoreMatchers.is(bookList.get(0).getBookName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].bookId", CoreMatchers.is(bookList.get(1).getBookId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].bookName", CoreMatchers.is(bookList.get(1).getBookName())));

	}

	@Test
	void testSaveBook() throws Exception {

		Book book = Book.builder().bookId(1).bookName("book1").build();

		when(bookService.createBook(Mockito.any(Book.class))).thenReturn(book);

		ResultActions response = mockMvc.perform(
	                                             post("/book")
	                                             .contentType(MediaType.APPLICATION_JSON)
	                                             .content(objectMapper.writeValueAsString(book))
	                             				).andDo(MockMvcResultHandlers.print()); // this will print object
		
		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookId", CoreMatchers.is(book.getBookId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookName", CoreMatchers.is(book.getBookName())));

	}

	@Test
	void testUpdateBook() throws JsonProcessingException, Exception {

		Book book = Book.builder().bookId(1).bookName("book1").build();

		when(bookService.getBookById(1)).thenReturn(Optional.of(book));

		when(bookService.updateBook(Mockito.any(Book.class))).thenReturn(book);

		ResultActions response = mockMvc.perform(
												put("/book")
												.contentType(MediaType.APPLICATION_JSON)
												.content(objectMapper.writeValueAsString(book))
												).andDo(MockMvcResultHandlers.print()); // this will print object

		response.andExpect(MockMvcResultMatchers.status().isAccepted())
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookId", CoreMatchers.is(book.getBookId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookName", CoreMatchers.is(book.getBookName())));
	}

	@Test
	void testDeleteBook() throws Exception {

		Book book = Book.builder().bookId(1).bookName("book1").build();

		when(bookService.getBookById(1)).thenReturn(Optional.of(book));

		doNothing().when(bookService).deleteBook(1);

		ResultActions response = mockMvc.perform(
												delete("/book/1")
												).andDo(MockMvcResultHandlers.print()); // this will print object

		response.andExpect(MockMvcResultMatchers.status().isAccepted());

	}

}
