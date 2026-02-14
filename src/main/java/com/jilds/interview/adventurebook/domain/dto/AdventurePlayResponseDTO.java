package com.jilds.interview.adventurebook.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jilds.interview.adventurebook.domain.enums.AdventureStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class AdventurePlayResponseDTO {
    private Integer adventureId;
    private Integer playerId;
    private Integer bookId;
    private SectionDTO section;
    private Integer healthPoints;
    private AdventureStatus status;
}
