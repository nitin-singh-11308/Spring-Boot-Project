package com.example.MSCafe.model;

import com.example.MSCafe.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount = 0D;
    private Boolean isPaid = true;

    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;

    @OneToOne(mappedBy = "account")
    @JoinColumn(name = "chef")
    @JsonIgnore
    private Chef chef;
}
