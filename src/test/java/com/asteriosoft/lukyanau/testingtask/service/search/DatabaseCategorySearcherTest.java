package com.asteriosoft.lukyanau.testingtask.service.search;

import com.asteriosoft.lukyanau.testingtask.entity.Category;
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
public class DatabaseCategorySearcherTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private DatabaseCategorySearcher databaseCategorySearcher;

    @BeforeEach
    public void setUp() {
        databaseCategorySearcher = new DatabaseCategorySearcher(categoryRepository);
    }

    @Test
    public void testSearchByRequestParamsAndNames() {
        //given
        Category category1 = categoryRepository.save(new Category(null, "naMe1", "category1"));
        Category category2 = categoryRepository.save(new Category(null, "Name2", "category2"));
        Category category3 = categoryRepository.save(new Category(null, "name3", "category3"));
        Category category4 = categoryRepository.save(new Category(null, "name4", "category4"));

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("category1", "name");
        paramMap.add("category1", "nonMatch");
        paramMap.add("category2", "name");
        paramMap.add("category3", "me3");
        paramMap.add("category4", "nonMatch");

        //when
        List<Category> categories = databaseCategorySearcher.searchByRequestParamsAndNames(paramMap);
        //then
        assertEquals(3, categories.size());
        assertTrue(categories.contains(category1));
        assertTrue(categories.contains(category2));
        assertTrue(categories.contains(category3));
        assertFalse(categories.contains(category4));
    }

}
