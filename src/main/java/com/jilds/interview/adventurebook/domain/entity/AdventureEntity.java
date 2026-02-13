package com.jilds.interview.adventurebook.domain.entity;

import com.jilds.interview.adventurebook.domain.enums.AdventureStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "adventure")
public class AdventureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adventure_sequence")
    @SequenceGenerator(name = "adventure_sequence", sequenceName = "adventure_sequence", allocationSize = 1)
    private Integer id;

    private Integer currentSectionId;

    private Integer healthPoints = 10;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "adventure_status")
    private AdventureStatus status;

    @ManyToOne
    private PlayerEntity player;

    @ManyToOne
    private BookEntity book;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private Instant created;

    @UpdateTimestamp
    @Column(name = "updated", insertable = false)
    private Instant updated;

    @Transient
    private SectionEntity currentSection;

    public void loseHealthPoints(Integer points) {
        this.healthPoints -= points;
        if (this.healthPoints <= 0) {
            this.status = AdventureStatus.LOSE;
        }
    }

    public void increaseHealthPoints(Integer points) {
        this.healthPoints += points;
    }

}
