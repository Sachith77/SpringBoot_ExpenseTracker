package com.expensetracker.service.analytics;

import com.expensetracker.dto.response.AnalyticsSummaryResponse;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseType;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public AnalyticsSummaryResponse getSummary() {
        String userId = userService.getCurrentUserId();
        
        List<Expense> expenses = expenseRepository.findByUserId(userId, PageRequest.of(0, 10000)).getContent();
        List<Category> categories = categoryRepository.findByUserIdOrderByNameAsc(userId);
        
        BigDecimal totalExpenses = BigDecimal.ZERO;
        BigDecimal totalIncome = BigDecimal.ZERO;
        Map<String, BigDecimal> expensesByCategory = new HashMap<>();
        
        for (Expense expense : expenses) {
            if (expense.getExpenseType() == ExpenseType.EXPENSE) {
                totalExpenses = totalExpenses.add(expense.getAmount());
                
                // Group by category
                String categoryName = getCategoryName(expense.getCategoryId(), categories);
                expensesByCategory.merge(categoryName, expense.getAmount(), BigDecimal::add);
            } else if (expense.getExpenseType() == ExpenseType.INCOME) {
                totalIncome = totalIncome.add(expense.getAmount());
            }
        }
        
        return AnalyticsSummaryResponse.builder()
                .totalExpenses(totalExpenses)
                .totalIncome(totalIncome)
                .balance(totalIncome.subtract(totalExpenses))
                .expenseCount(expenses.size())
                .categoryCount(categories.size())
                .expensesByCategory(expensesByCategory)
                .build();
    }
    
    private String getCategoryName(String categoryId, List<Category> categories) {
        if (categoryId == null) return "Uncategorized";
        return categories.stream()
                .filter(c -> c.getId().equals(categoryId))
                .map(Category::getName)
                .findFirst()
                .orElse("Unknown");
    }
}
