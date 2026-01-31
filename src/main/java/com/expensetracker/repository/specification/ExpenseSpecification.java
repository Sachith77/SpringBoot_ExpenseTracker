package com.expensetracker.repository.specification;

import com.expensetracker.dto.expense.ExpenseFilterRequest;
import com.expensetracker.model.Expense;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA Specification for building dynamic Expense queries.
 */
public class ExpenseSpecification {

    private ExpenseSpecification() {
        // Utility class
    }

    /**
     * Creates a specification based on filter criteria and user ID.
     */
    public static Specification<Expense> withFilters(Long userId, ExpenseFilterRequest filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always filter by user
            predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));

            if (filter == null) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            // Category filter
            if (filter.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), filter.getCategoryId()));
            }

            // Expense type filter
            if (filter.getExpenseType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("expenseType"), filter.getExpenseType()));
            }

            // Date range filter
            if (filter.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("expenseDate"), filter.getStartDate()));
            }

            if (filter.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("expenseDate"), filter.getEndDate()));
            }

            // Amount range filter
            if (filter.getMinAmount() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), filter.getMinAmount()));
            }

            if (filter.getMaxAmount() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), filter.getMaxAmount()));
            }

            // Search term (title or description)
            if (filter.getSearchTerm() != null && !filter.getSearchTerm().isBlank()) {
                String searchPattern = "%" + filter.getSearchTerm().toLowerCase() + "%";
                Predicate titlePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")), searchPattern);
                Predicate descriptionPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")), searchPattern);
                predicates.add(criteriaBuilder.or(titlePredicate, descriptionPredicate));
            }

            // Recurring filter
            if (filter.getRecurring() != null) {
                predicates.add(criteriaBuilder.equal(root.get("recurring"), filter.getRecurring()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
