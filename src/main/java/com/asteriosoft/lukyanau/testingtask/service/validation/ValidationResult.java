package com.asteriosoft.lukyanau.testingtask.service.validation;

public enum ValidationResult {
    IS_VALID("Validation was finished successful"),
    HAS_EMPTY_FIELD("Object has empty field which should be non empty"),
    HAS_NON_UNIQUE_VALUE("Object has field value which should be unique"),
    HAS_INVALID_INNER_OBJECT("Object has a field which contains invalid object");

    final String message;

    ValidationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
