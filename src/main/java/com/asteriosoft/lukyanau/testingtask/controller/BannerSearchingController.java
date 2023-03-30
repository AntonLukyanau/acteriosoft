package com.asteriosoft.lukyanau.testingtask.controller;

import com.asteriosoft.lukyanau.testingtask.dto.BannerDTO;
import com.asteriosoft.lukyanau.testingtask.dto.converter.Converter;
import com.asteriosoft.lukyanau.testingtask.entity.Banner;
import com.asteriosoft.lukyanau.testingtask.entity.Category;
import com.asteriosoft.lukyanau.testingtask.entity.LogRecord;
import com.asteriosoft.lukyanau.testingtask.repository.LogRepository;
import com.asteriosoft.lukyanau.testingtask.service.search.BannerSearcher;
import com.asteriosoft.lukyanau.testingtask.service.selector.BannerSelector;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/search/banner")
@RequiredArgsConstructor
public class BannerSearchingController {

    private final BannerSearcher bannerSearcher;
    private final BannerSelector bannerSelector;
    private final Converter<BannerDTO, Banner> bannerConverter;
    private final LogRepository logRepository;

    @GetMapping("/bid")
    public ResponseEntity<BannerDTO> getBannersByCategoryParams(
            @RequestParam MultiValueMap<String, String> params,
            HttpServletRequest request
    ) {

        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        LogRecord.LogRecordBuilder logBuilder = LogRecord.builder();
        logBuilder.requestIpAddress(ipAddress)
                .userAgent(userAgent)
                .requestTime(LocalDateTime.now());

        List<Banner> banners = bannerSearcher.searchByCategoriesRequestParams(params);
        Banner selectedBanner = bannerSelector.chooseBanner(banners, ipAddress, userAgent);
        saveLog(logBuilder, banners, selectedBanner);
        if (banners.isEmpty() || selectedBanner == null) {
            return ResponseEntity.noContent().build();
        }
        BannerDTO selectedBannerDTO = bannerConverter.toDTO(selectedBanner);
        return ResponseEntity.ok(selectedBannerDTO);
    }

    private void saveLog(LogRecord.LogRecordBuilder logBuilder, List<Banner> banners, Banner selectedBanner) {
        if (banners.isEmpty()) {
            logBuilder.noContentReason("Banner was not found in the database");
        } else if (selectedBanner == null) {
            logBuilder.noContentReason("All matched banners had been showed");
        } else {
            logBuilder.selectedBannerId(selectedBanner.getId())
                    .selectedBannerCategoryIds(extractCategoryIds(selectedBanner))
                    .selectedBannerPrice(selectedBanner.getPrice());
        }
        LogRecord logRecord = logBuilder.build();
        logRepository.save(logRecord);
    }

    private String extractCategoryIds(Banner banner) {
        return banner.getCategories().stream()
                .map(Category::getId)
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

}
