package com.asteriosoft.lukyanau.testingtask.controller;

import com.asteriosoft.lukyanau.testingtask.dto.CategoryDTO;
import com.asteriosoft.lukyanau.testingtask.dto.converter.Converter;
import com.asteriosoft.lukyanau.testingtask.entity.Category;
import com.asteriosoft.lukyanau.testingtask.service.validation.DTOValidator;
import com.asteriosoft.lukyanau.testingtask.service.crud.EntityCRUDService;
import com.asteriosoft.lukyanau.testingtask.service.validation.ValidationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/category")
public record CategoryCRUDController(
        EntityCRUDService<Category> categoryService,
        Converter<CategoryDTO, Category> categoryConverter,
        DTOValidator<CategoryDTO> categoryValidator
) {

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryDTO categoryDTO) {
        ValidationResult validationResult = categoryValidator.validate(categoryDTO);
        if (validationResult == ValidationResult.IS_VALID) {
            Category category = categoryConverter.fromDTO(categoryDTO);
            categoryService.create(category);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.unprocessableEntity().body(validationResult.getMessage());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> retrieveCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        CategoryDTO categoryDTO = categoryConverter.toDTO(category);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
    }

    @PutMapping
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        ValidationResult validation = categoryValidator.validate(categoryDTO);
        if (validation == ValidationResult.IS_VALID && categoryDTO.hasId()) {
            if (categoryValidator.isAlreadyExists(categoryDTO.getId())) {
                return ResponseEntity.unprocessableEntity().body("Category already exists");
            }
            Category category = categoryConverter.fromDTO(categoryDTO);
            if (categoryService.update(category)) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }
        }
        return ResponseEntity.unprocessableEntity().body(validation.getMessage());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        if (categoryService.deleteById(id)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("No possibility to delete category which used in active links");
    }


}
