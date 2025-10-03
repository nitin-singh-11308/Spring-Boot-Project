package com.example.MSCafe.model;

import com.example.MSCafe.enums.DishCategory;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    @Enumerated(EnumType.STRING)
    private DishCategory category;
}
