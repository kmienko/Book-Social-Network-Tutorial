package com.kmienko.Book_Social_Network_Tutorial.book;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedBookResponse extends BookResponse {
    private boolean returned;
    private boolean returnedApproved;
}
