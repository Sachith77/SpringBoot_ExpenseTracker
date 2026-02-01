package com.expensetracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * MongoDB configuration for enabling auditing (CreatedDate, LastModifiedDate).
 */
@Configuration
@EnableMongoAuditing
public class MongoConfig {
}
