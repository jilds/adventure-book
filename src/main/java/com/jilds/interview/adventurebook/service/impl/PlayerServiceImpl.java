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

    private final PlayerMapper playerMapper;
    private final PlayerRepository playerRepository;

    @Override
    public PlayerResposeDTO createUser(PlayerRequestDTO playerRequestDTO) {
        var playerEntity = playerMapper.toPlayerEntity(playerRequestDTO);
        playerEntity = playerRepository.save(playerEntity);
        return playerMapper.toPlayerDTO(playerEntity);
    }

    @Override
    public List<PlayerResposeDTO> getAllPlayers() {
        var playerEntities = playerRepository.findAll();
        var playerDTOs = playerMapper.toPlayerDTOList(playerEntities);

        return ObjectUtils.isEmpty(playerDTOs) ? List.of() : playerDTOs;
    }

    @Override
    public void deletePlayer(Integer userId) {
        playerRepository.deleteById(userId);
    }
}
