package com.smartspend.backend.module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    private String email;   // logged-in user
    @Getter
    @Setter
    private String type;    // INCOME or EXPENSE
    @Getter
    @Setter
    private double amount;
    @Getter
    @Setter
    private String category;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private LocalDateTime createdAt;
    @Getter
    @Setter
    private int month; // 1–12
    @Getter
    @Setter
    private int year;  // 2025

    public Transaction() {
        this.createdAt = LocalDateTime.now();
    }


}
