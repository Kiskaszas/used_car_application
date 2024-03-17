package com.test.used_car_application.repository;

import com.test.used_car_application.pojo.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findByBrand(String brand);
    List<Advertisement> findByType(String type);
    List<Advertisement> findByBrandAndType(String brand, String type);
    List<Advertisement> findByPriceLessThanEqual(Long price);
    List<Advertisement> findByBrandAndTypeAndPriceLessThanEqual(
            String brand, String type, Long price);
}
