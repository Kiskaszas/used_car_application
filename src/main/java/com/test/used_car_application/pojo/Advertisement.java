package com.test.used_car_application.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "app_advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @NotBlank
    @Size(max = 20)
    private String brand;

    @NotBlank
    @Size(max = 20)
    private String type;

    @NotBlank
    @Size(max = 200)
    private String description;

    @Positive
    private Long price;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createdDate;

    public Advertisement(AppUser user, String brand, String type, String description, Long price, Date createdDate) {
        this.user = user;
        this.brand = brand;
        this.type = type;
        this.description = description;
        this.price = price;
        this.createdDate = createdDate;
    }
}