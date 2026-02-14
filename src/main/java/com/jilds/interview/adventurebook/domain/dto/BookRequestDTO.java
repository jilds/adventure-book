package com.jilds.interview.adventurebook.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jilds.interview.adventurebook.domain.enums.SectionType;
import com.jilds.interview.adventurebook.exception.ValidationError;
import com.jilds.interview.adventurebook.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class BookRequestDTO extends BookDTO {

    private Set<SectionDTO> sections;

    public void isValid() {
        if (ObjectUtils.isEmpty(sections)) {
            return;
        }

        var hasOnlyOneBegin = this.getSections().stream()
                .filter(sectionDTO -> sectionDTO.getType().equals(SectionType.BEGIN))
                .limit(2)
                .count() == 1;

        var hasNoEnd = this.getSections().stream()
                .filter(sectionDTO -> sectionDTO.getType().equals(SectionType.END))
                .limit(1)
                .count() < 1;

        var hasNoOptions = this.getSections().stream()
                .filter(sectionDTO -> sectionDTO.getType().equals(SectionType.NODE))
                .filter(sectionDTO -> ObjectUtils.isEmpty(sectionDTO.getOptions()))
                .limit(1)
                .count() >= 1;

        List<ValidationError> errors = new ArrayList<>();
        if (!hasOnlyOneBegin) {
            errors.add(new ValidationError("section", "Book has none, or more than one beginning!"));
        }

        if (hasNoOptions) {
            errors.add(new ValidationError("section", "A non-ending section has no options!"));
        }

        if (hasNoEnd) {
            errors.add(new ValidationError("section", "Book has no ending (but can have multiple)!"));
        }

        var sectionIds = this.getSections().stream()
                .map(SectionDTO::getSectionNumber)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        this.getSections().stream()
                .filter(sectionDTO -> !ObjectUtils.isEmpty(sectionDTO.getOptions()))
                .flatMap(sectionDTO -> Optional.ofNullable(sectionDTO.getOptions()).orElse(List.of()).stream())
                .filter(optionDTO -> Objects.nonNull(optionDTO.getNextSectionNumber()) && !sectionIds.contains(optionDTO.getNextSectionNumber()))
                .forEach(optionDTO -> errors.add(new ValidationError("option", "goto_id", optionDTO.getNextSectionNumber(), "Option points to non-existing section id")));

        if (!ObjectUtils.isEmpty(errors)){
            throw new ValidationException("Book is invalid", errors);
        }
    }
}
