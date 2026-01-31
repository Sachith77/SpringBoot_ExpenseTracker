package com.expensetracker.dto.budget;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.YearMonth;

/**
 * DTO for creating budget requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBudgetRequest {

    @NotNull(message = "Budget amount is required")
    @DecimalMin(value = "0.01", message = "Budget amount must be greater than 0")
    @Digits(integer = 17, fraction = 2, message = "Budget amount format is invalid")
    private BigDecimal budgetAmount;

    @NotNull(message = "Budget month is required")
    private YearMonth budgetMonth;

    private Long categoryId;

    @DecimalMin(value = "1", message = "Alert threshold must be at least 1%")
    @DecimalMax(value = "100", message = "Alert threshold must not exceed 100%")
    private BigDecimal alertThreshold;
}
