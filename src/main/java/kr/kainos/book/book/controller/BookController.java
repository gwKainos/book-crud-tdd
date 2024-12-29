package kr.kainos.book.book.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import kr.kainos.book.book.domain.Book;
import kr.kainos.book.book.domain.BookRequest;
import kr.kainos.book.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequest bookRequest) {
    Book book = bookService.createNewBook(bookRequest);

    return ResponseEntity
        .created(URI.create("/api/books/" + book.getId()))
        .build();
  }

  @GetMapping
  public ResponseEntity<List<Book>> getAllBooks() {
    List<Book> books = bookService.getAllBooks();
    return ResponseEntity.ok(books);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
    Book book = bookService.getBookById(id);
    if (book == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(book);

//    return bookService.getBookById(id);
  }
}
