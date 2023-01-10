package com.cofinprobootcamp.backend.exceptions;

public class CSVFormatException extends Exception{
    String error;

    public CSVFormatException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
