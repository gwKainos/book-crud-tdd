package kr.kainos.book.book.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Book")
@Getter
@Setter
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "isbn", nullable = false, unique = true)
  private String isbn;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "author", nullable = false)
  private String author;
}