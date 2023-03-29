package com.asteriosoft.lukyanau.testingtask.service.validation;

public interface DTOValidator<T> {

    ValidationResult validate(T dto);

    boolean isAlreadyExists(Long id);

}
