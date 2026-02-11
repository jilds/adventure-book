package com.jilds.interview.adventurebook.controller;

import com.jilds.interview.adventurebook.domain.dto.AdventurePlayResponseDTO;
import com.jilds.interview.adventurebook.domain.dto.PlayerRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.PlayerResposeDTO;
import com.jilds.interview.adventurebook.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
@Tag(name = "Player Controller", description = "Endpoints for managing players in the adventure book application")
public class PlayerController {

    private final PlayerService playerService;

    @Operation(summary = "Hey there, start an adventure")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerResposeDTO> createPlayer(@RequestBody @Valid PlayerRequestDTO playerRequestDTO) {
        var playerResponseDTO = playerService.createUser(playerRequestDTO);
        return new ResponseEntity<>(playerResponseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Hey there, start an adventure")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerResposeDTO>> getAllPlayers() {
        var playersResponseDTO = playerService.getAllPlayers();
        return ResponseEntity.ok(playersResponseDTO);
    }

    @Operation(summary = "Hey there, start an adventure")
    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdventurePlayResponseDTO> deletePlayer(@PathVariable Integer userId) {
        playerService.deletePlayer(userId);
        return ResponseEntity.ok().build();
    }

}
