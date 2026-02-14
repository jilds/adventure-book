package com.jilds.interview.adventurebook.domain.entity;

import com.jilds.interview.adventurebook.domain.enums.AdventureStatus;
import com.jilds.interview.adventurebook.domain.enums.Consequence;
import com.jilds.interview.adventurebook.exception.AdventureBookException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

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

    public void handleNextSection(Integer nextSectionNumber) {
        if (!ObjectUtils.isEmpty(currentSection) && !ObjectUtils.isEmpty(currentSection.getOptions())) {
            this.isNextSectionValid(nextSectionNumber);
            this.handleConsequence(nextSectionNumber);
        }
    }

    private void handleConsequence(Integer nextSectionNumber) {
        var consequence = currentSection
                .getOptions().stream()
                .filter(option -> option.getNextSectionNumber().equals(nextSectionNumber))
                .map(OptionEntity::getConsequence)
                .filter(c -> !ObjectUtils.isEmpty(c))
                .findFirst();

        if (consequence.isPresent()) {
            if (consequence.get().getType().equals(Consequence.LOSE_HEALTH)) {
                this.healthPoints -= consequence.get().getValue();
                if (this.healthPoints <= 0) {
                    this.status = AdventureStatus.LOSE;
                }
            } else if (consequence.get().getType().equals(Consequence.GAIN_HEALTH)) {
                this.healthPoints += consequence.get().getValue();
            }
        }
    }

    private void isNextSectionValid(Integer nextSectionNumber) {
        var matchResult = currentSection.getOptions().stream()
                .anyMatch(option -> option.getNextSectionNumber().equals(nextSectionNumber));
        if (!matchResult) {
            throw new AdventureBookException("Invalid next section number", HttpStatus.BAD_REQUEST);
        }
    }
}
