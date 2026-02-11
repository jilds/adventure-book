package com.jilds.interview.adventurebook.service.impl;

import com.jilds.interview.adventurebook.domain.dto.AdventurePlayRequestDTO;
import com.jilds.interview.adventurebook.domain.dto.AdventurePlayResponseDTO;
import com.jilds.interview.adventurebook.domain.dto.AdventureStartRequestDTO;
import com.jilds.interview.adventurebook.domain.entity.AdventureEntity;
import com.jilds.interview.adventurebook.domain.enums.SectionType;
import com.jilds.interview.adventurebook.domain.mapper.AdventureMapper;
import com.jilds.interview.adventurebook.domain.mapper.OptionMapper;
import com.jilds.interview.adventurebook.domain.mapper.SectionMapper;
import com.jilds.interview.adventurebook.exception.ValidationException;
import com.jilds.interview.adventurebook.repository.*;
import com.jilds.interview.adventurebook.service.AdventureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AdventureServiceImpl implements AdventureService {

    private final AdventureRepository adventureRepository;
    private final BookRepository bookRepository;
    private final SectionRepository sectionRepository;
    private final PlayerRepository playerRepository;
    private final OptionRepository optionRepository;

    @Override
    public AdventurePlayResponseDTO startAdventure(AdventureStartRequestDTO adventureStartRequestDTO) {
        var book = bookRepository.findById(adventureStartRequestDTO.getBookId())
                .orElseThrow(() -> new ValidationException("Book with id " + adventureStartRequestDTO.getBookId() + " does not exist"));

        var player = playerRepository.findById(adventureStartRequestDTO.getUserId())
                .orElseThrow(() -> new ValidationException("Invalid user"));

        var section = sectionRepository.findSectionByTypeAndBookId(SectionType.BEGIN, adventureStartRequestDTO.getBookId());
        var sectionDTO = SectionMapper.INSTANCE.toSectionDTO(section);
        var options = optionRepository.findAllBySectionId(section.getId());

        if (!ObjectUtils.isEmpty(options)) {
            var optionDTO = OptionMapper.INSTANCE.toOptionDTOList(options);
            sectionDTO.setOptions(optionDTO);
        }


        AdventureEntity adventureEntity = new AdventureEntity();
        adventureEntity.setBook(book);
        adventureEntity.setPlayer(player);
        adventureEntity.setCurrentSectionId(section.getSectionNumber());

        adventureEntity = adventureRepository.save(adventureEntity);

        var adventurePlayResponseDTO = new AdventurePlayResponseDTO();
        adventurePlayResponseDTO.setAdventureId(adventureEntity.getId());
        adventurePlayResponseDTO.setUserId(player.getId());
        adventurePlayResponseDTO.setBookId(book.getId());
        adventurePlayResponseDTO.setSection(sectionDTO);
        adventurePlayResponseDTO.setHealthPoints(adventureEntity.getHealthPoints());

        return adventurePlayResponseDTO;
    }

    public AdventurePlayResponseDTO playAdventure(AdventurePlayRequestDTO adventurePlayRequestDTO) {
        var adventureEntity = adventureRepository.findById(adventurePlayRequestDTO.getAdventureId())
                .orElseThrow(() -> new ValidationException("Adventure with id " + adventurePlayRequestDTO.getAdventureId() + " does not exist"));

        adventureEntity.setCurrentSectionId(adventurePlayRequestDTO.getNextSelectedSectionNumber());
        adventureEntity = adventureRepository.save(adventureEntity);

        var section = sectionRepository.findSectionBySectionNumber(adventureEntity.getCurrentSectionId());
        var sectionDTO = SectionMapper.INSTANCE.toSectionDTO(section);
        var options = optionRepository.findAllBySectionId(section.getId());

        if (!ObjectUtils.isEmpty(options)) {
            var optionDTO = OptionMapper.INSTANCE.toOptionDTOList(options);
            sectionDTO.setOptions(optionDTO);
        }

        var adventurePlayResponseDTO = new AdventurePlayResponseDTO();
        adventurePlayResponseDTO.setAdventureId(adventureEntity.getId());
        adventurePlayResponseDTO.setUserId(adventureEntity.getPlayer().getId());
        adventurePlayResponseDTO.setBookId(adventureEntity.getBook().getId());
        adventurePlayResponseDTO.setSection(sectionDTO);
        adventurePlayResponseDTO.setHealthPoints(adventureEntity.getHealthPoints());
        return AdventureMapper.INSTANCE.toAdventurePlayResponseDTO(adventureEntity, section);
    }

    @Override
    public void finishAdventure(Integer adventureId) {
        adventureRepository.finishAdventure(adventureId);
    }
}
