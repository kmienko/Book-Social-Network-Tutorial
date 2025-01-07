package com.kmienko.Book_Social_Network_Tutorial.feedback;

import com.kmienko.Book_Social_Network_Tutorial.book.Book;
import org.springframework.stereotype.Service;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequest request){
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(Book.builder().id(request.bookId()).build())
                .build();
    }
}
