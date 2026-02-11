package com.jilds.interview.adventurebook.repository;

import com.jilds.interview.adventurebook.domain.entity.AdventureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdventureRepository extends JpaRepository<AdventureEntity, Integer> {

    @Query("UPDATE AdventureEntity adv SET adv.completed=true WHERE adv.id = :adventureId")
    void finishAdventure(Integer adventureId);

}
