package com.jilds.interview.adventurebook.controller;

import com.jilds.interview.adventurebook.model.entity.BookEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Book Controller", description = "Controller for managing books")
public class BookController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookEntity> addbook(@RequestBody BookEntity book) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookEntity>> getBooks(@RequestBody Object criteria) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok().build();
    }

}
