package com.expensetracker.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for yearly expense report.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YearlyReportResponse {

    private int year;
    private BigDecimal totalExpenses;
    private BigDecimal totalIncome;
    private BigDecimal netAmount;
    private BigDecimal averageMonthlyExpense;
    private BigDecimal averageMonthlyIncome;
    private int totalTransactions;
    private List<MonthlyData> monthlyData;
    private List<TopCategory> topCategories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyData {
        private int month;
        private String monthName;
        private BigDecimal expenses;
        private BigDecimal income;
        private BigDecimal netAmount;
        private int transactionCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopCategory {
        private Long categoryId;
        private String categoryName;
        private String categoryColor;
        private BigDecimal totalAmount;
        private double percentage;
    }
}
