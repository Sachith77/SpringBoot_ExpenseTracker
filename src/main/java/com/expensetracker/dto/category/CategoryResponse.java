package com.expensetracker.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for category responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private String color;
    private String icon;
    private boolean isDefault;
    private int expenseCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
