package com.asteriosoft.lukyanau.testingtask.service.validation;

import com.asteriosoft.lukyanau.testingtask.dto.CategoryDTO;
import com.asteriosoft.lukyanau.testingtask.repository.CategoryRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryValidator implements DTOValidator<CategoryDTO> {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public ValidationResult validate(CategoryDTO dto) {
        if (Strings.isBlank(dto.getRequest()) || Strings.isBlank(dto.getName())) {
            return ValidationResult.HAS_EMPTY_FIELD;
        }
        if (categoryRepository.findCategoryByName(dto.getName()).isPresent()) {
            return ValidationResult.HAS_NON_UNIQUE_VALUE;
        }
        if (categoryRepository.findCategoryByRequest(dto.getRequest()).isPresent()) {
            return ValidationResult.HAS_NON_UNIQUE_VALUE;
        }
        return ValidationResult.IS_VALID;
    }

    @Override
    public boolean isAlreadyExists(@NonNull Long id) {
        return categoryRepository.existsById(id);
    }

}
