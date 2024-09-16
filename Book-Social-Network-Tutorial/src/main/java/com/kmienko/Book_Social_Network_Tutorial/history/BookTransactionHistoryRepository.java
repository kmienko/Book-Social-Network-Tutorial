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
}