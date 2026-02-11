package com.jilds.interview.adventurebook.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jilds.interview.adventurebook.domain.enums.Category;
import com.jilds.interview.adventurebook.domain.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class BookDTO {
    private String title;
    private String author;
    private Difficulty difficulty;
    private List<Category> categories;
}
