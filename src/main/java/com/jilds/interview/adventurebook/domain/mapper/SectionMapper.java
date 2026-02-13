package com.jilds.interview.adventurebook.domain.mapper;

import com.jilds.interview.adventurebook.domain.dto.SectionDTO;
import com.jilds.interview.adventurebook.domain.entity.SectionEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Objects;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {Objects.class},
        uses = {OptionMapper.class})
public interface SectionMapper {

    SectionDTO toSectionDTO(SectionEntity sectionEntity);

    List<SectionDTO> toSectionDTOList(List<SectionEntity> sectionEntities);

    SectionEntity toSectionEntity(SectionDTO sectionDTO);

    List<SectionEntity> toSectionEntityList(List<SectionDTO> sectionDTOS);
}
