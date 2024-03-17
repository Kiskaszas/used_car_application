package com.test.used_car_application.controller;

import com.test.used_car_application.pojo.Advertisement;
import com.test.used_car_application.pojo.AppUser;
import com.test.used_car_application.service.AdvertisementService;
import com.test.used_car_application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {

    private AdvertisementService advertisementService;

    private UserService userService;

    public AdvertisementController(AdvertisementService advertisementService, UserService userService) {
        this.advertisementService = advertisementService;
        this.userService = userService;
    }

    @PostMapping("/ad")
    public ResponseEntity<?> createAdvertisement(@Validated @RequestBody Advertisement advertisement,
                                                 BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();
        Optional<AppUser> user = userService.findByEmail(loggedInUserEmail);

        if (user.isPresent()) {
            advertisement.setUser(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A megadott hirdetéshez user nem található.");
        }

        advertisementService.saveAdvertisement(advertisement);

        return ResponseEntity.status(HttpStatus.CREATED).body("Hirdetés sikeresen létrehozva.");
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAdvertisements(@RequestParam(required = false) String brand,
                                                  @RequestParam(required = false) String type,
                                                  @RequestParam(required = false) Long price) {

        List<Advertisement> foundAdvertisements = advertisementService.searchAdvertisementsForAllField(brand, type, price);
        if (foundAdvertisements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nincs találat.");
        }

        return ResponseEntity.ok(foundAdvertisements);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvertisement(@PathVariable Long id,
                                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Csak bejelentkezett felhasználók törölhetik a hirdetéseket.");
        }

        if (!advertisementService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A hirdetés nem található vagy nem tartozik a felhasználóhoz.");
        }

        advertisementService.deleteAdvertisement(id);
        return ResponseEntity.ok("A hirdetés sikeresen törölve lett.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdvertisement(@PathVariable Long id) {
        Advertisement advertisement = advertisementService.findById(id);

        if (advertisement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A megadott hirdetés nem található.");
        }

        return ResponseEntity.ok(advertisement);
    }
}
