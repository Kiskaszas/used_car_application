package com.test.used_car_application.service;

import com.test.used_car_application.Exception.UsedCarAppException;
import com.test.used_car_application.pojo.Advertisement;
import com.test.used_car_application.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementService {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    public void saveAdvertisement(Advertisement advertisement) {
        advertisementRepository.save(advertisement);
    }

    public List<Advertisement> searchAdvertisementsForAllField(String brand, String type, Long price) {
        return advertisementRepository.findByBrandAndTypeAndPriceLessThanEqual(
                brand, type, price);
    }

    public List<Advertisement> findByBrand(String brand){
        return advertisementRepository.findByBrand(brand);
    }

    public List<Advertisement> findByType(String type){
        return advertisementRepository.findByType(type);
    }

    public List<Advertisement> findByBrandAndType(String brand, String type){
        return advertisementRepository.findByBrandAndType(brand, type);
    }

    public List<Advertisement> findByPriceLessThanEqual(Long price){
        return advertisementRepository.findByPriceLessThanEqual(price);
    }

    public void deleteAdvertisement(Long id) {
        advertisementRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
        return advertisementOptional.isPresent();
    }

    public Advertisement findById(Long id) {
        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
        if (advertisementOptional.isPresent()) {
            return advertisementOptional.get();
        } else {
            throw new UsedCarAppException("A hirdetés nem található.");
        }
    }
}
