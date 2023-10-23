package com.etl.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ErrorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errorId;

    @Column
    private String EntityName;

    @Column
    private LocalDateTime timestamp;

    @Column(name = "errorMessage", length = 900)
    private String errorMessage;


    public String getEntityName() {
        return EntityName;
    }

    public void setEntityName(String entityName) {
        EntityName = entityName;
    }

    public Long geterrorId() {
        return errorId;
    }
    public void seterrorId(Long errorId) {
        this.errorId = errorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ErrorEntity(Long errorId, String entityName, LocalDateTime timestamp, String errorMessage) {
        super();
        this.errorId = errorId;
        EntityName = entityName;
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
    }

    public ErrorEntity() {
        super();
    }

    @Override
    public String toString() {
        String formattedTimestamp;
        if (timestamp != null) {
            formattedTimestamp = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            formattedTimestamp = "No timestamp available";
        }
        return "ErrorEntity [errorId=" + errorId + ", EntityName=" + EntityName + ", timestamp=" + formattedTimestamp
                + ", errorMessage=" + errorMessage + "]";
    }

}
