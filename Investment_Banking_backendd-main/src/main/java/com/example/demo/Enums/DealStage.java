package com.example.demo.Enums;

public enum DealStage {
    PROSPECT("Prospect"),
    UNDER_EVALUATION("Under Evaluation"),
    TERM_SHEET_SUBMITTED("Term Sheet Submitted"),
    CLOSED("Closed"),
    LOST("Lost");
    
    private final String displayName;
    
    DealStage(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}