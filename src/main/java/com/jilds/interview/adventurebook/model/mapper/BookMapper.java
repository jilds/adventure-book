package com.jilds.interview.adventurebook.model.mapper;

import com.jilds.interview.adventurebook.model.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.model.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.model.entity.BookEntity;
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
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookResposeDTO toBookDTO(BookEntity bookRequestDTO);
    BookEntity toBookEntity(BookRequestDTO bookDTO);
}
