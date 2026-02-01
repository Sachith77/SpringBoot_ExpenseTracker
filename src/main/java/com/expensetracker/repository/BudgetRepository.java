package com.expensetracker.repository;

import com.expensetracker.model.Budget;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Budget entity operations.
 */
@Repository
public interface BudgetRepository extends MongoRepository<Budget, String> {

    Optional<Budget> findByIdAndUserId(String id, String userId);

    List<Budget> findByUserIdAndBudgetMonth(String userId, YearMonth budgetMonth);

    Optional<Budget> findByUserIdAndCategoryIdAndBudgetMonth(String userId, String categoryId, YearMonth budgetMonth);

    Optional<Budget> findByUserIdAndCategoryIdIsNullAndBudgetMonth(String userId, YearMonth budgetMonth);

    List<Budget> findByUserIdOrderByBudgetMonthDesc(String userId);

    @Query("{ 'userId': ?0, 'alertSent': false, '$expr': { '$gte': [ { '$multiply': [ { '$divide': ['$spentAmount', '$budgetAmount'] }, 100 ] }, '$alertThreshold' ] } }")
    List<Budget> findBudgetsNeedingAlert(String userId);

    @Query("{ 'alertSent': false, '$expr': { '$gte': [ { '$multiply': [ { '$divide': ['$spentAmount', '$budgetAmount'] }, 100 ] }, '$alertThreshold' ] } }")
    List<Budget> findAllBudgetsNeedingAlert();

    boolean existsByUserIdAndCategoryIdAndBudgetMonth(String userId, String categoryId, YearMonth budgetMonth);

    boolean existsByUserIdAndCategoryIdIsNullAndBudgetMonth(String userId, YearMonth budgetMonth);
}
