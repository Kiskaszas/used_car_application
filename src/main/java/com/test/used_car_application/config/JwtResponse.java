package com.test.used_car_application.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    public JwtResponse(String accessToken) {
        this.token = accessToken;
    }

}

