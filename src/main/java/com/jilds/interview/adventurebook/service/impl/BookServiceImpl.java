package com.jilds.interview.adventurebook.service.impl;

import com.jilds.interview.adventurebook.domain.dto.BookCriteriaDTO;
import com.jilds.interview.adventurebook.domain.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.domain.enums.Category;
import com.jilds.interview.adventurebook.domain.mapper.BookMapper;
import com.jilds.interview.adventurebook.domain.specification.GenericSpecification;
import com.jilds.interview.adventurebook.exception.AdventureBookException;
import com.jilds.interview.adventurebook.repository.BookRepository;
import com.jilds.interview.adventurebook.service.BookService;
import com.jilds.interview.adventurebook.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final SectionService sectionService;

    @Override
    @Transactional
    public BookResposeDTO createBook(BookRequestDTO bookRequestDTO) {
        bookRequestDTO.isValid();

        var bookEntity = bookMapper.toBookEntity(bookRequestDTO);

        var savedBookEntity = bookRepository.save(bookEntity);

        sectionService.createSections(bookEntity, bookRequestDTO.getSections());

        return bookMapper.toBookDTO(savedBookEntity);
    }

    @Override
    public List<BookResposeDTO> searchBook(BookCriteriaDTO filters) {
        var bookEntities = ObjectUtils.isEmpty(filters) ?
                bookRepository.findAll() :
                bookRepository.findAll(new GenericSpecification<>(filters.getCriteriaDTO()));

        var bookDTOs = bookMapper.toBookDTOList(bookEntities);

        return ObjectUtils.isEmpty(bookDTOs) ? List.of() : bookDTOs;
    }

    @Override
    public BookResposeDTO getBookDetails(Integer bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new AdventureBookException("Book not found with id: " + bookId, HttpStatus.NOT_FOUND));

        return bookMapper.toBookDTO(book);
    }

    @Override
    public BookResposeDTO patchBookCategory(Integer bookId, List<Category> categories) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new AdventureBookException("Book not found with id: " + bookId, HttpStatus.NOT_FOUND));
        book.updateCategories(categories);
        var updatedBook = bookRepository.save(book);
        return bookMapper.toBookDTO(updatedBook);
    }

    @Override
    public List<BookResposeDTO> loadDemo() {


        return List.of();
    }
}
