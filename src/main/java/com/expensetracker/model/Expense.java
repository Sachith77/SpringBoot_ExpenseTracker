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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Expense entity representing financial transactions.
 * Supports both expenses and income entries.
 */
@Document(collection = "expenses")
@CompoundIndexes({
    @CompoundIndex(name = "idx_expense_user_date", def = "{'userId': 1, 'expenseDate': -1}"),
    @CompoundIndex(name = "idx_expense_user_category", def = "{'userId': 1, 'categoryId': 1}")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    private String id;

    private String title;

    private String description;

    private BigDecimal amount;

    @Builder.Default
    private ExpenseType expenseType = ExpenseType.EXPENSE;

    @Indexed
    private LocalDate expenseDate;

    private String receiptUrl;

    private String receiptFilename;

    private String notes;

    @Builder.Default
    private boolean recurring = false;

    private String recurringFrequency; // DAILY, WEEKLY, MONTHLY, YEARLY

    @Indexed
    private String userId;

    @Indexed
    private String categoryId;
    
    // Denormalized category info for faster queries
    private String categoryName;
    private String categoryColor;
    private String categoryIcon;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense expense)) return false;
        return Objects.equals(id, expense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
