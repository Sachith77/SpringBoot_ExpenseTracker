package com.expensetracker.dto.request;

import com.expensetracker.model.ExpenseType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for updating expense requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExpenseRequest {

    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Digits(integer = 17, fraction = 2, message = "Amount format is invalid")
    private BigDecimal amount;

    private ExpenseType expenseType;

    private LocalDate expenseDate;

    private String categoryId;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    private Boolean recurring;

    @Size(max = 20, message = "Recurring frequency must not exceed 20 characters")
    private String recurringFrequency;
}
