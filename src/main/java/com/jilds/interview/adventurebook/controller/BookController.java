package com.jilds.interview.adventurebook.controller;

import com.jilds.interview.adventurebook.model.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.model.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResposeDTO> addbook(@RequestBody BookRequestDTO book) {
        BookResposeDTO bookResposeDTO = bookService.createBook(book);
        return ResponseEntity.ok(bookResposeDTO);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResposeDTO>> getBooks(@RequestBody Object criteria) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResposeDTO> getBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok().build();
    }

}
