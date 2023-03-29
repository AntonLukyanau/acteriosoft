package com.asteriosoft.lukyanau.testingtask.service.search;

import com.asteriosoft.lukyanau.testingtask.entity.Banner;
import com.asteriosoft.lukyanau.testingtask.entity.Category;
import com.asteriosoft.lukyanau.testingtask.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseBannerSearcher implements BannerSearcher {

    private final BannerRepository bannerRepository;
    private final CategorySearcher categorySearcher;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, readOnly = true)
    public List<Banner> searchByCategoriesRequestParams(MultiValueMap<String, String> requestParams) {
        List<Category> categories = categorySearcher.searchByRequestParamsAndNames(requestParams);
        return bannerRepository.findByCategories(categories);
    }
}
