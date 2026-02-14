package com.jilds.interview.adventurebook.domain.mapper;

import com.jilds.interview.adventurebook.domain.dto.ConsequenceDTO;
import com.jilds.interview.adventurebook.domain.entity.ConsequenceEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {Objects.class})
public interface ConsequenceMapper {

    ConsequenceDTO toConsequenceDTO(ConsequenceEntity consequenceEntity);

    ConsequenceEntity toConsequenceEntity(ConsequenceDTO consequenceDTO);

}
