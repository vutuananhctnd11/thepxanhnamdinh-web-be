package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.report.CreateReportRequest;
import com.graduate.be_txnd_fanzone.dto.report.ReportResponse;
import com.graduate.be_txnd_fanzone.service.ReportService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportController {

    ReportService reportService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageableListResponse<ReportResponse>>> getListReport (@RequestParam int page, @RequestParam int limit) {
        ApiResponse<PageableListResponse<ReportResponse>> apiResponse = new ApiResponse<>(reportService.getListReport(page, limit));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createReport (@RequestBody @Valid CreateReportRequest request) {
        reportService.createReport(request);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.CREATED);
    }

    @PatchMapping("/approve/{reportId}")
    public ResponseEntity<ApiResponse<String>> approveReport (@PathVariable Long reportId) {
        reportService.approveReport(reportId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @PatchMapping("/reject/{reportId}")
    public ResponseEntity<ApiResponse<String>> rejectReport (@PathVariable Long reportId) {
        reportService.rejectReport(reportId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }
}
