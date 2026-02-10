package com.jilds.interview.adventurebook.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "adventure")
public class AdventureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Long currentSectionId;

    private Integer healthPoints = 10;

    private boolean completed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private PlayerEntity player;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookEntity book;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private Instant created;

    @UpdateTimestamp
    @Column(name = "updated", insertable = false)
    private Instant updated;

    public void loseHealthPoints(Integer points) {
        this.healthPoints -= points;
        if (this.healthPoints <= 0) {
            this.completed = true;
        }
    }

    public void increaseHealthPoints(Integer points) {
        this.healthPoints += points;
    }

}
