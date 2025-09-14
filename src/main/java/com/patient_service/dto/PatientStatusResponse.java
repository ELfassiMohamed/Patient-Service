package com.patient_service.dto;

import com.patient_service.models.AccountStatus;

public class PatientStatusResponse {
	private String patientId;
    private String email;
    private AccountStatus status;
    private String statusMessage;
    private boolean canAccessMedicalHistory;
    private boolean profileComplete;
    private String assignedProviderName;
    
    // Constructors
    public PatientStatusResponse() {}
    
    public PatientStatusResponse(String patientId, String email, AccountStatus status, 
                               boolean canAccessMedicalHistory, boolean profileComplete) {
        this.patientId = patientId;
        this.email = email;
        this.status = status;
        this.statusMessage = status.getDescription();
        this.canAccessMedicalHistory = canAccessMedicalHistory;
        this.profileComplete = profileComplete;
    }
    
    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public AccountStatus getStatus() {
        return status;
    }
    
    public void setStatus(AccountStatus status) {
        this.status = status;
        this.statusMessage = status.getDescription();
    }
    
    public String getStatusMessage() {
        return statusMessage;
    }
    
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    public boolean isCanAccessMedicalHistory() {
        return canAccessMedicalHistory;
    }
    
    public void setCanAccessMedicalHistory(boolean canAccessMedicalHistory) {
        this.canAccessMedicalHistory = canAccessMedicalHistory;
    }
    
    public boolean isProfileComplete() {
        return profileComplete;
    }
    
    public void setProfileComplete(boolean profileComplete) {
        this.profileComplete = profileComplete;
    }
    
    public String getAssignedProviderName() {
        return assignedProviderName;
    }
    
    public void setAssignedProviderName(String assignedProviderName) {
        this.assignedProviderName = assignedProviderName;
    }
}
