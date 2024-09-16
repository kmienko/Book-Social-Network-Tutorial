package com.kmienko.Book_Social_Network_Tutorial.book;

import com.kmienko.Book_Social_Network_Tutorial.exception.OperationNotPermittedException;
import com.kmienko.Book_Social_Network_Tutorial.history.BookTransactionHistory;
import com.kmienko.Book_Social_Network_Tutorial.history.BookTransactionHistoryRepository;
import com.kmienko.Book_Social_Network_Tutorial.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookTransactionHistoryRepository transactionHistoryRepo;

    public Integer save(BookRequest request, Authentication connctedUser) {
        User user = (User) connctedUser.getPrincipal();
        Book book = bookMapper.toBook(request);
        book.setOwner(user);
        bookRepository.save(book);
        return bookRepository.save(book).getId();
    }

    public BookResponse findById(Integer id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookResponse)
                .orElseThrow(()-> new EntityNotFoundException("Book not found with id: " + id));
    }

    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllShareableBooks(pageable, user.getUserId());
        return buildPageBookResponse(books);
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(user.getUserId()),pageable);
        return buildPageBookResponse(books);
    }

    private PageResponse<BookResponse> buildPageBookResponse(Page<Book> books){
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse).toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    private PageResponse<BookHistoryResponse> buildPageBookHistoryResponse(Page<BookTransactionHistory> bookHistory){
        List<BookHistoryResponse> borrowedBookResponses = bookHistory.stream()
                .map(bookMapper::toBookHistoryResponse).toList();
        return new PageResponse<>(
                borrowedBookResponses,
                bookHistory.getNumber(),
                bookHistory.getSize(),
                bookHistory.getTotalElements(),
                bookHistory.getTotalPages(),
                bookHistory.isFirst(),
                bookHistory.isLast()
        );
    }

    public PageResponse<BookHistoryResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> borrowedBooks = transactionHistoryRepo.findAllBorrowedBooks(pageable, user.getUserId());
        return buildPageBookHistoryResponse(borrowedBooks);
    }

    public PageResponse<BookHistoryResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> returnedBooks = transactionHistoryRepo.findAllReturnedBooks(pageable, user.getUserId());
        return buildPageBookHistoryResponse(returnedBooks);
    }

    public Integer updateShareableStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID: "+bookId));
        User user = (User) connectedUser.getPrincipal();
        if(!Objects.equals(book.getOwner().getUserId(), user.getUserId())){
            throw new OperationNotPermittedException("You cannot update not your book");
        }
        book.setShareable(!book.isShareable());
        return bookId;
    }

    public Integer updateArchiveStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID: "+bookId));
        User user = (User) connectedUser.getPrincipal();
        if(!Objects.equals(book.getOwner().getUserId(), user.getUserId())){
            throw new OperationNotPermittedException("You cannot update not your book");
        }
        book.setArchived(!book.isArchived());
        return bookId;
    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID: "+bookId));
        if(book.isArchived() || !book.isShareable())
            throw new OperationNotPermittedException("You cannot borrow selected book");
        User user = (User) connectedUser.getPrincipal();
        if(Objects.equals(book.getOwner().getUserId(), user.getUserId()))
            throw new OperationNotPermittedException("You cannot borrow your own book");
        final boolean isAlreadyBorrowed = transactionHistoryRepo.isAlreadyBorrowedByUser(bookId, user.getUserId());
        if(isAlreadyBorrowed)
            throw new OperationNotPermittedException("Selected book is already borrowed");
        BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnedApproved(false)
                .build();
        return transactionHistoryRepo.save(bookTransactionHistory).getId();
    }
}
