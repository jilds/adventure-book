package com.jilds.interview.adventurebook.controller;

import com.jilds.interview.adventurebook.model.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.model.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<BookResposeDTO>> getBooks(@RequestBody
                                                         @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                 content = @Content(
                                                                         mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                                         examples = @ExampleObject(
                                                                                 name = "Search Books Example",
                                                                                 value = """
                                                                                        {
                                                                                            "title": "The Great Gatsby",
                                                                                            "author": "F. Scott Fitzgerald",
                                                                                            "difficulty": "TESTE",
                                                                                            "category": "TESTE2",
                                                                                        }
                                                                                        """
                                                                         )
                                                                 )
                                                         )
                                                         Map<String, Object> filters) {
        var responseValue = bookService.searchBook(filters);
        return ResponseEntity.ok(responseValue);
    }

    @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResposeDTO> getBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok().build();
    }

}
