package com.healthdata.service.impl;

import com.healthdata.service.HiveService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Service
public class HiveServiceImpl implements HiveService {
    private static final Logger logger = LoggerFactory.getLogger(HiveServiceImpl.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public void importOcrJson(String year, String ocrJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(ocrJson);
            // 1. 提取表名（如“主要城市降水量”）
            String tableName = null;
            // 修改为tables_result
            JsonNode tableDetections = root.at("/tables_result");
            if (tableDetections.isMissingNode() || !tableDetections.isArray() || tableDetections.isEmpty()) {
                logger.error("tables_result not found or not array");
                return;
            }
            // 只取第一个表格
            JsonNode table = tableDetections.get(0);
            // 直接用 header 和 body，不再处理 cells
            // 新增：提取 header 和 body
            JsonNode header = table.get("header");
            String tableTitle = null;
            String tableUnit = null;
            if (header != null && header.isArray()) {
                for (JsonNode h : header) {
                    String words = h.path("words").asText("");
                    if (words.contains("单位")) {
                        tableUnit = words;
                    } else if (tableTitle == null) {
                        tableTitle = words;
                    }
                }
            }
            logger.info("Table title: {}", tableTitle);
            logger.info("Table unit: {}", tableUnit);

            // 提取列名
            List<String> columnNames = new ArrayList<>();
            // rowData提前声明，供后续SQL使用
            Map<String, List<String>> rowData = new LinkedHashMap<>();
            JsonNode body = table.get("body");
            if (body != null && body.isArray()) {
                // 先找出第一行的所有列名
                for (JsonNode cell : body) {
                    int rowStart = cell.path("row_start").asInt();
                    if (rowStart == 0) {
                        columnNames.add(cell.path("words").asText(""));
                    }
                }
                logger.info("Column names: {}", columnNames);

                // 提取数据行
                for (JsonNode cell : body) {
                    int rowStart = cell.path("row_start").asInt();
                    int colStart = cell.path("col_start").asInt();
                    String words = cell.path("words").asText("");
                    if (rowStart > 0) {
                        // 拆分换行符，将每个分段作为一列
                        String[] splitWords = words.split("\\n");
                        if (colStart == 0) {
                            // 处理异常识别
                            String rowName = splitWords[0];
                            List<String> rowList = new ArrayList<>();
                            for (int i = 1; i < splitWords.length; i++) {
                                rowList.add(splitWords[i]);
                            }
                            rowData.put(rowName, rowList);
                        } else {
                            // 追加到当前行
                            List<String> lastRow = new ArrayList<>(rowData.values()).get(rowData.size() - 1);
                            for (String w : splitWords) {
                                lastRow.add(w);
                            }
                            // 更新map
                            String lastKey = new ArrayList<>(rowData.keySet()).get(rowData.size() - 1);
                            rowData.put(lastKey, lastRow);
                        }
                    }
                }
                // 打印数据
                for (Map.Entry<String, List<String>> entry : rowData.entrySet()) {
                    logger.info("Row: {} => {}", entry.getKey(), entry.getValue());
                }
            }
            // 你可以在这里继续做表名、字段、数据行的提取和后续插入逻辑

            // 处理列名，保留中文，只去除MySQL不允许的特殊字符
            List<String> safeColumnNames = new ArrayList<>();
            Set<String> colNameSet = new HashSet<>();
            int colIdx = 1;
            for (String col : columnNames) {
                // 允许中英文、数字、下划线，去除其它特殊字符
                String colName = col.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9_]", "");
                if (colName.isEmpty()) {
                    colName = "列" + colIdx;
                }
                // 保证唯一
                while (colNameSet.contains(colName)) {
                    colName = colName + colIdx;
                    colIdx++;
                }
                safeColumnNames.add(colName);
                colNameSet.add(colName);
                colIdx++;
            }
            System.out.println("\n********************************************\n"+tableTitle+"\n********************************************\n");
            // 去掉形如“数字-数字”这种区间前缀（如2-5、10-20等），以及所有前缀数字和下划线
            String noYearTitle = tableTitle != null ? tableTitle.replaceFirst("^(\\d+-\\d+|[\\d_]+)", "") : "";
            // 如果还有数字-数字或数字/下划线前缀，继续去除，直到没有为止
            while (noYearTitle.matches("^(\\d+-\\d+|[\\d_]).*")) {
                noYearTitle = noYearTitle.replaceFirst("^(\\d+-\\d+|[\\d_]+)", "");
            }
            String tableNameForMysql;
            if (tableTitle != null) {
                tableNameForMysql = year + "_" + noYearTitle + (tableUnit != null ? "_" + tableUnit.replaceAll("单位[:：]?", "") : "");
            } else {
                tableNameForMysql = "table" + (tableUnit != null ? "_" + tableUnit.replaceAll("单位[:：]?", "") : "");
            }
            tableNameForMysql = tableNameForMysql.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9_]", "");
            if (tableNameForMysql.length() > 50) tableNameForMysql = tableNameForMysql.substring(0, 50);
            if (columnNames.isEmpty()) {
                logger.error("No column names found, skip table creation");
                return;
            }
            // 建表SQL
            StringBuilder createSql = new StringBuilder("CREATE TABLE IF NOT EXISTS `" + tableNameForMysql + "` (");
            for (String colName : safeColumnNames) {
                createSql.append("`" + colName + "` VARCHAR(255),");
            }
            createSql.deleteCharAt(createSql.length() - 1).append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
            System.out.println("建表SQL: " + createSql);
            logger.info("建表SQL: {}", createSql);
            // 插入SQL
            try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
                stmt.execute(createSql.toString());
                for (Map.Entry<String, List<String>> entry : rowData.entrySet()) {
                    StringBuilder insertSql = new StringBuilder("INSERT INTO `" + tableNameForMysql + "` (");
                    for (String colName : safeColumnNames) {
                        insertSql.append("`" + colName + "`,");
                    }
                    insertSql.deleteCharAt(insertSql.length() - 1).append(") VALUES (");
                    // 行名+数据
                    insertSql.append("'" + entry.getKey().replace("'", "''") + "',");
                    List<String> vals = entry.getValue();
                    for (int i = 0; i < columnNames.size() - 1; i++) {
                        String v = (i < vals.size() ? vals.get(i) : "");
                        insertSql.append("'" + v.replace("'", "''") + "',");
                    }
                    insertSql.deleteCharAt(insertSql.length() - 1).append(");");
                    System.out.println("插入SQL: " + insertSql);
                    logger.info("插入SQL: {}", insertSql);
                    stmt.execute(insertSql.toString());
                }
            } catch (SQLException e) {
                logger.error("MySQL建表或插入失败", e);
            }
        } catch (Exception e) {
            logger.error("Failed to parse OCR JSON or extract table", e);
        }
    }
}
