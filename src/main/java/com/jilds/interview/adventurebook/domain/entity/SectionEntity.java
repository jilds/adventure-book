package com.jilds.interview.adventurebook.domain.entity;

import com.jilds.interview.adventurebook.domain.enums.SectionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "section")
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "section_sequence")
    @SequenceGenerator(name = "section_sequence", sequenceName = "section_sequence", allocationSize = 1)
    private Integer id;

    private String text;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type", columnDefinition = "section_type")
    private SectionType type;

    private Integer sectionNumber;

    @ManyToOne
    private BookEntity book;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private Instant created;

    @UpdateTimestamp
    @Column(name = "updated", insertable = false)
    private Instant updated;

    @Transient
    private List<OptionEntity> options;

}
