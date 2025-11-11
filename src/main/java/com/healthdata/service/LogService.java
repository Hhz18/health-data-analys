package com.healthdata.service;

import com.healthdata.entity.Log;
import java.time.LocalDateTime;
import java.util.List;

public interface LogService {
    List<Log> queryLogs(LocalDateTime startTime, LocalDateTime endTime, String source, String level);
    Log getLogById(Long id);
    void markLogAsRead(Long id);
    void deleteLogById(Long id);
    int getTotalLogCount();
    int getTodayLogCount();
    int getUnprocessedLogCount();
    int getDangerousLogCount();
    List<java.util.Map<String, Object>> getLogLevelDistribution();
    List<java.util.Map<String, Object>> getLogTrend(LocalDateTime startDate, LocalDateTime endDate);
    List<Log> getAllImportantLogs();
    List<Log> getImportantLogsByLevel(String level);
}
