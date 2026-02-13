package com.jilds.interview.adventurebook.repository;

import com.jilds.interview.adventurebook.domain.entity.AdventureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdventureRepository extends JpaRepository<AdventureEntity, Integer> {

    List<AdventureEntity> findAllByPlayer_Id(Integer playerId);

}
