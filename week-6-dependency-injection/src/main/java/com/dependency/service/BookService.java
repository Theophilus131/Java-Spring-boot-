package com.dependency.service;


import com.dependency.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;


   // field injection
 /*  @Autowired
   private BookRepository bookRepository1;



    setter injection
    @Autowired
public void setBookRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
}
*/


    //conttructor injection recommended
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String getBookData() {
        return bookRepository.getBookData();
    }
}
