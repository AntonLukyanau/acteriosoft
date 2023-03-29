package com.asteriosoft.lukyanau.testingtask.dto.converter;

import com.asteriosoft.lukyanau.testingtask.dto.CategoryDTO;
import com.asteriosoft.lukyanau.testingtask.entity.Category;
import org.springframework.stereotype.Service;

@Service
public record CategoryDTOConverter() implements Converter<CategoryDTO, Category> {

    @Override
    public Category fromDTO(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.getId(), categoryDTO.getName(), categoryDTO.getRequest());
    }

    @Override
    public CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getRequest());
    }

}
