package com.expensetracker.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

/**
 * DTO for monthly expense summary.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySummaryResponse {

    private YearMonth month;
    private BigDecimal totalExpenses;
    private BigDecimal totalIncome;
    private BigDecimal netAmount;
    private BigDecimal budget;
    private BigDecimal remainingBudget;
    private double budgetUsagePercentage;
    private int transactionCount;
    private List<CategoryExpense> categoryBreakdown;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryExpense {
        private Long categoryId;
        private String categoryName;
        private String categoryColor;
        private BigDecimal amount;
        private double percentage;
        private int transactionCount;
    }
}
