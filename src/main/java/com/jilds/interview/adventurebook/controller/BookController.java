package com.jilds.interview.adventurebook.controller;

import com.jilds.interview.adventurebook.domain.dto.BookCriteriaDTO;
import com.jilds.interview.adventurebook.domain.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.domain.enums.Category;
import com.jilds.interview.adventurebook.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Book Controller", description = "Controller for managing books")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Create a new book, you know, like the one you want to read, but you don't have it yet")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResposeDTO> addBook(@RequestBody @Valid BookRequestDTO book) {
        BookResposeDTO bookResposeDTO = bookService.createBook(book);
        return new ResponseEntity<>(bookResposeDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Search for books based on various criteria to find that one book that matches your mood")
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResposeDTO>> getBooks(@RequestBody(required = false) BookCriteriaDTO filters) {
        var responseValue = bookService.searchBook(filters);
        return ResponseEntity.ok(responseValue);
    }

    @Operation(summary = "Get some details about a book, because you know, to know more about that book before you decide to read it or not")
    @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResposeDTO> getBookDetails(@PathVariable Integer bookId) {
        BookResposeDTO bookResposeDTO = bookService.getBookDetails(bookId);
        return ResponseEntity.ok(bookResposeDTO);
    }

    @Operation(summary = "Update book categories, only categories will be updated, other fields will be ignored")
    @PatchMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResposeDTO> patchBookCategories(@PathVariable Integer bookId, @RequestBody(required = false) List<Category> categories) {
        BookResposeDTO bookResposeDTO = bookService.patchBookCategory(bookId, categories);
        return ResponseEntity.ok(bookResposeDTO);
    }

    @Operation(summary = "Has no idea, load some demos to play and enjoy adventure")
    @GetMapping(value = "/load-demo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResposeDTO>> loadDemoBooks() {
        List<BookResposeDTO> bookResposeDTO = bookService.loadDemo();
        return new ResponseEntity<>(bookResposeDTO, HttpStatus.CREATED);
    }

}
