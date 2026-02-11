package com.jilds.interview.adventurebook.repository;

import com.jilds.interview.adventurebook.domain.entity.ConsequenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsequenceRepository extends JpaRepository<ConsequenceEntity, Long> {
}
