package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Expense entity operations.
 */
@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    // Basic CRUD with user context
    Optional<Expense> findByIdAndUserId(String id, String userId);

    Page<Expense> findByUserId(String userId, Pageable pageable);

    Page<Expense> findByUserIdAndCategoryId(String userId, String categoryId, Pageable pageable);

    // Date range queries
    @Query("{ 'userId': ?0, 'expenseDate': { '$gte': ?1, '$lte': ?2 } }")
    Page<Expense> findByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("{ 'userId': ?0, 'expenseDate': { '$gte': ?1, '$lte': ?2 } }")
    List<Expense> findByUserIdAndDateRangeList(String userId, LocalDate startDate, LocalDate endDate);

    // Find by type and date range
    @Query("{ 'userId': ?0, 'expenseType': ?1, 'expenseDate': { '$gte': ?2, '$lte': ?3 } }")
    List<Expense> findByUserIdAndTypeAndDateRange(String userId, ExpenseType type, LocalDate startDate, LocalDate endDate);

    // Count queries
    @Query(value = "{ 'userId': ?0, 'expenseDate': { '$gte': ?1, '$lte': ?2 } }", count = true)
    long countByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate);

    // Check for existing expenses in category
    boolean existsByCategoryId(String categoryId);

    // Count expenses by category
    long countByCategoryId(String categoryId);

    // Find recurring expenses
    List<Expense> findByUserIdAndRecurringTrue(String userId);

    // Find by user and expense type
    List<Expense> findByUserIdAndExpenseType(String userId, ExpenseType expenseType);
}
