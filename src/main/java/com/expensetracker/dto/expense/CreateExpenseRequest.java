package com.expensetracker.dto.expense;

import com.expensetracker.model.ExpenseType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for creating expense requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Digits(integer = 17, fraction = 2, message = "Amount format is invalid")
    private BigDecimal amount;

    @NotNull(message = "Expense type is required")
    private ExpenseType expenseType;

    @NotNull(message = "Expense date is required")
    private LocalDate expenseDate;

    private Long categoryId;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    private boolean recurring;

    @Size(max = 20, message = "Recurring frequency must not exceed 20 characters")
    private String recurringFrequency;
}
