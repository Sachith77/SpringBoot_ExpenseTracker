package com.expensetracker.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for expense trends analysis.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseTrendResponse {

    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalAmount;
    private BigDecimal averageDailySpending;
    private List<DailyExpense> dailyExpenses;
    private TrendAnalysis trendAnalysis;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyExpense {
        private LocalDate date;
        private BigDecimal amount;
        private int transactionCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrendAnalysis {
        private String trend; // INCREASING, DECREASING, STABLE
        private double changePercentage;
        private BigDecimal highestSpendingDay;
        private LocalDate highestSpendingDate;
        private BigDecimal lowestSpendingDay;
        private LocalDate lowestSpendingDate;
    }
}
