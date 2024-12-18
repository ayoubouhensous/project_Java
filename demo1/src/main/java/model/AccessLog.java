package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AccessLog {
    private Long id;
    private String userName;
    private LocalDateTime timestamp;
    private String status;

    public AccessLog() {}

    public AccessLog(String userName, LocalDateTime timestamp, String status) {
        this.userName = userName;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
