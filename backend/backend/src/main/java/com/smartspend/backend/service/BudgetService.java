package com.smartspend.backend.service;

import com.smartspend.backend.module.Budget;
import com.smartspend.backend.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final TransactionService transactionService;

    public BudgetService(BudgetRepository budgetRepository,
                         TransactionService transactionService) {
        this.budgetRepository = budgetRepository;
        this.transactionService = transactionService;
    }

    // ✅ Save or Update Budget
    public void saveBudget(String email, double amount) {

        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        Budget budget = budgetRepository
                .findByEmailAndMonthAndYear(email, month, year)
                .orElse(new Budget());

        budget.setEmail(email);
        budget.setMonth(month);
        budget.setYear(year);
        budget.setAmount(amount);

        budgetRepository.save(budget);
    }

    // ✅ Get Budget Amount
    public double getBudget(String email) {

        LocalDate now = LocalDate.now();

        return budgetRepository
                .findByEmailAndMonthAndYear(
                        email,
                        now.getMonthValue(),
                        now.getYear()
                )
                .map(Budget::getAmount)
                .orElse(0.0);
    }

    // ✅ Remaining = Budget − Expense
    public double getRemaining(String email) {

        double budget = getBudget(email);
        double expense = transactionService
                .getSummary(email)
                .getExpense();

        return budget - expense;
    }
}
