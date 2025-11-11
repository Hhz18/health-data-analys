package com.healthdata.service.impl;

import com.healthdata.entity.Log;
import com.healthdata.mappers.LogMapper;
import com.healthdata.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public List<Log> queryLogs(LocalDateTime startTime, LocalDateTime endTime, String source, String level) {
        return logMapper.queryLogs(startTime, endTime, source, level);
    }

    @Override
    public Log getLogById(Long id) {
        return logMapper.getLogById(id);
    }

    @Override
    public void markLogAsRead(Long id) {
        logMapper.markLogAsRead(id);
    }

    @Override
    public void deleteLogById(Long id) {
        logMapper.deleteLogById(id);
    }

    @Override
    public int getTotalLogCount() {
        return logMapper.getTotalLogCount();
    }

    @Override
    public int getTodayLogCount() {
        return logMapper.getTodayLogCount();
    }

    @Override
    public int getUnprocessedLogCount() {
        return logMapper.getUnprocessedLogCount();
    }

    @Override
    public int getDangerousLogCount() {
        return logMapper.getDangerousLogCount();
    }

    @Override
    public List<java.util.Map<String, Object>> getLogLevelDistribution() {
        return (List)logMapper.getLogLevelDistribution();
    }

    @Override
    public List<java.util.Map<String, Object>> getLogTrend(LocalDateTime startDate, LocalDateTime endDate) {
        // 调用MyBatis XML中的getLogTrend
        return logMapper.getLogTrend(startDate, endDate);
    }

    @Override
    public List<Log> getAllImportantLogs() {
        return logMapper.getAllImportantLogs();
    }

    @Override
    public List<Log> getImportantLogsByLevel(String level) {
        return logMapper.getImportantLogsByLevel(level);
    }
}
