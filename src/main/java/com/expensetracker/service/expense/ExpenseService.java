package com.expensetracker.service.expense;

import com.expensetracker.dto.request.CreateExpenseRequest;
import com.expensetracker.dto.response.CategoryResponse;
import com.expensetracker.dto.response.ExpenseResponse;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public ExpenseResponse createExpense(CreateExpenseRequest request) {
        String userId = userService.getCurrentUserId();

        Expense expense = Expense.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .expenseType(request.getExpenseType())
                .expenseDate(request.getExpenseDate())
                .notes(request.getNotes())
                .recurring(request.isRecurring())
                .recurringFrequency(request.getRecurringFrequency())
                .userId(userId)
                .build();

        // Set category if provided
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findByIdAndUserId(request.getCategoryId(), userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));
            expense.setCategoryId(category.getId());
            expense.setCategoryName(category.getName());
            expense.setCategoryColor(category.getColor());
            expense.setCategoryIcon(category.getIcon());
        }

        expense = expenseRepository.save(expense);
        log.info("Expense created: {} for user: {}", expense.getTitle(), userId);
        
        return mapToResponse(expense);
    }

    public List<ExpenseResponse> getAllExpenses() {
        String userId = userService.getCurrentUserId();
        Pageable pageable = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "expenseDate"));
        Page<Expense> expenses = expenseRepository.findByUserId(userId, pageable);
        return expenses.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ExpenseResponse getExpenseById(String id) {
        String userId = userService.getCurrentUserId();
        Expense expense = expenseRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));
        return mapToResponse(expense);
    }

    public ExpenseResponse updateExpense(String id, CreateExpenseRequest request) {
        String userId = userService.getCurrentUserId();
        Expense expense = expenseRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        if (request.getTitle() != null) expense.setTitle(request.getTitle());
        if (request.getDescription() != null) expense.setDescription(request.getDescription());
        if (request.getAmount() != null) expense.setAmount(request.getAmount());
        if (request.getExpenseType() != null) expense.setExpenseType(request.getExpenseType());
        if (request.getExpenseDate() != null) expense.setExpenseDate(request.getExpenseDate());
        if (request.getNotes() != null) expense.setNotes(request.getNotes());
        expense.setRecurring(request.isRecurring());
        if (request.getRecurringFrequency() != null) expense.setRecurringFrequency(request.getRecurringFrequency());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findByIdAndUserId(request.getCategoryId(), userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));
            expense.setCategoryId(category.getId());
            expense.setCategoryName(category.getName());
            expense.setCategoryColor(category.getColor());
            expense.setCategoryIcon(category.getIcon());
        }

        expense = expenseRepository.save(expense);
        return mapToResponse(expense);
    }

    public void deleteExpense(String id) {
        String userId = userService.getCurrentUserId();
        Expense expense = expenseRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));
        expenseRepository.delete(expense);
        log.info("Expense deleted: {}", id);
    }

    private ExpenseResponse mapToResponse(Expense expense) {
        CategoryResponse categoryResponse = null;
        if (expense.getCategoryId() != null) {
            categoryResponse = CategoryResponse.builder()
                    .id(expense.getCategoryId())
                    .name(expense.getCategoryName())
                    .color(expense.getCategoryColor())
                    .icon(expense.getCategoryIcon())
                    .build();
        }

        return ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .expenseType(expense.getExpenseType())
                .expenseDate(expense.getExpenseDate())
                .category(categoryResponse)
                .notes(expense.getNotes())
                .recurring(expense.isRecurring())
                .recurringFrequency(expense.getRecurringFrequency())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }
}
