package com.jilds.interview.adventurebook.service.impl;

import com.jilds.interview.adventurebook.domain.dto.AdventurePlayRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.AdventurePlayResponseDTO;
import com.jilds.interview.adventurebook.domain.dto.AdventureStartRequestDTO;
import com.jilds.interview.adventurebook.domain.entity.AdventureEntity;
import com.jilds.interview.adventurebook.domain.entity.OptionEntity;
import com.jilds.interview.adventurebook.domain.enums.AdventureStatus;
import com.jilds.interview.adventurebook.domain.enums.SectionType;
import com.jilds.interview.adventurebook.domain.mapper.AdventureMapper;
import com.jilds.interview.adventurebook.exception.AdventureBookException;
import com.jilds.interview.adventurebook.exception.ValidationError;
import com.jilds.interview.adventurebook.exception.ValidationException;
import com.jilds.interview.adventurebook.repository.*;
import com.jilds.interview.adventurebook.service.AdventureService;
import com.jilds.interview.adventurebook.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdventureServiceImpl implements AdventureService {

    private final AdventureMapper adventureMapper;
    private final AdventureRepository adventureRepository;
    private final BookRepository bookRepository;
    private final SectionRepository sectionRepository;
    private final PlayerRepository playerRepository;
    private final OptionRepository optionRepository;
    private final ConsequenceRepository consequenceRepository;
    private final SectionService sectionService;

    @Override
    public AdventurePlayResponseDTO startAdventure(AdventureStartRequestDTO adventureStartRequestDTO) {

        var book = bookRepository.findById(adventureStartRequestDTO.getBookId())
                .orElseThrow(() -> new ValidationException("Book not found", List.of(
                        ValidationError.builder()
                                .entity(adventureStartRequestDTO.getClass().getSimpleName())
                                .property("bookId")
                                .invalidValue(adventureStartRequestDTO.getBookId())
                                .message("Book with id " + adventureStartRequestDTO.getBookId() + " does not exist")
                                .build()
                )));

        var player = playerRepository.findById(adventureStartRequestDTO.getPlayerId())
                .orElseThrow(() -> new ValidationException("Invalid user", List.of(
                        ValidationError.builder()
                                .entity(adventureStartRequestDTO.getClass().getSimpleName())
                                .property("playerId")
                                .invalidValue(adventureStartRequestDTO.getPlayerId())
                                .message("Invalid User!")
                                .build()
                )));

        var sectionDTO = sectionService.getSectionByTypeAndBookId(SectionType.BEGIN, book.getId());

        AdventureEntity adventureEntity = new AdventureEntity();
        adventureEntity.setBook(book);
        adventureEntity.setPlayer(player);
        adventureEntity.setCurrentSectionId(sectionDTO.getId());
        adventureEntity.setStatus(AdventureStatus.IN_PROGRESS);

        adventureEntity = adventureRepository.save(adventureEntity);

        var adventurePlayResponseDTO = adventureMapper.toAdventurePlayResponseDTO(adventureEntity);
        adventurePlayResponseDTO.setSection(sectionDTO);

        return adventurePlayResponseDTO;
    }

    @Override
    public AdventurePlayResponseDTO playAdventure(AdventurePlayRequestDTO adventurePlayRequestDTO) {
        var adventureEntity = loadAdventureEntity(adventurePlayRequestDTO.getAdventureId());

        adventureEntity.handleNextSection(adventurePlayRequestDTO.getNextSectionNumber());

        var section = sectionService.getSectionByBookIdAndSectionNumber(adventureEntity.getBook().getId(), adventurePlayRequestDTO.getNextSectionNumber());

        adventureEntity.setCurrentSectionId(section.getId());
        if (section.getType() == SectionType.END) {
            adventureEntity.setStatus(AdventureStatus.COMPLETE);
        }
        adventureRepository.save(adventureEntity);

        var adventurePlayResponseDTO = adventureMapper.toAdventurePlayResponseDTO(adventureEntity);
        adventurePlayResponseDTO.setSection(section);

        return adventurePlayResponseDTO;
    }

    @Override
    public AdventurePlayResponseDTO getAdventureById(Integer adventureId) {
        var adventureEntity = loadAdventureEntity(adventureId);

        return adventureMapper.toAdventurePlayResponseDTO(adventureEntity);

    }

    public List<AdventurePlayResponseDTO> getAdventureByPlayerId(Integer playerId) {
        var adventureEntity = adventureRepository.findAllByPlayer_Id(playerId);

        if (ObjectUtils.isEmpty(adventureEntity)) {
            throw new AdventureBookException("No adventure fund for player " + playerId, HttpStatus.NOT_FOUND);
        }

        var adventurePlayResponseDTO = new ArrayList<AdventurePlayResponseDTO>();
        adventureEntity.forEach(adventure -> {
            var section = sectionRepository.findSectionById(adventure.getCurrentSectionId());

            var adventureDTO = adventureMapper.toAdventurePlayResponseDTO(adventure, section);

            adventurePlayResponseDTO.add(adventureDTO);
        });

        return adventurePlayResponseDTO;
    }

    @Override
    public void finishAdventure(Integer adventureId) {
        var adventureEntity = adventureRepository.findById(adventureId)
                .orElseThrow(() -> new ValidationException("Adventure with id " + adventureId + " does not exist"));
        adventureEntity.setHealthPoints(0);
        adventureEntity.setStatus(AdventureStatus.QUIT);
        adventureRepository.save(adventureEntity);
    }

    private AdventureEntity loadAdventureEntity(Integer adventureId) {
        var adventureEntity = adventureRepository.findById(adventureId)
                .orElseThrow(() -> new ValidationException("Adventure with id " + adventureId + " does not exist"));

        var section = sectionRepository.findById(adventureEntity.getCurrentSectionId())
                .orElseThrow(() -> new ValidationException("Invalid Adventure, please contact the Admin!"));

        if (!ObjectUtils.isEmpty(section)) {
                var options = optionRepository.findAllBySectionId(section.getId());
                if (!ObjectUtils.isEmpty(options)) {
                    for (OptionEntity option : options) {
                        var consequence = consequenceRepository.findConsequenceByOption_Id(option.getId());
                        if (!ObjectUtils.isEmpty(consequence)) {
                            option.setConsequence(consequence);
                        }
                    }
                    section.setOptions(options);
                }
        }
        adventureEntity.setCurrentSection(section);

        return adventureEntity;
    }
}
