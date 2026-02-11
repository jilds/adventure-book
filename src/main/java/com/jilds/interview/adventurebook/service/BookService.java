package com.jilds.interview.adventurebook.service;

import com.jilds.interview.adventurebook.model.dto.BookCriteriaDTO;
import com.jilds.interview.adventurebook.model.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.model.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.model.enums.Category;

import java.util.List;

public interface BookService {

    BookResposeDTO createBook(BookRequestDTO bookRequestDTO);

    List<BookResposeDTO> searchBook(BookCriteriaDTO filters);

    BookResposeDTO getBookDetails(Long bookId);

    BookResposeDTO patchBookCategory(Long bookId, Category category);
}
