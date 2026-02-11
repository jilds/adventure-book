package com.jilds.interview.adventurebook.domain.mapper;

import com.jilds.interview.adventurebook.domain.dto.PlayerRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.PlayerResposeDTO;
import com.jilds.interview.adventurebook.domain.entity.PlayerEntity;
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
public interface PlayerMapper {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    PlayerEntity toPlayerEntity(PlayerRequestDTO playerRequestDTO);

    PlayerResposeDTO toPlayerDTO(PlayerEntity playerEntity);

    List<PlayerResposeDTO> toPlayerDTOList(List<PlayerEntity> playerEntities);
}
