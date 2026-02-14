package com.jilds.interview.adventurebook.service.impl;

import com.jilds.interview.adventurebook.domain.dto.BookCriteriaDTO;
import com.jilds.interview.adventurebook.domain.dto.BookDTO;
import com.jilds.interview.adventurebook.domain.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.domain.entity.BookEntity;
import com.jilds.interview.adventurebook.domain.enums.Category;
import com.jilds.interview.adventurebook.domain.mapper.BookMapper;
import com.jilds.interview.adventurebook.domain.specification.GenericSpecification;
import com.jilds.interview.adventurebook.exception.AdventureBookException;
import com.jilds.interview.adventurebook.repository.BookRepository;
import com.jilds.interview.adventurebook.service.BookService;
import com.jilds.interview.adventurebook.service.SectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final SectionService sectionService;
    private final ObjectMapper objectMapper;

    @Value("classpath*:demo/*.json")
    private Resource[] jsonResources;

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
        List<BookResposeDTO> bookDTOs = new ArrayList<>();
        for (Resource res : jsonResources) {
            try {
                BookRequestDTO bookDTO = objectMapper.readValue(res.getInputStream(), BookRequestDTO.class);
                var newBook = this.createBook(bookDTO);
                bookDTOs.add(newBook);
            } catch (IOException ioe) {
                log.error(ioe.getMessage());
                throw new AdventureBookException(ioe.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return bookDTOs;
    }
}
