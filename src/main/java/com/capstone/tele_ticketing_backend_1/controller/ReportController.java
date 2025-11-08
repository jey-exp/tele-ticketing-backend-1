package com.capstone.tele_ticketing_backend_1.controller;


import com.capstone.tele_ticketing_backend_1.dto.AverageResolutionTimeDto;
import com.capstone.tele_ticketing_backend_1.dto.SatisfactionScoreDto;
import com.capstone.tele_ticketing_backend_1.dto.TimeSeriesDataPointDto;
import com.capstone.tele_ticketing_backend_1.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@PreAuthorize("hasRole('CXO')")
@RequiredArgsConstructor
public class ReportController {

    private final ReportingService reportingService;

    @GetMapping("/ticket-volume")
    public ResponseEntity<List<TimeSeriesDataPointDto>> getTicketVolume() {
        return ResponseEntity.ok(reportingService.getTicketVolumeReport());
    }

    @GetMapping("/resolution-time")
    public ResponseEntity<AverageResolutionTimeDto> getAverageResolutionTime() {
        return ResponseEntity.ok(reportingService.getAverageResolutionTimeReport());
    }

    @GetMapping("/satisfaction-scores")
    public ResponseEntity<List<SatisfactionScoreDto>> getSatisfactionScores() {
        return ResponseEntity.ok(reportingService.getSatisfactionScoreReport());
    }
}
