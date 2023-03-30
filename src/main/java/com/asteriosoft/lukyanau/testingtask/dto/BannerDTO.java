package com.asteriosoft.lukyanau.testingtask.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class BannerDTO {

    private Long id;
    private String name;
    private String body;
    private BigDecimal price;
    private List<CategoryDTO> categories;

    public boolean hasId() {
        return id != null;
    }

}
