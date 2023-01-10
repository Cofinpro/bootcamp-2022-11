package com.cofinprobootcamp.backend.exceptions;

import java.util.List;

public class CSVArgumentNotValidException extends RuntimeException{
    List<String> violations;
    int rowNumber;
    public CSVArgumentNotValidException(List<String> violations, int rowNumber) {
        this.violations = violations;
        this.rowNumber = rowNumber;
    }

    public List<String> getViolations() {
        return violations;
    }

    public void setViolations(List<String> violations) {
        this.violations = violations;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
