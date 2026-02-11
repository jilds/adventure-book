package com.jilds.interview.adventurebook.service;

import com.jilds.interview.adventurebook.domain.dto.SectionDTO;
import com.jilds.interview.adventurebook.domain.entity.BookEntity;

import java.util.Set;

public interface SectionService {

    void createSections(BookEntity book, Set<SectionDTO> sections);

}
