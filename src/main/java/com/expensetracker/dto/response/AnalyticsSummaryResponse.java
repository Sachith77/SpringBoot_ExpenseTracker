package com.expensetracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsSummaryResponse {
    private BigDecimal totalExpenses;
    private BigDecimal totalIncome;
    private BigDecimal balance;
    private int expenseCount;
    private int categoryCount;
    private Map<String, BigDecimal> expensesByCategory;
}
