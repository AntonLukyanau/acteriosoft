package com.asteriosoft.lukyanau.testingtask.service.validation;

import com.asteriosoft.lukyanau.testingtask.service.validation.ValidationResult;

public interface DTOValidator<T> {

    ValidationResult validate(T dto);

    boolean isAlreadyExists(Long id);

}
