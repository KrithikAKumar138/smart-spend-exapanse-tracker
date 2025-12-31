package com.smartspend.backend.module;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "budgets",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email", "month", "year"}))
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private int month;   // 1 - 12
    private int year;

    private double amount;
}

