package com.jilds.interview.adventurebook.service.impl;

import com.jilds.interview.adventurebook.domain.dto.OptionDTO;
import com.jilds.interview.adventurebook.domain.dto.SectionDTO;
import com.jilds.interview.adventurebook.domain.entity.BookEntity;
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

    private final SectionRepository sectionRepository;
    private final OptionRepository optionRepository;
    private final ConsequenceRepository consequenceRepository;

    @Override
    @Transactional
    public void createSections(BookEntity book, Set<SectionDTO> sections) {
        sections.forEach((sectionDTO -> {

            var sectionEntity = SectionMapper.INSTANCE.toSectionEntity(sectionDTO);
            sectionEntity.setBook(book);
            sectionEntity = sectionRepository.save(sectionEntity);

            if (!ObjectUtils.isEmpty(sectionDTO.getOptions())) {
                for (OptionDTO optionDTO : sectionDTO.getOptions()) {
                    var optionEntity = OptionMapper.INSTANCE.toOptionEntity(optionDTO);
                    optionEntity.setSection(sectionEntity);
                    optionEntity = optionRepository.save(optionEntity);
                    if (!ObjectUtils.isEmpty(optionDTO.getConsequence())) {
                        var consequenceEntity = ConsequenceMapper.INSTANCE.toConsequenceEntity(optionDTO.getConsequence());
                        consequenceEntity.setOption(optionEntity);
                        consequenceRepository.save(consequenceEntity);
                    }
                }
            }

        }));
    }
}
