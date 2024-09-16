package com.kmienko.Book_Social_Network_Tutorial.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer> {
  @Query("Select history from BookTransactionHistory history where history.user.userId = :userId")
  Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);

  @Query("""
          Select history
          from BookTransactionHistory history
          where history.book.owner.userId = :userId
          """)//Java 15 multi line String
  Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);

  @Query("""
          select (COUNT(*) > 0) AS isBorrowed
          from BookTransactionHistory history
          where history.user.userId = :userId
          and history.book.id = :bookId
          and history.returnedApproved = false
          """)
  boolean isAlreadyBorrowedByUser(Integer bookId, Integer userId);
}