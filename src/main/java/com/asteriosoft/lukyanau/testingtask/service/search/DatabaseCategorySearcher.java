package com.asteriosoft.lukyanau.testingtask.service.search;

import com.asteriosoft.lukyanau.testingtask.entity.Category;
import com.asteriosoft.lukyanau.testingtask.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatabaseCategorySearcher implements CategorySearcher {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<Category> searchByRequestParamsAndNames(MultiValueMap<String, String> paramNamesToCategoryName) {
        List<Category> searchResult = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : paramNamesToCategoryName.entrySet()) {
            List<Category> categories = searchByRequestParamAndNames(entry.getKey(), entry.getValue());
            searchResult.addAll(categories);
        }
        return searchResult;
    }

    private List<Category> searchByRequestParamAndNames(String requestParam, List<String> names) {
        List<Category> searchResult = new ArrayList<>();
        for (String name : names) {
            var foundCategories = categoryRepository.findCategoriesByRequestAndNameLikeIgnoreCase(
                    requestParam,
                    '%' + name + '%');
            searchResult.addAll(foundCategories);
        }
        return searchResult;
    }

}
