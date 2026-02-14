package com.jilds.interview.adventurebook.domain.entity;

import com.jilds.interview.adventurebook.domain.enums.Category;
import com.jilds.interview.adventurebook.domain.enums.Difficulty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)
    private Integer id;

    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "difficulty", nullable = false, columnDefinition = "category")
    private Difficulty difficulty;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Category> categories;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private Instant created;

    @UpdateTimestamp
    @Column(name = "updated", insertable = false)
    private Instant updated;

    public void updateCategories(List<Category> categories) {
        this.categories = new ArrayList<>();
        if (ObjectUtils.isEmpty(categories)) {
            return;
        }
        this.categories = categories;
    }

}
