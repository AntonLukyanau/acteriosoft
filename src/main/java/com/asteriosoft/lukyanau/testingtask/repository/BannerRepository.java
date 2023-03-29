package com.asteriosoft.lukyanau.testingtask.repository;

import com.asteriosoft.lukyanau.testingtask.entity.Banner;
import com.asteriosoft.lukyanau.testingtask.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    long countByCategoriesId(Long categoryId);

    Optional<Banner> findByName(String bannerName);

    @Query("select distinct b from Banner b join b.categories c where c.id in :categoryIds")
    List<Banner> findAllByCategoryIds(@Param("categoryIds") List<Long> categoryIds);

    default List<Banner> findByCategories(List<Category> categories) {
        return findAllByCategoryIds(categories.stream()
                .map(Category::getId)
                .toList());
    }

}
