package com.jilds.interview.adventurebook.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class BookEntity {

    private Long id;
    private String title;
    private String author;
    private Set<SectionEntity> sections;


    public void addSection(SectionEntity section) {
        if (Objects.isNull(section)) {
            this.sections = new HashSet<>();
        }
        this.sections.add(section);
        section.setBook(this);
    }

}
