package com.jilds.interview.adventurebook.model.dto;

import com.jilds.interview.adventurebook.exception.ValidationError;
import com.jilds.interview.adventurebook.exception.ValidationException;
import com.jilds.interview.adventurebook.model.enums.SectionType;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class BookRequestDTO extends BookDTO {

    public void isValid() {
        var hasOnly1Begin = this.getSections().stream()
                .filter(sectionDTO -> sectionDTO.getType().equals(SectionType.BEGIN))
                .limit(2)
                .count() == 1;

        var hasEnd = this.getSections().stream()
                .filter(sectionDTO -> sectionDTO.getType().equals(SectionType.END))
                .limit(1)
                .count() >= 1;

        var hasNoOptions = this.getSections().stream()
                .filter(sectionDTO -> sectionDTO.getType().equals(SectionType.NODE))
                .filter(sectionDTO -> ObjectUtils.isEmpty(sectionDTO.getOptions()))
                .limit(1)
                .count() >= 1;

        List<ValidationError> errors = new ArrayList<>();
        if (!hasOnly1Begin) {
            errors.add(new ValidationError("section", "Book has none, or more than one beginning!"));
        }

        if (!hasNoOptions) {
            errors.add(new ValidationError("section", "A non-ending section has no options!"));
        }

        if (!hasEnd) {
            errors.add(new ValidationError("section", "Book has no ending (but can have multiple)!"));
        }

        if (!ObjectUtils.isEmpty(errors)){
            throw new ValidationException("Book is invalid", errors);
        }
    }
}
