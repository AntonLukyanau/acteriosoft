package com.asteriosoft.lukyanau.testingtask.repository;

import com.asteriosoft.lukyanau.testingtask.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findCategoryByName(String categoryName);

    Optional<Category> findCategoryByRequest(String categoryRequest);

    List<Category> findCategoriesByRequestAndNameLikeIgnoreCase(String request, String name);

}
