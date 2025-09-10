package com.solvd.enums;

public enum HttpStatus {
    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    NOT_FOUND(404);

    private final int STATUS_CODE;

    HttpStatus(int statusCode) {
        this.STATUS_CODE = statusCode;
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}
