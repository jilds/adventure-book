package com.jilds.interview.adventurebook.controller;

import com.jilds.interview.adventurebook.model.dto.BookCriteriaDTO;
import com.jilds.interview.adventurebook.model.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.model.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.model.enums.Category;
import com.jilds.interview.adventurebook.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "Add a new book")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResposeDTO> addBook(@RequestBody @Valid BookRequestDTO book) {
        BookResposeDTO bookResposeDTO = bookService.createBook(book);
        return ResponseEntity.ok(bookResposeDTO);
    }

    @Operation(summary = "Get books based on criteria")
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResposeDTO>> getBooks(@RequestBody BookCriteriaDTO filters) {
        var responseValue = bookService.searchBook(filters);
        return ResponseEntity.ok(responseValue);
    }

    @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResposeDTO> getBookDetails(@PathVariable Long bookId) {
        BookResposeDTO bookResposeDTO = bookService.getBookDetails(bookId);
        return ResponseEntity.ok(bookResposeDTO);
    }

    @PatchMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResposeDTO> patchBookCategories(@PathVariable Long bookId, @RequestBody(required = false) Category category) {
        BookResposeDTO bookResposeDTO = bookService.patchBookCategory(bookId, category);
        return ResponseEntity.ok(bookResposeDTO);
    }

}
