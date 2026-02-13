package com.jilds.interview.adventurebook.domain.mapper;

import com.jilds.interview.adventurebook.domain.dto.BookRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.BookResposeDTO;
import com.jilds.interview.adventurebook.domain.entity.BookEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Objects;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {Objects.class})
public interface BookMapper {

    BookResposeDTO toBookDTO(BookEntity bookRequestDTO);

    BookEntity toBookEntity(BookRequestDTO bookDTO);

    List<BookResposeDTO> toBookDTOList(List<BookEntity> bookEntities);
}
