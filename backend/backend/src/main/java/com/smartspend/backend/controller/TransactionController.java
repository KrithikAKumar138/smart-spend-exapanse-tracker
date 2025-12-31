package com.smartspend.backend.controller;

import com.smartspend.backend.dto.TransactionRequest;
import com.smartspend.backend.dto.TransactionSummary;
import com.smartspend.backend.module.Transaction;
import com.smartspend.backend.service.TransactionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public void addTransaction(
            @RequestBody TransactionRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName(); // from JWT
        service.saveTransaction(request, email);
    }
    @GetMapping("/summary")
    public TransactionSummary getSummary(Authentication authentication) {

        String email = authentication.getName();
        return service.getSummary(email);
    }
    @GetMapping("/list")
    public Page<Transaction> getTransactions(
            Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getUserTransactions(auth.getName(), page, size);
    }
    @GetMapping("/expense-chart")
    public Map<String, Double> expenseChart(Authentication auth) {
        return service.getExpenseByCategory(auth.getName());
    }






}
