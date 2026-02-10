package com.jilds.interview.adventurebook.model.dto;

import com.jilds.interview.adventurebook.model.enums.Category;
import com.jilds.interview.adventurebook.model.enums.Difficulty;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class BookCriteriaDTO {

    private String title;
    private String author;
    private Difficulty difficulty;
    private Category category;

    @SneakyThrows
    @Hidden
    public Map<String, Object> getCriteriaDTO() {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field: fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(this));
        }
        return map;
    }
}
