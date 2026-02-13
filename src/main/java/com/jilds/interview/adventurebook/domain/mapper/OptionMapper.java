package com.jilds.interview.adventurebook.domain.mapper;

import com.jilds.interview.adventurebook.domain.dto.OptionDTO;
import com.jilds.interview.adventurebook.domain.entity.OptionEntity;
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
        uses = {ConsequenceMapper.class})
public interface OptionMapper {

    OptionDTO toOptionDTO(OptionEntity optionEntity);

    List<OptionDTO> toOptionDTOList(List<OptionEntity> optionEntities);

    OptionEntity toOptionEntity(OptionDTO optionDTO);
}
