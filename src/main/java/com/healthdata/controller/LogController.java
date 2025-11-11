package com.healthdata.controller;

import com.healthdata.entity.Log;
import com.healthdata.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("")
    public List<Log> queryLogs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String level) {
        return logService.queryLogs(startTime, endTime, source, level);
    }

    @GetMapping("/{id}")
    public Log getLogById(@PathVariable Long id) {
        logService.markLogAsRead(id);
        return logService.getLogById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteLogById(@PathVariable Long id) {
        logService.deleteLogById(id);
    }

    @GetMapping("/stats")
    public java.util.Map<String, Object> getLogStats() {
        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("total", logService.getTotalLogCount());
        stats.put("today", logService.getTodayLogCount());
        stats.put("unprocessed", logService.getUnprocessedLogCount());
        stats.put("dangerous", logService.getDangerousLogCount());
        stats.put("levelDistribution", logService.getLogLevelDistribution());
        return stats;
    }

    @GetMapping("/trend")
    public List<java.util.Map<String, Object>> getLogTrend(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return logService.getLogTrend(startDate, endDate);
    }

    @GetMapping("/important")
    public List<Log> getAllImportantLogs() {
        return logService.getAllImportantLogs();
    }

    @GetMapping("/important/{level}")
    public List<Log> getImportantLogsByLevel(@PathVariable String level) {
        return logService.getImportantLogsByLevel(level);
    }
}
