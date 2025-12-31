package com.smartspend.backend.controller;
import com.smartspend.backend.service.BudgetService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    // ✅ Set Budget
    @PostMapping("/set")
    public void setBudget(@RequestParam double amount,
                          Authentication authentication) {

        String email = authentication.getName();
        budgetService.saveBudget(email, amount);
    }

    // ✅ Get Budget Overview
    @GetMapping("/overview")
    public Map<String, Double> getBudgetOverview(Authentication authentication) {

        String email = authentication.getName();

        Map<String, Double> response = new HashMap<>();
        response.put("budget", budgetService.getBudget(email));
        response.put("remaining", budgetService.getRemaining(email));

        return response;
    }
}
