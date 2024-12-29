package kr.kainos.book.book.service;

import java.util.List;
import kr.kainos.book.book.domain.Book;
import kr.kainos.book.book.domain.BookRequest;
import kr.kainos.book.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;

  public Book createNewBook(BookRequest bookRequest) {
    Book book = new Book();
    book.setIsbn(bookRequest.getIsbn());
    book.setAuthor(bookRequest.getAuthor());
    book.setTitle(bookRequest.getTitle());

    return bookRepository.save(book);
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }
}
