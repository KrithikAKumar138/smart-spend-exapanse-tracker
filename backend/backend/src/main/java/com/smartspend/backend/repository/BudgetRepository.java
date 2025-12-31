package com.smartspend.backend.repository;

import com.smartspend.backend.module.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByEmailAndMonthAndYear(
            String email,
            int month,
            int year
    );
}
