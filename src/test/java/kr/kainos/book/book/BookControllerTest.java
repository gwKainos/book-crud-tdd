package kr.kainos.book.book;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.kainos.book.book.controller.BookController;
import kr.kainos.book.book.domain.Book;
import kr.kainos.book.book.domain.BookRequest;
import kr.kainos.book.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@Import(BookControllerTest.MockConfig.class)  // Import로 테스트 설정 주입
public class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private BookService bookService;

  @Captor
  private ArgumentCaptor<BookRequest> bookRequestCaptor;

  @TestConfiguration
  static class MockConfig {
    @Bean
    public BookService bookService() {
      return Mockito.mock(BookService.class);
    }
  }

  @Test
  public void API_책_생성() throws Exception {
    BookRequest bookRequest = new BookRequest();
    bookRequest.setAuthor("이동원");
    bookRequest.setIsbn("9791193939192");
    bookRequest.setTitle("찬란한 선택");

    Book book = new Book();
    book.setId(1L);
    book.setAuthor(bookRequest.getAuthor());
    book.setIsbn(bookRequest.getIsbn());
    book.setTitle(bookRequest.getTitle());

    when(bookService.createNewBook(bookRequestCaptor.capture())).thenReturn(book);

    this.mockMvc
        .perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookRequest)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", "/api/books/1"));

    assertThat(bookRequestCaptor.getValue().getAuthor(), is("이동원"));
    assertThat(bookRequestCaptor.getValue().getIsbn(), is("9791193939192"));
    assertThat(bookRequestCaptor.getValue().getTitle(), is("찬란한 선택"));
  }

  @Test
  public void API_책_리스트() throws Exception {
    when(bookService.getAllBooks()).thenReturn(List.of(
        createBook(1L, "천국에서 온 탐정", "이동원", "9791138586863"),
        createBook(2L, "완벽한 인생", "이동원", "9791158090630"),
        createBook(3L, "살고 싶다", "이동원", "9791195260201")));


    this.mockMvc
        .perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].title", is("천국에서 온 탐정")))
        .andExpect(jsonPath("$[0].author", is("이동원")))
        .andExpect(jsonPath("$[0].isbn", is("9791138586863")))
        .andExpect(jsonPath("$[0].id", is(1)));
  }

  private Book createBook(Long id, String title, String author, String isbn) {
    Book book = new Book();
    book.setAuthor(author);
    book.setIsbn(isbn);
    book.setTitle(title);
    book.setId(id);
    return book;
  }
}
