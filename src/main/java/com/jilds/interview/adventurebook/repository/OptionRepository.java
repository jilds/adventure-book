package com.jilds.interview.adventurebook.repository;

import com.jilds.interview.adventurebook.domain.entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

    List<OptionEntity> findAllBySectionId(Integer id);

}
