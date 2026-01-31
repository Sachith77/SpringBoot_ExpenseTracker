package com.expensetracker.repository;

import com.expensetracker.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Budget entity operations.
 */
@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByIdAndUserId(Long id, Long userId);

    List<Budget> findByUserIdAndBudgetMonth(Long userId, YearMonth budgetMonth);

    Optional<Budget> findByUserIdAndCategoryIdAndBudgetMonth(Long userId, Long categoryId, YearMonth budgetMonth);

    Optional<Budget> findByUserIdAndCategoryIsNullAndBudgetMonth(Long userId, YearMonth budgetMonth);

    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId ORDER BY b.budgetMonth DESC")
    List<Budget> findByUserIdOrderByBudgetMonthDesc(@Param("userId") Long userId);

    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId AND b.alertSent = false AND " +
           "(b.spentAmount / b.budgetAmount * 100) >= b.alertThreshold")
    List<Budget> findBudgetsNeedingAlert(@Param("userId") Long userId);

    @Query("SELECT b FROM Budget b WHERE b.alertSent = false AND " +
           "(b.spentAmount / b.budgetAmount * 100) >= b.alertThreshold")
    List<Budget> findAllBudgetsNeedingAlert();

    boolean existsByUserIdAndCategoryIdAndBudgetMonth(Long userId, Long categoryId, YearMonth budgetMonth);

    boolean existsByUserIdAndCategoryIsNullAndBudgetMonth(Long userId, YearMonth budgetMonth);
}
