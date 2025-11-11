package com.healthdata.mappers;

import com.healthdata.entity.Log;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LogMapper {
    @Insert("INSERT INTO log(time, level, source, important, dangerous, status, content) VALUES(#{time}, #{level}, #{source}, #{important}, #{dangerous}, #{status}, #{content})")
    void insertLog(Log log);

    @Select({
        "<script>",
        "SELECT * FROM log",
        "<where>",
        "  <if test='startTime != null'>AND time &gt;= #{startTime}</if>",
        "  <if test='endTime != null'>AND time &lt;= #{endTime}</if>",
        "  <if test='source != null and source != \"\"'>AND source LIKE CONCAT('%', #{source}, '%')</if>",
        "  <if test='level != null and level != \"\"'>AND level = #{level}</if>",
        "</where>",
        "ORDER BY time DESC",
        "</script>"
    })
    List<Log> queryLogs(@Param("startTime") LocalDateTime startTime,
                       @Param("endTime") LocalDateTime endTime,
                       @Param("source") String source,
                       @Param("level") String level);

    @Select("SELECT * FROM log WHERE id = #{id}")
    Log getLogById(@Param("id") Long id);

    @Update("UPDATE log SET status = 1 WHERE id = #{id}")
    void markLogAsRead(@Param("id") Long id);

    @Delete("DELETE FROM log WHERE id = #{id}")
    void deleteLogById(@Param("id") Long id);

    @Select("SELECT COUNT(*) FROM log")
    int getTotalLogCount();

    @Select("SELECT COUNT(*) FROM log WHERE DATE(time) = CURDATE()")
    int getTodayLogCount();

    @Select("SELECT COUNT(*) FROM log WHERE status = 0")
    int getUnprocessedLogCount();

    @Select("SELECT COUNT(*) FROM log WHERE dangerous = 1")
    int getDangerousLogCount();

    @Select("SELECT level, COUNT(*) as count FROM log GROUP BY level")
    List<java.util.Map<String, Object>> getLogLevelDistribution();

    @Select("SELECT * FROM log WHERE important = 1 ORDER BY time DESC")
    List<Log> getAllImportantLogs();

    @Select("SELECT * FROM log WHERE important = 1 AND level = #{level} ORDER BY time DESC")
    List<Log> getImportantLogsByLevel(@Param("level") String level);

    @Select("SELECT DATE_FORMAT(time, '%Y-%m-%d') as date, COUNT(*) as count FROM log WHERE time >= #{startDate} AND time <= #{endDate} GROUP BY date ORDER BY date ASC")
    List<java.util.Map<String, Object>> getLogTrend(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
