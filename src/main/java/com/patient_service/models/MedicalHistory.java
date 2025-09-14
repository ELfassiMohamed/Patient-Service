package com.patient_service.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "medical_history")
public class MedicalHistory {
	    @Id
	    private String id;
	    
	    @Indexed
	    private String patientId;
	    
	    @Indexed
	    private String providerId;
	    
	    private String providerName;
	    private String visitType; // Consultation, Follow-up, Emergency, etc.
	    private LocalDateTime visitDate;
	    private LocalDateTime createdAt = LocalDateTime.now();
	    private LocalDateTime updatedAt = LocalDateTime.now();
	    
	    // Medical Information
	    private String chiefComplaint;
	    private String diagnosis;
	    private String treatmentPlan;
	    private String prescription;
	    private String notes;
	    private String vitalSigns;
	    
	    // Follow-up Information
	    private LocalDateTime nextAppointment;
	    private String followUpInstructions;
	    private List<String> attachments; // File URLs/paths
	    
	    // Status
	    private String status; // COMPLETED, PENDING, CANCELLED
	    
	    // Constructors
	    public MedicalHistory() {}
	    
	    public MedicalHistory(String patientId, String providerId, String providerName) {
	        this.patientId = patientId;
	        this.providerId = providerId;
	        this.providerName = providerName;
	        this.visitDate = LocalDateTime.now();
	        this.status = "COMPLETED";
	    }
	    
	    // Getters and Setters
	    public String getId() {
	        return id;
	    }
	    
	    public void setId(String id) {
	        this.id = id;
	    }
	    
	    public String getPatientId() {
	        return patientId;
	    }
	    
	    public void setPatientId(String patientId) {
	        this.patientId = patientId;
	    }
	    
	    public String getProviderId() {
	        return providerId;
	    }
	    
	    public void setProviderId(String providerId) {
	        this.providerId = providerId;
	    }
	    
	    public String getProviderName() {
	        return providerName;
	    }
	    
	    public void setProviderName(String providerName) {
	        this.providerName = providerName;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public String getVisitType() {
	        return visitType;
	    }
	    
	    public void setVisitType(String visitType) {
	        this.visitType = visitType;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public LocalDateTime getVisitDate() {
	        return visitDate;
	    }
	    
	    public void setVisitDate(LocalDateTime visitDate) {
	        this.visitDate = visitDate;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }
	    
	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }
	    
	    public LocalDateTime getUpdatedAt() {
	        return updatedAt;
	    }
	    
	    public void setUpdatedAt(LocalDateTime updatedAt) {
	        this.updatedAt = updatedAt;
	    }
	    
	    public String getChiefComplaint() {
	        return chiefComplaint;
	    }
	    
	    public void setChiefComplaint(String chiefComplaint) {
	        this.chiefComplaint = chiefComplaint;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public String getDiagnosis() {
	        return diagnosis;
	    }
	    
	    public void setDiagnosis(String diagnosis) {
	        this.diagnosis = diagnosis;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public String getTreatmentPlan() {
	        return treatmentPlan;
	    }
	    
	    public void setTreatmentPlan(String treatmentPlan) {
	        this.treatmentPlan = treatmentPlan;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public String getPrescription() {
	        return prescription;
	    }
	    
	    public void setPrescription(String prescription) {
	        this.prescription = prescription;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public String getNotes() {
	        return notes;
	    }
	    
	    public void setNotes(String notes) {
	        this.notes = notes;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public String getVitalSigns() {
	        return vitalSigns;
	    }
	    
	    public void setVitalSigns(String vitalSigns) {
	        this.vitalSigns = vitalSigns;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public LocalDateTime getNextAppointment() {
	        return nextAppointment;
	    }
	    
	    public void setNextAppointment(LocalDateTime nextAppointment) {
	        this.nextAppointment = nextAppointment;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public String getFollowUpInstructions() {
	        return followUpInstructions;
	    }
	    
	    public void setFollowUpInstructions(String followUpInstructions) {
	        this.followUpInstructions = followUpInstructions;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public List<String> getAttachments() {
	        return attachments;
	    }
	    
	    public void setAttachments(List<String> attachments) {
	        this.attachments = attachments;
	        this.updatedAt = LocalDateTime.now();
	    }
	    
	    public String getStatus() {
	        return status;
	    }
	    
	    public void setStatus(String status) {
	        this.status = status;
	        this.updatedAt = LocalDateTime.now();
	    }
}
