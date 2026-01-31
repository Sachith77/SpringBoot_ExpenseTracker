package com.expensetracker.repository;

import com.expensetracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Category entity operations.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserIdOrderByNameAsc(Long userId);

    List<Category> findByUserIdOrIsDefaultTrueOrderByNameAsc(Long userId);

    Optional<Category> findByIdAndUserId(Long id, Long userId);

    Optional<Category> findByNameAndUserId(String name, Long userId);

    boolean existsByNameAndUserId(String name, Long userId);

    @Query("SELECT c FROM Category c WHERE c.isDefault = true ORDER BY c.name ASC")
    List<Category> findDefaultCategories();

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.expenses WHERE c.id = :id AND c.user.id = :userId")
    Optional<Category> findByIdAndUserIdWithExpenses(@Param("id") Long id, @Param("userId") Long userId);

    @Query("SELECT COUNT(e) FROM Expense e WHERE e.category.id = :categoryId")
    long countExpensesByCategoryId(@Param("categoryId") Long categoryId);
}
