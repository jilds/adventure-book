package com.jilds.interview.adventurebook.repository;

import com.jilds.interview.adventurebook.domain.entity.SectionEntity;
import com.jilds.interview.adventurebook.domain.enums.SectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Integer> {

    SectionEntity findSectionByTypeAndBookId(SectionType type, Integer bookId);

    SectionEntity findSectionBySectionNumber(Integer sectionNumber);

}
