package com.dependency.repository;


import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

   public String getBookData() {
       return "Book Data";
   }
}
