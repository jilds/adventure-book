package com.jilds.interview.adventurebook.domain.mapper;

import com.jilds.interview.adventurebook.domain.dto.AdventurePlayResponseDTO;
import com.jilds.interview.adventurebook.domain.entity.AdventureEntity;
import com.jilds.interview.adventurebook.domain.entity.SectionEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {Objects.class},
        uses = {SectionMapper.class})
public interface AdventureMapper {

    @Mapping(source = "id", target = "adventureId")
    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "currentSection", target = "section")
    AdventurePlayResponseDTO toAdventurePlayResponseDTO(AdventureEntity adventureEntity);

    @Mapping(source = "adventureEntity.id", target = "adventureId")
    @Mapping(source = "adventureEntity.player.id", target = "playerId")
    @Mapping(source = "adventureEntity.book.id", target = "bookId")
    @Mapping(source = "sectionEntity", target = "section")
    AdventurePlayResponseDTO toAdventurePlayResponseDTO(AdventureEntity adventureEntity, SectionEntity sectionEntity);


}
