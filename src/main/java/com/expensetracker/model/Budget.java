package com.expensetracker.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Budget entity for setting monthly budget limits per category.
 */
@Document(collection = "budgets")
@CompoundIndexes({
    @CompoundIndex(name = "idx_budget_user_month", def = "{'userId': 1, 'budgetMonth': -1}"),
    @CompoundIndex(name = "idx_budget_user_category_month", def = "{'userId': 1, 'categoryId': 1, 'budgetMonth': 1}")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    private String id;

    private BigDecimal budgetAmount;

    @Builder.Default
    private BigDecimal spentAmount = BigDecimal.ZERO;

    @Indexed
    private YearMonth budgetMonth;

    @Builder.Default
    private BigDecimal alertThreshold = new BigDecimal("80.00"); // 80% by default

    @Builder.Default
    private boolean alertSent = false;

    @Indexed
    private String userId;

    private String categoryId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Business logic methods
    public BigDecimal getRemainingBudget() {
        return budgetAmount.subtract(spentAmount);
    }

    public double getUsagePercentage() {
        if (budgetAmount.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        return spentAmount.divide(budgetAmount, 4, java.math.RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100")).doubleValue();
    }

    public boolean isOverBudget() {
        return spentAmount.compareTo(budgetAmount) > 0;
    }

    public boolean shouldSendAlert() {
        if (alertSent) return false;
        double usagePercentage = getUsagePercentage();
        return usagePercentage >= alertThreshold.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Budget budget)) return false;
        return Objects.equals(id, budget.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
