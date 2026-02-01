package com.expensetracker.dto.request;

import com.expensetracker.model.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for expense filtering criteria.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseFilterRequest {

    private String categoryId;
    
    private ExpenseType expenseType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private String searchTerm;

    private Boolean recurring;
}
