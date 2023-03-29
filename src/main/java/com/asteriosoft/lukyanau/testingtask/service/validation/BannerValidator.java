package com.asteriosoft.lukyanau.testingtask.service.validation;

import com.asteriosoft.lukyanau.testingtask.dto.BannerDTO;
import com.asteriosoft.lukyanau.testingtask.dto.CategoryDTO;
import com.asteriosoft.lukyanau.testingtask.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerValidator implements DTOValidator<BannerDTO> {

    private final DTOValidator<CategoryDTO> categoryValidator;
    private final BannerRepository bannerRepository;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ValidationResult validate(BannerDTO dto) {
        if (hasNonUniqueName(dto)) {
            return ValidationResult.HAS_NON_UNIQUE_VALUE;
        }
        if (checkEmptyFields(dto)) {
            return ValidationResult.HAS_EMPTY_FIELD;
        }
        if (validateInnerCategories(dto.getCategories())) {
            return ValidationResult.HAS_INVALID_INNER_OBJECT;
        }
        return ValidationResult.IS_VALID;
    }

    @Override
    public boolean isAlreadyExists(Long id) {
        return bannerRepository.existsById(id);
    }

    private boolean hasNonUniqueName(BannerDTO dto) {
        return bannerRepository.findByName(dto.getName()).isPresent();
    }

    private boolean checkEmptyFields(BannerDTO dto) {
        return Strings.isBlank(dto.getName())
                || Strings.isBlank(dto.getBody())
                || dto.getCategories() == null
                || dto.getCategories().size() == 0;
    }

    private boolean validateInnerCategories(List<CategoryDTO> categories) {
        return !categories.stream()
                    .map(CategoryDTO::getId)
                    .allMatch(categoryValidator::isAlreadyExists)
                || categories.stream()
                    .anyMatch(category -> categoryValidator.validate(category) != ValidationResult.IS_VALID);
    }

}
