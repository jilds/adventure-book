package com.jilds.interview.adventurebook.service.impl;

import com.jilds.interview.adventurebook.exception.AdventureBookException;
import com.jilds.interview.adventurebook.model.dto.BookCriteriaDTO;
import com.jilds.interview.adventurebook.model.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.model.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.model.enums.Category;
import com.jilds.interview.adventurebook.model.mapper.BookMapper;
import com.jilds.interview.adventurebook.model.specification.GenericSpecification;
import com.jilds.interview.adventurebook.repository.BookRepository;
import com.jilds.interview.adventurebook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResposeDTO createBook(BookRequestDTO bookRequestDTO) {
        bookRequestDTO.isValid();

        var bookEntity = BookMapper.INSTANCE.toBookEntity(bookRequestDTO);
        var savedBookEntity = bookRepository.save(bookEntity);
        return BookMapper.INSTANCE.toBookDTO(savedBookEntity);
    }

    @Override
    public List<BookResposeDTO> searchBook(BookCriteriaDTO filters) {
        var bookEntities = bookRepository.findAll(new GenericSpecification<>(filters.getCriteriaDTO()));
        var bookDTOs = BookMapper.INSTANCE.toBookDTOList(bookEntities);
        if (ObjectUtils.isEmpty(bookDTOs)) {
            return List.of();
        }
        return bookDTOs;
    }

    @Override
    public BookResposeDTO getBookDetails(Long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new AdventureBookException("Book not found with id: " + bookId, HttpStatus.NOT_FOUND));

        return BookMapper.INSTANCE.toBookDTO(book);
    }

    @Override
    public BookResposeDTO patchBookCategory(Long bookId, Category category) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new AdventureBookException("Book not found with id: " + bookId, HttpStatus.NOT_FOUND));
        book.setCategory(category);
        var updatedBook = bookRepository.save(book);
        return BookMapper.INSTANCE.toBookDTO(updatedBook);
    }
}
