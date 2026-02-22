package com.productmanager.product_manager.exception;

import java.time.LocalDateTime;

public class ErrorEntity {

    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    public ErrorEntity(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}