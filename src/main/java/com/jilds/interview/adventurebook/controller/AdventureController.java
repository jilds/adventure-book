package com.jilds.interview.adventurebook.controller;

import com.jilds.interview.adventurebook.domain.dto.AdventurePlayRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.AdventurePlayResponseDTO;
import com.jilds.interview.adventurebook.domain.dto.AdventureStartRequestDTO;
import com.jilds.interview.adventurebook.service.AdventureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adventures")
@RequiredArgsConstructor
@Tag(name = "Adventure Controller", description = "API for playing adventures, here you can start an adventure and get the current state of the adventure")
public class AdventureController {

    private final AdventureService adventureService;

    @Operation(summary = "Hey there, start an adventure")
    @PostMapping(value = "/start", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdventurePlayResponseDTO> startAdventure(@RequestBody @Valid AdventureStartRequestDTO adventureStartRequestDTO) {
        var adventureDTO = adventureService.startAdventure(adventureStartRequestDTO);
        return ResponseEntity.ok(adventureDTO);
    }

    @Operation(summary = "Play an adventure")
    @PostMapping(value = "/play", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdventurePlayResponseDTO> playAdventure(@RequestBody @Valid AdventurePlayRequestDTO adventurePlayRequestDTO) {
        adventureService.playAdventure(adventurePlayRequestDTO);
        return ResponseEntity.ok(new AdventurePlayResponseDTO());
    }

    @Operation(summary = "End an adventure. Loooser!")
    @DeleteMapping(value = "/end/{adventureId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> playAdventure(@PathVariable Integer adventureId) {
        adventureService.finishAdventure(adventureId);
        return ResponseEntity.ok("Adventure with id " + adventureId + " has been finished");
    }

}
