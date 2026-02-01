package com.expensetracker.repository;

import com.expensetracker.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Category entity operations.
 */
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    List<Category> findByUserIdOrderByNameAsc(String userId);

    @Query("{ '$or': [ { 'userId': ?0 }, { 'isDefault': true } ] }")
    List<Category> findByUserIdOrIsDefaultTrueOrderByNameAsc(String userId);

    Optional<Category> findByIdAndUserId(String id, String userId);

    Optional<Category> findByNameAndUserId(String name, String userId);

    boolean existsByNameAndUserId(String name, String userId);

    @Query("{ 'isDefault': true }")
    List<Category> findDefaultCategories();
}
