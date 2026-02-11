package com.jilds.interview.adventurebook.domain.mapper;

import com.jilds.interview.adventurebook.domain.dto.AdventurePlayResponseDTO;
import com.jilds.interview.adventurebook.domain.entity.AdventureEntity;
import com.jilds.interview.adventurebook.domain.entity.SectionEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {Objects.class})
public interface AdventureMapper {

    AdventureMapper INSTANCE = Mappers.getMapper(AdventureMapper.class);

    AdventurePlayResponseDTO toAdventurePlayResponseDTO(AdventureEntity adventureEntity, SectionEntity section);

}
