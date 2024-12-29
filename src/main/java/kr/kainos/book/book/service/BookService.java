package kr.kainos.book.book.service;

import java.util.List;
import java.util.Optional;
import kr.kainos.book.book.domain.Book;
import kr.kainos.book.book.domain.BookRequest;
import kr.kainos.book.book.repository.BookRepository;
import kr.kainos.book.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public Book getBookById(Long id) {
    Optional<Book> requestedBook = bookRepository.findById(id);

    if(requestedBook.isEmpty()){
      throw new BookNotFoundException(String.format("Book with id: '%s' not found", id));
    }

    return requestedBook.get();
  }

  @Transactional
  public Book updateBook(long id, BookRequest bookRequest) {
    Optional<Book> bookFromDatabase = bookRepository.findById(id);

    if (bookFromDatabase.isEmpty()) {
      throw new BookNotFoundException(String.format("Book with id: '%s' not found", id));
    }

    Book bookToUpdate = bookFromDatabase.get();

    bookToUpdate.setAuthor(bookRequest.getAuthor());
    bookToUpdate.setIsbn(bookRequest.getIsbn());
    bookToUpdate.setTitle(bookRequest.getTitle());

    return bookToUpdate;
  }
}
