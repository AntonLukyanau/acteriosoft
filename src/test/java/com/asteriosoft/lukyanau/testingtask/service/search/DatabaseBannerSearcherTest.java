package com.asteriosoft.lukyanau.testingtask.service.search;

import com.asteriosoft.lukyanau.testingtask.entity.Banner;
import com.asteriosoft.lukyanau.testingtask.entity.Category;
import com.asteriosoft.lukyanau.testingtask.repository.BannerRepository;
import com.asteriosoft.lukyanau.testingtask.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DatabaseBannerSearcherTest {

    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private DatabaseBannerSearcher databaseBannerSearcher;

    @BeforeEach
    public void setUp() {
        CategorySearcher categorySearcher = new DatabaseCategorySearcher(categoryRepository);
        databaseBannerSearcher = new DatabaseBannerSearcher(bannerRepository, categorySearcher);
    }

    @Test
    public void when_searchByCategoriesRequestParams_givenValidParams_then_returnMatchingBanners() {
        //given
        Category category1 = categoryRepository.save(new Category(null, "oooooo", "par"));
        Category category2 = categoryRepository.save(new Category(null, "Vacation ad ooo", "param"));
        Category category3 = categoryRepository.save(new Category(null, "IT Schools", "cat"));
        Category category4 = categoryRepository.save(new Category(null, "nonMatch", "nonMatch"));

        Banner banner1 = bannerRepository.save(Banner.builder()
                .name("Banner 1")
                .body("body 1")
                .price(100.0)
                .categories(List.of(category1, category2))
                .build());
        Banner banner2 = bannerRepository.save(Banner.builder()
                .name("Banner 2")
                .body("body 2")
                .price(150.0)
                .categories(List.of(category2, category3))
                .build());
        Banner banner3 = bannerRepository.save(Banner.builder()
                .name("Banner 3")
                .body("body 3")
                .price(155.0)
                .categories(List.of(category4))
                .build());

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("cat", "IT");
        paramMap.add("cat", "nonMatch");
        paramMap.add("par", "ooo");
        paramMap.add("par", "nonMatch");
        paramMap.add("param", "nonMatch");

        //when
        List<Banner> banners = databaseBannerSearcher.searchByCategoriesRequestParams(paramMap);

        //then
        assertEquals(2, banners.size());
        assertTrue(banners.contains(banner1));
        assertTrue(banners.contains(banner2));
        assertFalse(banners.contains(banner3));
    }
}
