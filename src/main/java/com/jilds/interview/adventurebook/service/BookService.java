package com.jilds.interview.adventurebook.service;

import com.jilds.interview.adventurebook.model.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.model.dto.BookResposeDTO;

import java.util.List;
import java.util.Map;

public interface BookService {

    BookResposeDTO createBook(BookRequestDTO bookRequestDTO);

    List<BookResposeDTO> searchBook(Map<String, Object> filters);

}
