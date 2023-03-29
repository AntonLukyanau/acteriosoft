package com.asteriosoft.lukyanau.testingtask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private String request;

    public boolean hasId() {
        return id != null;
    }

}
