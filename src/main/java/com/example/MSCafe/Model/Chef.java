package com.example.MSCafe.Model;

import com.example.MSCafe.enums.Cuisine;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="chef")
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double experience;

    @Enumerated(EnumType.STRING)
    private Cuisine cuisine;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account")
    private Account account;
}
