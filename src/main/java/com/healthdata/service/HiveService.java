package com.healthdata.service;

/**
 * HiveService 用于将 OCR 结果等 JSON 数据导入 Hive，按分类自动建表。
 */
public interface HiveService {
    /**
     * 将 OCR 识别结果导入 Hive，按 category 建表。
     * @param category 分类名（表名）
     * @param ocrJson  OCR 识别结果 JSON 字符串
     */
    void importOcrJson(String category, String ocrJson);
}

