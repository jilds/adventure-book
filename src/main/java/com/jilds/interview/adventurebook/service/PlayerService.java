package com.jilds.interview.adventurebook.service;

import com.jilds.interview.adventurebook.domain.dto.PlayerRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.PlayerResposeDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface PlayerService {

    PlayerResposeDTO createUser(@Valid PlayerRequestDTO playerRequestDTO);

    List<PlayerResposeDTO> getAllPlayers();

    void deletePlayer(Integer userId);
}
