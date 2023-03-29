package com.asteriosoft.lukyanau.testingtask.service.search;

import com.asteriosoft.lukyanau.testingtask.entity.Category;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface CategorySearcher {

    List<Category> searchByRequestParamsAndNames(MultiValueMap<String, String> paramNamesToCategoryName);

}
