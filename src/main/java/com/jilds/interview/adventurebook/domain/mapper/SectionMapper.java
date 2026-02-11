package com.jilds.interview.adventurebook.domain.mapper;

import com.jilds.interview.adventurebook.domain.dto.SectionDTO;
import com.jilds.interview.adventurebook.domain.entity.SectionEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {Objects.class})
public interface SectionMapper {

    SectionMapper INSTANCE = Mappers.getMapper(SectionMapper.class);

    SectionDTO toSectionDTO(SectionEntity sectionEntity);

    List<SectionDTO> toSectionDTOList(List<SectionEntity> sectionEntities);

    SectionEntity toSectionEntity(SectionDTO sectionDTO);

    List<SectionEntity> toSectionEntityList(List<SectionDTO> sectionDTOS);
}
