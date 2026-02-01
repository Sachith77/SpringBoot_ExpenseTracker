package com.expensetracker.controller.analytics;

import com.expensetracker.dto.response.ApiResponse;
import com.expensetracker.dto.response.AnalyticsSummaryResponse;
import com.expensetracker.service.analytics.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<AnalyticsSummaryResponse>> getSummary() {
        AnalyticsSummaryResponse response = analyticsService.getSummary();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
