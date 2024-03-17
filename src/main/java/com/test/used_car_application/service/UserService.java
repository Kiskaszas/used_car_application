package com.test.used_car_application.service;

import com.test.used_car_application.pojo.AppUser;
import com.test.used_car_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<AppUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    public AppUser createUser(String name, String email, String password) {
        return saveUser(new AppUser(name, email, password));
    }
}
