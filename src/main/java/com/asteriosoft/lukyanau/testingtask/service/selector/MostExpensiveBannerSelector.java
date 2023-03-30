package com.asteriosoft.lukyanau.testingtask.service.selector;

import com.asteriosoft.lukyanau.testingtask.entity.Banner;
import com.asteriosoft.lukyanau.testingtask.entity.LogRecord;
import com.asteriosoft.lukyanau.testingtask.repository.BannerRepository;
import com.asteriosoft.lukyanau.testingtask.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MostExpensiveBannerSelector implements BannerSelector {

    private final BannerRepository bannerRepository;
    private final LogRepository logRepository;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public Banner chooseBanner(List<Banner> banners, String ipAddress, String userAgent) {
        List<Banner> previousBanners = loadBannersByIp(ipAddress, userAgent);
        return banners.stream()
                .filter(previousBanners::contains)
                .max(Comparator.comparing(Banner::getPrice))
                .orElse(null);
    }

    private List<Banner> loadBannersByIp(String ipAddress, String userAgent) {
        List<LogRecord> logs = logRepository.findByRequestIpAddressAndUserAgentAndRequestTimeAfter(
                ipAddress,
                userAgent,
                LocalDate.now().atStartOfDay());
        List<Long> showedBannerIds = logs.stream()
                .map(LogRecord::getSelectedBannerId)
                .filter(Objects::nonNull)
                .toList();
        return bannerRepository.findAllById(showedBannerIds);
    }

}
