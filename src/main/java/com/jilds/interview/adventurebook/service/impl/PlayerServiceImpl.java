package com.jilds.interview.adventurebook.service.impl;

import com.jilds.interview.adventurebook.domain.dto.PlayerRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.PlayerResposeDTO;
import com.jilds.interview.adventurebook.domain.mapper.PlayerMapper;
import com.jilds.interview.adventurebook.repository.PlayerRepository;
import com.jilds.interview.adventurebook.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public PlayerResposeDTO createUser(PlayerRequestDTO playerRequestDTO) {
        var playerEntity = PlayerMapper.INSTANCE.toPlayerEntity(playerRequestDTO);
        playerEntity = playerRepository.save(playerEntity);
        return PlayerMapper.INSTANCE.toPlayerDTO(playerEntity);
    }

    @Override
    public List<PlayerResposeDTO> getAllPlayers() {
        var playerEntities = playerRepository.findAll();
        var playerDTOs = PlayerMapper.INSTANCE.toPlayerDTOList(playerEntities);

        return ObjectUtils.isEmpty(playerDTOs) ? List.of() : playerDTOs;
    }

    @Override
    public void deletePlayer(Integer userId) {
        playerRepository.deleteById(userId);
    }
}
