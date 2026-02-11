package com.jilds.interview.adventurebook.service;

import com.jilds.interview.adventurebook.domain.dto.AdventurePlayRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.AdventurePlayResponseDTO;
import com.jilds.interview.adventurebook.domain.dto.AdventureStartRequestDTO;

public interface AdventureService {
    AdventurePlayResponseDTO startAdventure(AdventureStartRequestDTO adventureStartRequestDTO);

    AdventurePlayResponseDTO playAdventure(AdventurePlayRequestDTO adventurePlayRequestDTO);

    void finishAdventure(Integer adventureId);

}
