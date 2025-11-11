package com.healthdata.entity;

import java.time.LocalDateTime;

public class Log {
    private Long id;
    private LocalDateTime time;
    private String level;
    private String source;
    private Boolean important;
    private Boolean dangerous;
    private Boolean status;
    private String content;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public Boolean getImportant() { return important; }
    public void setImportant(Boolean important) { this.important = important; }
    public Boolean getDangerous() { return dangerous; }
    public void setDangerous(Boolean dangerous) { this.dangerous = dangerous; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
