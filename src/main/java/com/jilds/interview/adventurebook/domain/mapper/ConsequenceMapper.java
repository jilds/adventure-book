package com.jilds.interview.adventurebook.domain.mapper;

import com.jilds.interview.adventurebook.domain.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.domain.dto.ConsequenceDTO;
import com.jilds.interview.adventurebook.domain.entity.BookEntity;
import com.jilds.interview.adventurebook.domain.entity.ConsequenceEntity;
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
public interface ConsequenceMapper {

    ConsequenceMapper INSTANCE = Mappers.getMapper(ConsequenceMapper.class);

    ConsequenceDTO toConsequenceDTO(ConsequenceEntity consequenceEntity);

    ConsequenceEntity toConsequenceEntity(ConsequenceDTO consequenceDTO);

    List<BookResposeDTO> toBookDTOList(List<BookEntity> bookEntities);
}
