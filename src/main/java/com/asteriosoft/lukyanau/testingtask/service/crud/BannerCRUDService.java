package com.asteriosoft.lukyanau.testingtask.service.crud;

import com.asteriosoft.lukyanau.testingtask.entity.Banner;
import com.asteriosoft.lukyanau.testingtask.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BannerCRUDService implements EntityCRUDService<Banner> {

    private final BannerRepository bannerRepository;

    @Override
    public boolean create(Banner banner) {
        if (banner != null) {
            bannerRepository.save(banner);
            return true;
        }
        return false;
    }

    @Override
    public Banner findById(Long id) {
        return bannerRepository.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public boolean update(Banner banner) {
        boolean bannerIsExists = bannerRepository.existsById(banner.getId());
        if (bannerIsExists) {
            bannerRepository.save(banner);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id != null) {
            bannerRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
