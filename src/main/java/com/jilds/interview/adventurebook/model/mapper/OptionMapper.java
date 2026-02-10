package com.jilds.interview.adventurebook.model.mapper;

import com.jilds.interview.adventurebook.model.dto.OptionDTO;
import com.jilds.interview.adventurebook.model.entity.OptionEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {Objects.class})
public interface OptionMapper {

    OptionMapper INSTANCE = Mappers.getMapper(OptionMapper.class);

    @Mapping(source = "nextSectionNumber", target = "gotoId")
    OptionDTO toOptionDTO(OptionEntity optionEntity);

    List<OptionDTO> toOptionDTOList(List<OptionEntity> optionEntities);

    @Mapping(source = "gotoId", target = "nextSectionNumber")
    OptionEntity toOptionEntity(OptionDTO optionDTO);

    List<OptionEntity> toEntityDTOList(List<OptionDTO> optionDTOS);
}
