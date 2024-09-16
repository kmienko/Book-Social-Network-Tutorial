package com.kmienko.Book_Social_Network_Tutorial.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("""
            Select b from Book b
            where b.archived = false
            and b.shareable = true
            and b.owner.userId != :userId
            """)
    Page<Book> findAllShareableBooks(Pageable pageable, Integer userId);
}