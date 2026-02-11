package com.jilds.interview.adventurebook.domain.entity;

import com.jilds.interview.adventurebook.domain.enums.Consequence;
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
@Table(name = "consequence")
public class ConsequenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consequence_sequence")
    @SequenceGenerator(name = "consequence_sequence", sequenceName = "consequence_sequence", allocationSize = 1)
    private Integer id;

    private String text;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "consequence")
    private Consequence type;

    private Long value;

    @ManyToOne
    private OptionEntity option;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private Instant created;

    @UpdateTimestamp
    @Column(name = "updated", insertable = false)
    private Instant updated;

}
