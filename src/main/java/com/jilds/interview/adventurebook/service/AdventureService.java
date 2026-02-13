package com.jilds.interview.adventurebook.service;

import com.jilds.interview.adventurebook.domain.dto.AdventurePlayRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.AdventurePlayResponseDTO;
import com.jilds.interview.adventurebook.domain.dto.AdventureStartRequestDTO;

import java.util.List;

public interface AdventureService {
    AdventurePlayResponseDTO startAdventure(AdventureStartRequestDTO adventureStartRequestDTO);

    AdventurePlayResponseDTO playAdventure(AdventurePlayRequestDTO adventurePlayRequestDTO);

    AdventurePlayResponseDTO getAdventureById(Integer playerId);

    List<AdventurePlayResponseDTO> getAdventureByPlayerId(Integer playerId);

    void finishAdventure(Integer adventureId);
}
