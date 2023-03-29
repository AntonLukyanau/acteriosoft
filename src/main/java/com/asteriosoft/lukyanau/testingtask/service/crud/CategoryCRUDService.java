package com.asteriosoft.lukyanau.testingtask.service.crud;

import com.asteriosoft.lukyanau.testingtask.entity.Category;
import com.asteriosoft.lukyanau.testingtask.repository.BannerRepository;
import com.asteriosoft.lukyanau.testingtask.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryCRUDService implements EntityCRUDService<Category> {

    private final CategoryRepository categoryRepository;
    private final BannerRepository bannerRepository;

    @Override
    public boolean create(Category category) {
        if (category != null) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional
    public boolean update(Category category) {
        boolean bannerIsExists = categoryRepository.existsById(category.getId());
        if (bannerIsExists) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (id != null && bannerRepository.countByCategoriesId(id) == 0) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
