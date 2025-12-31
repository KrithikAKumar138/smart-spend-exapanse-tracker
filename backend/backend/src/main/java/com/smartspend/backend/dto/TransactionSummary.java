package com.smartspend.backend.dto;
public class TransactionSummary {

    private double income;
    private double expense;
    private double remaining;

    public TransactionSummary(double income, double expense) {
        this.income = income;
        this.expense = expense;
        this.remaining = income - expense;
    }

    public double getIncome() {
        return income;
    }

    public double getExpense() {
        return expense;
    }

    public double getRemaining() {
        return remaining;
    }
}

