package com.asteriosoft.lukyanau.testingtask.controller;

import com.asteriosoft.lukyanau.testingtask.dto.BannerDTO;
import com.asteriosoft.lukyanau.testingtask.dto.converter.Converter;
import com.asteriosoft.lukyanau.testingtask.entity.Banner;
import com.asteriosoft.lukyanau.testingtask.service.crud.EntityCRUDService;
import com.asteriosoft.lukyanau.testingtask.service.validation.DTOValidator;
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
@RequestMapping("/api/v1/banner")
public record BannerCRUDController(
        EntityCRUDService<Banner> bannerService,
        Converter<BannerDTO, Banner> bannerConverter,
        DTOValidator<BannerDTO> bannerValidator
) {

    @PostMapping
    public ResponseEntity<?> createBanner(@RequestBody BannerDTO bannerDTO) {
        ValidationResult validation = bannerValidator.validate(bannerDTO);
        if (validation == ValidationResult.IS_VALID) {
            Banner banner = bannerConverter.fromDTO(bannerDTO);
            bannerService.create(banner);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.unprocessableEntity().body(validation.getMessage());
    }

    @GetMapping("/{bid}")
    public ResponseEntity<BannerDTO> retrieveBanner(@PathVariable Long bid) {
        Banner banner = bannerService.findById(bid);
        if (banner == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        BannerDTO bannerDTO = bannerConverter.toDTO(banner);
        return ResponseEntity.status(HttpStatus.OK).body(bannerDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateBanner(@RequestBody BannerDTO bannerDTO) {
        if (bannerDTO.hasId()) {
            ValidationResult validation = bannerValidator.validate(bannerDTO);
            if (validation == ValidationResult.IS_VALID) {
                Banner banner = bannerConverter.fromDTO(bannerDTO);
                if (bannerService.update(banner)) {
                    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
                } else {
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Banner was not saved in DB");
                }
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validation.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Id must be non null");
    }

    @DeleteMapping("/{bid}")
    public ResponseEntity<?> deleteBanner(@PathVariable Long bid) {
        bannerService.deleteById(bid);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
