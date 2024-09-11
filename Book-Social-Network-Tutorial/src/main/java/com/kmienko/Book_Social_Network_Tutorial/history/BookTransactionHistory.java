package com.kmienko.Book_Social_Network_Tutorial.history;

import com.kmienko.Book_Social_Network_Tutorial.book.Book;
import com.kmienko.Book_Social_Network_Tutorial.common.BaseEntity;
import com.kmienko.Book_Social_Network_Tutorial.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookTransactionHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;



    private boolean returned;
    private boolean returnedApproved;
}
