package com.jilds.interview.adventurebook.service.impl;

import com.jilds.interview.adventurebook.model.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.model.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.model.mapper.BookMapper;
import com.jilds.interview.adventurebook.model.specification.GenericSpecification;
import com.jilds.interview.adventurebook.repository.BookRepository;
import com.jilds.interview.adventurebook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

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
    public List<BookResposeDTO> searchBook(Map<String, Object> filters) {
        var bookEntities = bookRepository.findAll(new GenericSpecification<>(filters));
        var bookDTOs = BookMapper.INSTANCE.toBookDTOList(bookEntities);
        if (ObjectUtils.isEmpty(bookDTOs)) {
            return List.of();
        }
        return bookDTOs;
    }
}
