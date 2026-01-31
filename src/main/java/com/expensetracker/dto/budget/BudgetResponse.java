package com.expensetracker.dto.budget;

import com.expensetracker.dto.category.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 * DTO for budget responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponse {

    private Long id;
    private BigDecimal budgetAmount;
    private BigDecimal spentAmount;
    private BigDecimal remainingBudget;
    private double usagePercentage;
    private YearMonth budgetMonth;
    private BigDecimal alertThreshold;
    private boolean alertSent;
    private boolean overBudget;
    private CategoryResponse category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
