package com.test.used_car_application.controller;

import com.test.used_car_application.config.JwtResponse;
import com.test.used_car_application.config.JwtTokenProvider;
import com.test.used_car_application.forms.CreateUserForm;
import com.test.used_car_application.forms.LoginForm;
import com.test.used_car_application.pojo.AppUser;
import com.test.used_car_application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody CreateUserForm createUserForm, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }
        AppUser appUser = new AppUser(createUserForm.getUsername(), createUserForm.getEmail(), createUserForm.getPassword());
        if (userService.findByEmail(appUser.getEmail()).isPresent()) {
            result.rejectValue("email", "01", "Ez az e-mail cím már foglalt.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }

        userService.saveUser(appUser);

        return ResponseEntity.ok(appUser);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        AppUser user = userService.createUser(name, email, password);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtTokenProvider.createToken(userDetails.getUsername());

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Érvénytelen felhasználónév vagy jelszó");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null) {
            jwtTokenProvider.blacklist(token);
        }
        return ResponseEntity.ok("Sikeres kijelentkezés");
    }

    @GetMapping("/x")
    public void x(){
        System.out.println("test");
    }
}
