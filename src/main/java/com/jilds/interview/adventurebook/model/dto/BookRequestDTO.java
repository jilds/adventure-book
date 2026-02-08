package com.jilds.interview.adventurebook.model.dto;

import com.jilds.interview.adventurebook.model.enums.SectionType;

public class BookRequestDTO extends BookDTO {

    public void isValid() {
        var hasOnly1Begin = this.getSections().stream()
                .filter(sectionDTO -> sectionDTO.getType().equals(SectionType.BEGIN))
                .limit(2)
                .count() == 1;

        if (hasOnly1Begin) {
            System.out.println("Book is invalid");
        }
    }
}
