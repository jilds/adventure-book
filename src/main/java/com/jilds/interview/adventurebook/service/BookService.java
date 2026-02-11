package com.jilds.interview.adventurebook.service;

import com.jilds.interview.adventurebook.domain.dto.BookCriteriaDTO;
import com.jilds.interview.adventurebook.domain.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.domain.enums.Category;

import java.util.List;

public interface BookService {

    BookResposeDTO createBook(BookRequestDTO bookRequestDTO);

    List<BookResposeDTO> searchBook(BookCriteriaDTO filters);

    BookResposeDTO getBookDetails(Integer bookId);

    BookResposeDTO patchBookCategory(Integer bookId, List<Category> categories);
}
