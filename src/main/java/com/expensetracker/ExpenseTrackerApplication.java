package com.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for Expense Tracker & Budget Management System.
 * 
 * Production-grade Spring Boot backend supporting:
 * - JWT-based authentication
 * - Role-based access control
 * - Expense & income tracking
 * - Budget analytics
 * - File uploads
 * - Email notifications
 * - Rate limiting
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
public class ExpenseTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApplication.class, args);
    }
}
