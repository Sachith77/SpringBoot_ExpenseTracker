package com.expensetracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for category-wise spending analysis.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWiseSpendingResponse {

    private BigDecimal totalSpent;
    private List<CategorySpending> categories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategorySpending {
        private Long categoryId;
        private String categoryName;
        private String categoryColor;
        private String categoryIcon;
        private BigDecimal totalAmount;
        private double percentage;
        private int transactionCount;
        private BigDecimal averageTransaction;
        private BigDecimal largestTransaction;
        private BigDecimal smallestTransaction;
    }
}
