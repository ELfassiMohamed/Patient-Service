package com.patient_service.models;

public enum AccountStatus {
	    PENDING("Account is pending provider approval"),
	    ACTIVE("Account is active"),
	    INACTIVE("Account has been deactivated");
	    
	    private final String description;
	    
	    AccountStatus(String description) {
	        this.description = description;
	    }
	    
	    public String getDescription() {
	        return description;
	    }
}
