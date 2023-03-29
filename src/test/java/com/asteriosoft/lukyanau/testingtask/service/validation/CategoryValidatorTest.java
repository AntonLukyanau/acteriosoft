package com.asteriosoft.lukyanau.testingtask.service.validation;

import com.asteriosoft.lukyanau.testingtask.dto.CategoryDTO;
import com.asteriosoft.lukyanau.testingtask.entity.Category;
import com.asteriosoft.lukyanau.testingtask.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryValidatorTest {

    @InjectMocks
    private CategoryValidator validator;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void when_validate_then_returnHAS_NON_UNIQUE_VALUE() {
        //given
        String name = "nonUnique";
        String request = "nonUniqueRequestParam";
        Category existedCategory = new Category(1L, name, request);
        CategoryDTO categoryWithSameName = new CategoryDTO(2L, name, "otherParam");
        CategoryDTO categoryWithSameRequest = new CategoryDTO(3L, "otherName", request);
        //when
        when(categoryRepository.findCategoryByName(any()))
                .thenReturn(Optional.of(existedCategory))
                .thenReturn(Optional.empty());
        ValidationResult validation1 = validator.validate(categoryWithSameName);

        when(categoryRepository.findCategoryByRequest(any()))
                .thenReturn(Optional.of(existedCategory))
                .thenReturn(Optional.empty());
        ValidationResult validation2 = validator.validate(categoryWithSameRequest);

        //then
        assertSame(ValidationResult.HAS_NON_UNIQUE_VALUE, validation1);
        assertSame(ValidationResult.HAS_NON_UNIQUE_VALUE, validation2);
    }

    @Test
    void when_validate_then_returnHAS_EMPTY_FIELD() {
        //given
        CategoryDTO category1 = new CategoryDTO(1L, null, "param");
        CategoryDTO category2 = new CategoryDTO(2L, "name", null);
        CategoryDTO category3 = new CategoryDTO(3L, "", null);
        CategoryDTO category4 = new CategoryDTO(4L, "name", "");
        CategoryDTO category5 = new CategoryDTO(5L, "name", "\n");
        //when
        ValidationResult validation1 = validator.validate(category1);
        ValidationResult validation2 = validator.validate(category2);
        ValidationResult validation3 = validator.validate(category3);
        ValidationResult validation4 = validator.validate(category4);
        ValidationResult validation5 = validator.validate(category5);
        //then
        assertSame(ValidationResult.HAS_EMPTY_FIELD, validation1);
        assertSame(ValidationResult.HAS_EMPTY_FIELD, validation2);
        assertSame(ValidationResult.HAS_EMPTY_FIELD, validation3);
        assertSame(ValidationResult.HAS_EMPTY_FIELD, validation4);
        assertSame(ValidationResult.HAS_EMPTY_FIELD, validation5);
    }

    @Test
    void when_validate_then_returnIS_VALID() {
        //given
        CategoryDTO category1 = new CategoryDTO(1L, "name", "param");
        CategoryDTO category2 = new CategoryDTO(null, "name", "param");
        //when
        when(categoryRepository.findCategoryByName(any())).thenReturn(Optional.empty());
        ValidationResult result1 = validator.validate(category1);
        ValidationResult result2 = validator.validate(category2);
        //then
        assertSame(ValidationResult.IS_VALID, result1);
        assertSame(ValidationResult.IS_VALID, result2);
    }

    @Test
    void when_isAlreadyExists_returnFalse() {
        //given
        CategoryDTO category1 = new CategoryDTO(1L, "name", "param");
        //when
        when(categoryRepository.existsById(any())).thenReturn(Boolean.FALSE);
        boolean alreadyExists = validator.isAlreadyExists(category1.getId());
        //then
        assertFalse(alreadyExists);
    }

    @Test
    void when_isAlreadyExists_returnTrue() {
        //given
        CategoryDTO category1 = new CategoryDTO(1L, "name", "param");
        //when
        when(categoryRepository.existsById(any())).thenReturn(Boolean.TRUE);
        boolean alreadyExists = validator.isAlreadyExists(category1.getId());
        //then
        assertTrue(alreadyExists);
    }

}