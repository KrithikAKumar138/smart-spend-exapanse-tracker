package com.smartspend.backend.repository;
import com.smartspend.backend.module.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByEmailOrderByCreatedAtDesc(String email, Pageable pageable);
        @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.email = :email AND t.type = :type")
        double sumByType(String email, String type);
        List<Transaction> findByEmailOrderByCreatedAtDesc(String email);
    @Query("""
SELECT t.category, SUM(t.amount)
FROM Transaction t
WHERE t.email = :email AND t.type = 'EXPENSE'
GROUP BY t.category
""")
    List<Object[]> expenseByCategory(String email);


}
