package kr.kainos.book.book.controller;

import jakarta.validation.Valid;
import java.net.URI;
import kr.kainos.book.book.domain.BookRequest;
import kr.kainos.book.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }
  @PostMapping
  public ResponseEntity<Void> createBook(@Valid @RequestBody BookRequest bookRequest) {
    Long bookId = bookService.createNewBook(bookRequest);

    return ResponseEntity
        .created(URI.create("/api/books/" + bookId))
        .build();
  }
}
