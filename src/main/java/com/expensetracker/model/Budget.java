package com.expensetracker.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Budget entity for setting monthly budget limits per category.
 */
@Entity
@Table(name = "budgets", indexes = {
    @Index(name = "idx_budget_user", columnList = "user_id"),
    @Index(name = "idx_budget_category", columnList = "category_id"),
    @Index(name = "idx_budget_month", columnList = "budget_month")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "budget_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal budgetAmount;

    @Column(name = "spent_amount", precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal spentAmount = BigDecimal.ZERO;

    @Column(name = "budget_month", nullable = false)
    private YearMonth budgetMonth;

    @Column(name = "alert_threshold", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal alertThreshold = new BigDecimal("80.00"); // 80% by default

    @Column(name = "alert_sent")
    @Builder.Default
    private boolean alertSent = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
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
