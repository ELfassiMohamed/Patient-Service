package com.patient_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MedicalHistoryDTO {
	 private String id;
	    private String providerName;
	    private String visitType;
	    private LocalDateTime visitDate;
	    private String chiefComplaint;
	    private String diagnosis;
	    private String treatmentPlan;
	    private String prescription;
	    private String notes;
	    private String vitalSigns;
	    private LocalDateTime nextAppointment;
	    private String followUpInstructions;
	    private List<String> attachments;
	    private String status;
	    
	    // Constructors
	    public MedicalHistoryDTO() {}
	    
	    // Getters and Setters
	    public String getId() {
	        return id;
	    }
	    
	    public void setId(String id) {
	        this.id = id;
	    }
	    
	    public String getProviderName() {
	        return providerName;
	    }
	    
	    public void setProviderName(String providerName) {
	        this.providerName = providerName;
	    }
	    
	    public String getVisitType() {
	        return visitType;
	    }
	    
	    public void setVisitType(String visitType) {
	        this.visitType = visitType;
	    }
	    
	    public LocalDateTime getVisitDate() {
	        return visitDate;
	    }
	    
	    public void setVisitDate(LocalDateTime visitDate) {
	        this.visitDate = visitDate;
	    }
	    
	    public String getChiefComplaint() {
	        return chiefComplaint;
	    }
	    
	    public void setChiefComplaint(String chiefComplaint) {
	        this.chiefComplaint = chiefComplaint;
	    }
	    
	    public String getDiagnosis() {
	        return diagnosis;
	    }
	    
	    public void setDiagnosis(String diagnosis) {
	        this.diagnosis = diagnosis;
	    }
	    
	    public String getTreatmentPlan() {
	        return treatmentPlan;
	    }
	    
	    public void setTreatmentPlan(String treatmentPlan) {
	        this.treatmentPlan = treatmentPlan;
	    }
	    
	    public String getPrescription() {
	        return prescription;
	    }
	    
	    public void setPrescription(String prescription) {
	        this.prescription = prescription;
	    }
	    
	    public String getNotes() {
	        return notes;
	    }
	    
	    public void setNotes(String notes) {
	        this.notes = notes;
	    }
	    
	    public String getVitalSigns() {
	        return vitalSigns;
	    }
	    
	    public void setVitalSigns(String vitalSigns) {
	        this.vitalSigns = vitalSigns;
	    }
	    
	    public LocalDateTime getNextAppointment() {
	        return nextAppointment;
	    }
	    
	    public void setNextAppointment(LocalDateTime nextAppointment) {
	        this.nextAppointment = nextAppointment;
	    }
	    
	    public String getFollowUpInstructions() {
	        return followUpInstructions;
	    }
	    
	    public void setFollowUpInstructions(String followUpInstructions) {
	        this.followUpInstructions = followUpInstructions;
	    }
	    
	    public List<String> getAttachments() {
	        return attachments;
	    }
	    
	    public void setAttachments(List<String> attachments) {
	        this.attachments = attachments;
	    }
	    
	    public String getStatus() {
	        return status;
	    }
	    
	    public void setStatus(String status) {
	        this.status = status;
	    }
}
