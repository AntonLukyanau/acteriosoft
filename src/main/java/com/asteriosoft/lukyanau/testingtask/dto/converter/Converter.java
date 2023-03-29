package com.asteriosoft.lukyanau.testingtask.dto.converter;

public interface Converter<DTO, Entity> {

    Entity fromDTO(DTO dto);

    DTO toDTO(Entity dto);

}
