package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Expense entity operations.
 * Implements JpaSpecificationExecutor for complex dynamic queries.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

    // Basic CRUD with user context
    Optional<Expense> findByIdAndUserId(Long id, Long userId);

    Page<Expense> findByUserId(Long userId, Pageable pageable);

    Page<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable);

    // Date range queries
    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.expenseDate BETWEEN :startDate AND :endDate")
    Page<Expense> findByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

    // Sum queries for analytics
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId AND e.expenseType = :type AND e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByUserIdAndTypeAndDateRange(
            @Param("userId") Long userId,
            @Param("type") ExpenseType type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Monthly summary query
    @Query("SELECT e.category.id, e.category.name, e.category.color, SUM(e.amount), COUNT(e) " +
           "FROM Expense e WHERE e.user.id = :userId AND e.expenseType = 'EXPENSE' " +
           "AND e.expenseDate BETWEEN :startDate AND :endDate " +
           "GROUP BY e.category.id, e.category.name, e.category.color " +
           "ORDER BY SUM(e.amount) DESC")
    List<Object[]> findMonthlyCategoryBreakdown(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Category-wise spending
    @Query("SELECT e.category.id, e.category.name, e.category.color, e.category.icon, " +
           "SUM(e.amount), COUNT(e), AVG(e.amount), MAX(e.amount), MIN(e.amount) " +
           "FROM Expense e WHERE e.user.id = :userId AND e.expenseType = 'EXPENSE' " +
           "AND e.expenseDate BETWEEN :startDate AND :endDate " +
           "GROUP BY e.category.id, e.category.name, e.category.color, e.category.icon " +
           "ORDER BY SUM(e.amount) DESC")
    List<Object[]> findCategoryWiseSpending(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Yearly report - monthly data
    @Query("SELECT MONTH(e.expenseDate), e.expenseType, SUM(e.amount), COUNT(e) " +
           "FROM Expense e WHERE e.user.id = :userId AND YEAR(e.expenseDate) = :year " +
           "GROUP BY MONTH(e.expenseDate), e.expenseType " +
           "ORDER BY MONTH(e.expenseDate)")
    List<Object[]> findYearlyMonthlyData(
            @Param("userId") Long userId,
            @Param("year") int year
    );

    // Daily expense trend
    @Query("SELECT e.expenseDate, SUM(e.amount), COUNT(e) " +
           "FROM Expense e WHERE e.user.id = :userId AND e.expenseType = 'EXPENSE' " +
           "AND e.expenseDate BETWEEN :startDate AND :endDate " +
           "GROUP BY e.expenseDate " +
           "ORDER BY e.expenseDate")
    List<Object[]> findDailyExpenseTrend(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Count queries
    @Query("SELECT COUNT(e) FROM Expense e WHERE e.user.id = :userId AND e.expenseDate BETWEEN :startDate AND :endDate")
    long countByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Top categories for yearly report
    @Query("SELECT e.category.id, e.category.name, e.category.color, SUM(e.amount) " +
           "FROM Expense e WHERE e.user.id = :userId AND e.expenseType = 'EXPENSE' " +
           "AND YEAR(e.expenseDate) = :year " +
           "GROUP BY e.category.id, e.category.name, e.category.color " +
           "ORDER BY SUM(e.amount) DESC")
    List<Object[]> findTopCategoriesForYear(
            @Param("userId") Long userId,
            @Param("year") int year
    );

    // Check for existing expenses in category
    boolean existsByCategoryId(Long categoryId);

    // Find recurring expenses
    List<Expense> findByUserIdAndRecurringTrue(Long userId);
}
