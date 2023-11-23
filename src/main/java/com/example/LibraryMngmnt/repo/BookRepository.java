package com.example.LibraryMngmnt.repo;

import com.example.LibraryMngmnt.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findById(UUID id);   //created using jpa repository to provide bydefault functionalities

}
