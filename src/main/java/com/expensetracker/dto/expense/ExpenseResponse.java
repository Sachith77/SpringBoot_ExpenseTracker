package com.expensetracker.dto.expense;

import com.expensetracker.dto.category.CategoryResponse;
import com.expensetracker.model.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for expense responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal amount;
    private ExpenseType expenseType;
    private LocalDate expenseDate;
    private CategoryResponse category;
    private String receiptUrl;
    private String receiptFilename;
    private String notes;
    private boolean recurring;
    private String recurringFrequency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
