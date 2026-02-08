package com.jilds.interview.adventurebook.model.entity;

import com.jilds.interview.adventurebook.model.enums.Category;
import com.jilds.interview.adventurebook.model.enums.Difficulty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String author;
    private Difficulty difficulty;
    private Category category;

    //private Set<SectionEntity> sections;


//    public void addSection(SectionEntity section) {
//        if (Objects.isNull(section)) {
//            this.sections = new HashSet<>();
//        }
//        this.sections.add(section);
//        section.setBook(this);
//    }

}
