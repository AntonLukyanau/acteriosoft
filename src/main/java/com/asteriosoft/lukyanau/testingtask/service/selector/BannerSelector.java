package com.asteriosoft.lukyanau.testingtask.service.selector;

import com.asteriosoft.lukyanau.testingtask.entity.Banner;

import java.util.List;

public interface BannerSelector {

    Banner chooseBanner(List<Banner> banners, String ipAddress);

}
