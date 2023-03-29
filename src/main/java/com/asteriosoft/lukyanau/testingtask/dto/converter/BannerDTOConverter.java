package com.asteriosoft.lukyanau.testingtask.dto.converter;

import com.asteriosoft.lukyanau.testingtask.dto.BannerDTO;
import com.asteriosoft.lukyanau.testingtask.dto.CategoryDTO;
import com.asteriosoft.lukyanau.testingtask.entity.Banner;
import com.asteriosoft.lukyanau.testingtask.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record BannerDTOConverter(
        Converter<CategoryDTO, Category> categoryConverter
) implements Converter<BannerDTO, Banner> {

    @Override
    public Banner fromDTO(BannerDTO bannerDTO) {
        List<Category> categories = bannerDTO.getCategories().stream()
                .map(categoryConverter::fromDTO)
                .toList();
        return Banner.builder()
                .id(bannerDTO.getId())
                .name(bannerDTO.getName())
                .body(bannerDTO.getBody())
                .price(bannerDTO.getPrice())
                .categories(categories)
                .build();
    }

    @Override
    public BannerDTO toDTO(Banner banner) {
        List<CategoryDTO> categories = banner.getCategories().stream()
                .map(categoryConverter::toDTO)
                .toList();
        return BannerDTO.builder()
                .id(banner.getId())
                .name(banner.getName())
                .body(banner.getBody())
                .price(banner.getPrice())
                .categories(categories)
                .build();
    }

}
