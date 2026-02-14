package com.jilds.interview.adventurebook.service;

import com.jilds.interview.adventurebook.domain.dto.SectionDTO;
import com.jilds.interview.adventurebook.domain.entity.BookEntity;
import com.jilds.interview.adventurebook.domain.enums.SectionType;

import java.util.Set;

public interface SectionService {

    void createSections(BookEntity book, Set<SectionDTO> sections);

    SectionDTO getSectionByTypeAndBookId(SectionType sectionType, Integer bookId);

    SectionDTO getSectionBySectionId(Integer sectionId);

    SectionDTO getSectionByBookIdAndSectionNumber(Integer bookId, Integer sectionNumber);
}
