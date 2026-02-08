package com.jilds.interview.adventurebook.repository;

import com.jilds.interview.adventurebook.model.entity.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Long> {
}
