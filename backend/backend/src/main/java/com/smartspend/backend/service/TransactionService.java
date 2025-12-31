package com.smartspend.backend.service;

import com.smartspend.backend.dto.TransactionRequest;
import com.smartspend.backend.dto.TransactionSummary;
import com.smartspend.backend.module.Transaction;
import com.smartspend.backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void saveTransaction(TransactionRequest request, String email) {

        Transaction tx = new Transaction();
        tx.setEmail(email);
        tx.setType(request.getType());
        tx.setAmount(request.getAmount());
        tx.setCategory(request.getCategory());
        tx.setDescription(request.getDescription());


        repository.save(tx);
    }
    public TransactionSummary getSummary(String email) {

        double income = repository.sumByType(email, "INCOME");
        double expense = repository.sumByType(email, "EXPENSE");

        return new TransactionSummary(income, expense);
    }
    public Page<Transaction> getUserTransactions(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByEmailOrderByCreatedAtDesc(email, pageable);
    }
    public Map<String, Double> getExpenseByCategory(String email) {
        List<Object[]> rows = repository.expenseByCategory(email);

        Map<String, Double> result = new HashMap<>();
        for (Object[] row : rows) {
            result.put((String) row[0], (Double) row[1]);
        }
        return result;
    }





}
