package kr.kainos.book.initializer;

import kr.kainos.book.book.domain.Book;
import kr.kainos.book.book.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Slf4j
@Component
public class BookInitializer implements CommandLineRunner {

  private final BookRepository bookRepository;

  public BookInitializer(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    Faker faker = new Faker();

    log.info("Start initialization");

    for (int i = 0; i < 10; i++) {

      Book book = new Book();
      book.setTitle(faker.book().title());
      book.setAuthor(faker.book().author());
      book.setIsbn(UUID.randomUUID().toString());

      bookRepository.save(book);
    }

    log.info("Finish initialization");
  }
}