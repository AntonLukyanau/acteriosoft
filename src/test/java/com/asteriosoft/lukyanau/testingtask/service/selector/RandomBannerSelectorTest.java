package com.asteriosoft.lukyanau.testingtask.service.selector;

import com.asteriosoft.lukyanau.testingtask.entity.Banner;
import com.asteriosoft.lukyanau.testingtask.entity.Category;
import com.asteriosoft.lukyanau.testingtask.entity.LogRecord;
import com.asteriosoft.lukyanau.testingtask.repository.BannerRepository;
import com.asteriosoft.lukyanau.testingtask.repository.LogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomBannerSelectorTest {

    @InjectMocks
    private RandomBannerSelector selector;
    @Mock
    private BannerRepository bannerRepository;
    @Mock
    private LogRepository logRepository;

    @Test
    void when_chooseBannerWithNotEmptyBannerList_then_returnBanner() {
        //given
        Category category = new Category(1L, "categoryName", "requestParamName");
        Banner banner = Banner.builder()
                .id(1L)
                .name("name")
                .body("body")
                .price(100.0)
                .deleted(false)
                .categories(List.of(category))
                .build();
        //when
        when(logRepository.findByRequestIpAddressAndRequestTimeAfter(any(), any())).thenReturn(Collections.emptyList());
        when(bannerRepository.findAllById(any())).thenReturn(Collections.singletonList(banner));
        Banner selectedBanner = selector.chooseBanner(List.of(banner), "127.0.0.1");
        //then
        assertEquals(banner, selectedBanner);
    }

    @Test
    void when_chooseBannerWithEmptyBannerList_then_returnNull() {
        //given
        List<Banner> matchedBanners = Collections.emptyList();
        //when
        when(logRepository.findByRequestIpAddressAndRequestTimeAfter(any(), any())).thenReturn(Collections.emptyList());
        when(bannerRepository.findAllById(any())).thenReturn(Collections.emptyList());
        Banner chooseBanner = selector.chooseBanner(matchedBanners, "127.0.0.1");
        //then
        assertNull(chooseBanner);
    }

    @Test
    void when_chooseBannerWithTwoMatchedBanners_then_returnNonShownBanner() {
        //given
        Category category = new Category(1L, "categoryName", "requestParamName");
        Banner shownBanner = Banner.builder()
                .id(1L)
                .name("name")
                .body("body")
                .price(100.0)
                .deleted(false)
                .categories(List.of(category))
                .build();
        LogRecord log = LogRecord.builder()
                .id(1L)
                .userAgent("agent")
                .requestTime(LocalDateTime.now())
                .selectedBannerCategoryIds(category.getId().toString())
                .selectedBannerPrice(100.0)
                .selectedBannerId(shownBanner.getId())
                .build();
        Banner banner = Banner.builder()
                .id(2L)
                .name("name2")
                .body("body2")
                .price(200.0)
                .deleted(false)
                .categories(List.of(category))
                .build();
        List<Banner> banners = List.of(shownBanner, banner);
        //when
        when(logRepository.findByRequestIpAddressAndRequestTimeAfter(any(), any())).thenReturn(Collections.singletonList(log));
        when(bannerRepository.findAllById(any())).thenReturn(Collections.singletonList(banner));
        Banner choosenBanner = selector.chooseBanner(banners, "ip");
        //then
        assertEquals(banner, choosenBanner);
    }
}