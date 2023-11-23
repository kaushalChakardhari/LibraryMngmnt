package com.example.LibraryMngmnt.controller;

import com.example.LibraryMngmnt.model.Book;
import com.example.LibraryMngmnt.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/getAllBooks")
    public ResponseEntity<?> getAllBooks() {
        try {
            List<Book> bookList = new ArrayList<>();
            bookRepository.findAll().forEach(bookList::add);  // retrieves all the books available in the bookRepository and collects them into the bookList.

            if (bookList.isEmpty()) {
                String msg="Currently there is no book in Library!";
                return new ResponseEntity<>(msg,HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<?> getBookById(@PathVariable UUID id) {
        Optional<Book> bookObj = bookRepository.findById(id); //used to avoid null pointer exceptions by explicitly indicating that a value might be absent
        if (bookObj.isPresent()) {

            return new ResponseEntity<>(bookObj.get(), HttpStatus.OK);
        } else {
            String msg="Book with id " +id+" is not there in library!";
            return new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        try {
            Book bookObj = bookRepository.save(book);
            return new ResponseEntity<>(bookObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@PathVariable UUID id, @RequestBody Book book) {
        try {
            Optional<Book> bookData = bookRepository.findById(id);
            if (bookData.isPresent()) {
                Book updatedBookData = bookData.get();

                              updatedBookData.setTitle(book.getTitle());
                              updatedBookData.setAuthor(book.getAuthor());

                Book bookObj = bookRepository.save(updatedBookData);
                return new ResponseEntity<>(bookObj, HttpStatus.CREATED);
            }
             String msg="Book with id " +id+" is not there in library!";
            return new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteBookById/{id}")
        public ResponseEntity<?> deleteBook(@PathVariable UUID id) {
        try {
            Optional<Book> bookData = bookRepository.findById(id);
            if(bookData.isPresent()) {
                bookRepository.deleteById(id);

                String msg = "Book with ID " + id + " is deleted successfully!";
                return new ResponseEntity<>(msg, HttpStatus.OK);
            }
            else{
                String msg = "There is no book with " + id;
                return new ResponseEntity<>(msg, HttpStatus.OK);

            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAllBooks")
    public ResponseEntity<?> deleteAllBooks() {
        try {
            bookRepository.deleteAll();
            String msg = "All books are deleted successfully!";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}