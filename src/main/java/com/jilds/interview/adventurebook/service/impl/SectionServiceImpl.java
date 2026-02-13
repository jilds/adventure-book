package com.jilds.interview.adventurebook.service.impl;

import com.jilds.interview.adventurebook.domain.dto.OptionDTO;
import com.jilds.interview.adventurebook.domain.dto.SectionDTO;
import com.jilds.interview.adventurebook.domain.entity.BookEntity;
import com.jilds.interview.adventurebook.domain.entity.SectionEntity;
import com.jilds.interview.adventurebook.domain.enums.SectionType;
import com.jilds.interview.adventurebook.domain.mapper.ConsequenceMapper;
import com.jilds.interview.adventurebook.domain.mapper.OptionMapper;
import com.jilds.interview.adventurebook.domain.mapper.SectionMapper;
import com.jilds.interview.adventurebook.repository.ConsequenceRepository;
import com.jilds.interview.adventurebook.repository.OptionRepository;
import com.jilds.interview.adventurebook.repository.SectionRepository;
import com.jilds.interview.adventurebook.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionMapper sectionMapper;
    private final SectionRepository sectionRepository;
    private final OptionMapper optionMapper;
    private final OptionRepository optionRepository;
    private final ConsequenceMapper consequenceMapper;
    private final ConsequenceRepository consequenceRepository;

    @Override
    @Transactional
    public void createSections(BookEntity book, Set<SectionDTO> sections) {
        sections.forEach((sectionDTO -> {

            var sectionEntity = sectionMapper.toSectionEntity(sectionDTO);
            sectionEntity.setBook(book);
            sectionEntity = sectionRepository.save(sectionEntity);

            if (!ObjectUtils.isEmpty(sectionDTO.getOptions())) {
                for (OptionDTO optionDTO : sectionDTO.getOptions()) {
                    var optionEntity = optionMapper.toOptionEntity(optionDTO);
                    optionEntity.setSection(sectionEntity);
                    optionEntity = optionRepository.save(optionEntity);
                    if (!ObjectUtils.isEmpty(optionDTO.getConsequence())) {
                        var consequenceEntity = consequenceMapper.toConsequenceEntity(optionDTO.getConsequence());
                        consequenceEntity.setOption(optionEntity);
                        consequenceRepository.save(consequenceEntity);
                    }
                }
            }

        }));
    }

    @Override
    public SectionDTO getSectionByTypeAndBookId(SectionType sectionType, Integer bookId) {
        var section = sectionRepository.findSectionByTypeAndBookId(sectionType, bookId);
        return buildSection(section);
    }

    @Override
    public SectionDTO getSectionBySectionId(Integer sectionId) {
        var section = sectionRepository.findSectionById(sectionId);
        return buildSection(section);
    }

    @Override
    public SectionDTO getSectionByBookIdAndSectionNumber(Integer bookId, Integer sectionNumber) {
        var section = sectionRepository.findSectionByBookIdAndSectionNumber(bookId, sectionNumber);
        return buildSection(section);
    }

    private SectionDTO buildSection(SectionEntity section) {
        var sectionDTO = sectionMapper.toSectionDTO(section);

        var options = optionRepository.findAllBySectionId(section.getId());
        var optionsDTO = optionMapper.toOptionDTOList(options);
        if (!ObjectUtils.isEmpty(optionsDTO)) {
            for (OptionDTO optionDTO : optionsDTO) {
                var consequence = consequenceRepository.findConsequenceByOption_Id(optionDTO.getId());
                if (!ObjectUtils.isEmpty(consequence)) {
                    var consequenceDTO = consequenceMapper.toConsequenceDTO(consequence);
                    optionDTO.setConsequence(consequenceDTO);
                }
            }
            sectionDTO.setOptions(optionsDTO);
        }

        return sectionDTO;
    }
}
