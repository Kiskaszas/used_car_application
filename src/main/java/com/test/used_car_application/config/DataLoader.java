package com.test.used_car_application.config;

import com.test.used_car_application.pojo.Advertisement;
import com.test.used_car_application.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.test.used_car_application.pojo.AppUser;
import com.test.used_car_application.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public DataLoader(UserRepository userRepository, AdvertisementRepository advertisementRepository) {
        this.userRepository = userRepository;
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadDefaultUsers();
        loadDefaultAdvertisements();
    }

    private void loadDefaultAdvertisements() {
        if (advertisementRepository.count() == 0) {
            Advertisement advertisement1 = new Advertisement();
            advertisement1.setBrand("Toyota");
            advertisement1.setType("Corolla");
            advertisement1.setDescription("Jó állapot, kevés km.");
            advertisement1.setPrice(15000L);
            advertisement1.setUser(userRepository.findById(1L).get());

            Advertisement advertisement2 = new Advertisement();
            advertisement2.setBrand("Honda");
            advertisement2.setType("Civic");
            advertisement2.setDescription("Családi okok miatt eladó");
            advertisement2.setPrice(12000L);
            advertisement2.setUser(userRepository.findById(1L).get());

            advertisementRepository.save(advertisement1);
            advertisementRepository.save(advertisement2);

            System.out.println(userRepository.findAll());
        }
    }

    private void loadDefaultUsers() {
        if (userRepository.count() == 0) {
            AppUser user1 = new AppUser("user1", "user1@example.com", "password1");
            AppUser user2 = new AppUser("user2", "user2@example.com", "password2");
            userRepository.save(user1);
            userRepository.save(user2);
        }
    }
}
