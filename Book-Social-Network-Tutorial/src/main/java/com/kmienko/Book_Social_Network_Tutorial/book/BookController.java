package com.kmienko.Book_Social_Network_Tutorial.book;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private final BookService service;

    @PostMapping
    public ResponseEntity<Integer> saveBook(
            @Valid @RequestBody BookRequest request,
            Authentication connectedUser) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping //with paging
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBooks(page, size, connectedUser));
    }

    @GetMapping("/owner") //with paging
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBooksByOwner(page, size, connectedUser));
    }
    @GetMapping("/borrowed") //with paging
    public ResponseEntity<PageResponse<BookHistoryResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBorrowedBooks(page, size, connectedUser));
    }
    @GetMapping("/returned") //with paging
    public ResponseEntity<PageResponse<BookHistoryResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllReturnedBooks(page, size, connectedUser));
    }

    @PatchMapping("/shareable/{id}")
    public ResponseEntity<Integer> updateShareableStatus(
            @PathVariable Integer id,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updateShareableStatus(id, connectedUser));
    }

    @PatchMapping("/archived/{id}")
    public ResponseEntity<Integer> updateArchiveStatus(
            @PathVariable Integer id,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updateArchiveStatus(id, connectedUser));
    }

    @PostMapping("/borrow/{id}")
    public ResponseEntity<Integer> borrowBook(
            @PathVariable Integer id,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.borrowBook(id, connectedUser));
    }

    @PatchMapping("/borrow/return/{id}")
    public ResponseEntity<Integer> returnBorrowBook(
            @PathVariable Integer id,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.returnBorrowedBook(id, connectedUser));
    }

    @PatchMapping("/borrow/return/approve/{id}")
    public ResponseEntity<Integer> approveReturnBorrowBook(
            @PathVariable Integer id,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.approveReturnBorrowedBook(id, connectedUser));
    }

    @PostMapping(value = "/cover/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable Integer id,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ){
        service.uploadBookCoverPicture(id, connectedUser, file);
        return ResponseEntity.accepted().build();
    }
}
