package com.asteriosoft.lukyanau.testingtask.service.validation;

import com.asteriosoft.lukyanau.testingtask.dto.BannerDTO;
import com.asteriosoft.lukyanau.testingtask.dto.CategoryDTO;
import com.asteriosoft.lukyanau.testingtask.entity.Banner;
import com.asteriosoft.lukyanau.testingtask.repository.BannerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BannerValidatorTest {

    @InjectMocks
    private BannerValidator bannerValidator;

    @Mock
    private DTOValidator<CategoryDTO> categoryValidator;

    @Mock
    private BannerRepository bannerRepository;

    @Test
    void when_validate_with_nonUniqueName_then_returnHAS_NON_UNIQUE_VALUE() {
        // given
        BannerDTO bannerDTO = new BannerDTO();
        bannerDTO.setName("existingName");
        when(bannerRepository.findByName("existingName")).thenReturn(Optional.of(new Banner()));

        // when
        ValidationResult validationResult = bannerValidator.validate(bannerDTO);

        // then
        assertEquals(ValidationResult.HAS_NON_UNIQUE_VALUE, validationResult);
    }

    @Test
    void when_validate_with_emptyFields_then_returnHAS_EMPTY_FIELD() {
        // given
        BannerDTO bannerDTO = new BannerDTO();
        bannerDTO.setName("");
        bannerDTO.setBody("body");
        bannerDTO.setCategories(Collections.singletonList(new CategoryDTO()));

        // when
        ValidationResult validationResult = bannerValidator.validate(bannerDTO);

        // then
        assertEquals(ValidationResult.HAS_EMPTY_FIELD, validationResult);
    }

    @Test
    void when_validate_with_invalidCategories_then_returnHAS_INVALID_INNER_OBJECT() {
        // given
        BannerDTO bannerDTO = new BannerDTO();
        bannerDTO.setName("name");
        bannerDTO.setBody("body");
        bannerDTO.setCategories(Collections.singletonList(new CategoryDTO(1L, null, "request")));

        when(categoryValidator.isAlreadyExists(any())).thenReturn(true);
        when(categoryValidator.validate(any())).thenReturn(ValidationResult.HAS_EMPTY_FIELD);

        // when
        ValidationResult validationResult = bannerValidator.validate(bannerDTO);

        // then
        assertEquals(ValidationResult.HAS_INVALID_INNER_OBJECT, validationResult);
    }

    @Test
    void when_validate_with_validInput_then_returnIS_VALID() {
        // given
        BannerDTO bannerDTO = new BannerDTO();
        bannerDTO.setName("name");
        bannerDTO.setBody("body");
        bannerDTO.setCategories(Collections.singletonList(new CategoryDTO(1L, "name", "request")));

        when(categoryValidator.isAlreadyExists(any())).thenReturn(true);
        when(categoryValidator.validate(any())).thenReturn(ValidationResult.IS_VALID);
        when(bannerRepository.findByName(any())).thenReturn(Optional.empty());

        // when
        ValidationResult validationResult = bannerValidator.validate(bannerDTO);

        // then
        assertEquals(ValidationResult.IS_VALID, validationResult);
    }

    @Test
    void when_isAlreadyExists_with_existingId_then_returnTrue() {
        // given
        Long bannerId = 1L;
        when(bannerRepository.existsById(bannerId)).thenReturn(true);

        // when
        boolean result = bannerValidator.isAlreadyExists(bannerId);

        // then
        assertTrue(result);
    }

    @Test
    void when_isAlreadyExists_with_nonExistingId_then_returnFalse() {
        // given
        Long bannerId = 1L;
        when(bannerRepository.existsById(bannerId)).thenReturn(false);

        // when
        boolean result = bannerValidator.isAlreadyExists(bannerId);

        // then
        assertFalse(result);
    }

}

