package com.example.demo.Enums;

public enum DealType {
    M_A("M&A"),
    EQUITY_FINANCING("Equity Financing"),
    DEBT_OFFERING("Debt Offering"),
    IPO("IPO");
    
    private final String displayName;
    
    DealType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}