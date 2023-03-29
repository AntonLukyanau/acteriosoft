package com.asteriosoft.lukyanau.testingtask.service.search;

import com.asteriosoft.lukyanau.testingtask.entity.Banner;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface BannerSearcher {

    List<Banner> searchByCategoriesRequestParams(MultiValueMap<String, String> requestParams);

}
