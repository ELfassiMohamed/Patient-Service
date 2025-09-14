package com.patient_service.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.patient_service.dto.MedicalHistoryDTO;
import com.patient_service.models.AccountStatus;
import com.patient_service.models.MedicalHistory;
import com.patient_service.models.Patient;
import com.patient_service.repository.MedicalHistoryRepository;

@Service
public class MedicalHistoryService {
	@Autowired
    private MedicalHistoryRepository medicalHistoryRepository;
    
    @Autowired
    private PatientService patientService;
    
    public List<MedicalHistoryDTO> getPatientMedicalHistory(String patientId, String currentUserEmail) {
        // Verify the patient exists and the user has permission to view
        Patient patient = patientService.findById(patientId);
        
        // Check if the requesting user is the patient themselves
        if (!patient.getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Access denied: You can only view your own medical history");
        }
        
        // Check if patient account is active
        if (patient.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Access denied: Account must be active to view medical history");
        }
        
        List<MedicalHistory> medicalHistories = medicalHistoryRepository.findByPatientIdOrderByVisitDateDesc(patientId);
        
        return medicalHistories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Page<MedicalHistoryDTO> getPatientMedicalHistoryPaginated(String patientId, String currentUserEmail, 
                                                                   int page, int size) {
        // Verify the patient exists and the user has permission to view
        Patient patient = patientService.findById(patientId);
        
        if (!patient.getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Access denied: You can only view your own medical history");
        }
        
        if (patient.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Access denied: Account must be active to view medical history");
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<MedicalHistory> medicalHistories = medicalHistoryRepository.findByPatientIdOrderByVisitDateDesc(patientId, pageable);
        
        return medicalHistories.map(this::convertToDTO);
    }
    
    public List<MedicalHistoryDTO> getRecentMedicalHistory(String patientId, String currentUserEmail, int days) {
        Patient patient = patientService.findById(patientId);
        
        if (!patient.getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Access denied: You can only view your own medical history");
        }
        
        if (patient.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Access denied: Account must be active to view medical history");
        }
        
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        List<MedicalHistory> recentHistory = medicalHistoryRepository.findRecentByPatientId(patientId, since);
        
        return recentHistory.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public MedicalHistoryDTO getLatestVisit(String patientId, String currentUserEmail) {
        Patient patient = patientService.findById(patientId);
        
        if (!patient.getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Access denied: You can only view your own medical history");
        }
        
        if (patient.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Access denied: Account must be active to view medical history");
        }
        
        MedicalHistory latestVisit = medicalHistoryRepository.findFirstByPatientIdOrderByVisitDateDesc(patientId);
        
        return latestVisit != null ? convertToDTO(latestVisit) : null;
    }
    
    public long getVisitCount(String patientId, String currentUserEmail) {
        Patient patient = patientService.findById(patientId);
        
        if (!patient.getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Access denied: You can only view your own medical history");
        }
        
        return medicalHistoryRepository.countByPatientId(patientId);
    }
    
    // Method to be called by RabbitMQ consumer when new medical history is received
    public MedicalHistory addMedicalHistory(MedicalHistory medicalHistory) {
        return medicalHistoryRepository.save(medicalHistory);
    }
    
    // Method to update existing medical history (called via RabbitMQ)
    public MedicalHistory updateMedicalHistory(String historyId, MedicalHistory updates) {
        MedicalHistory existing = medicalHistoryRepository.findById(historyId)
                .orElseThrow(() -> new RuntimeException("Medical history not found with ID: " + historyId));
        
        // Update fields
        if (updates.getChiefComplaint() != null) {
            existing.setChiefComplaint(updates.getChiefComplaint());
        }
        if (updates.getDiagnosis() != null) {
            existing.setDiagnosis(updates.getDiagnosis());
        }
        if (updates.getTreatmentPlan() != null) {
            existing.setTreatmentPlan(updates.getTreatmentPlan());
        }
        if (updates.getPrescription() != null) {
            existing.setPrescription(updates.getPrescription());
        }
        if (updates.getNotes() != null) {
            existing.setNotes(updates.getNotes());
        }
        if (updates.getVitalSigns() != null) {
            existing.setVitalSigns(updates.getVitalSigns());
        }
        if (updates.getFollowUpInstructions() != null) {
            existing.setFollowUpInstructions(updates.getFollowUpInstructions());
        }
        if (updates.getNextAppointment() != null) {
            existing.setNextAppointment(updates.getNextAppointment());
        }
        if (updates.getStatus() != null) {
            existing.setStatus(updates.getStatus());
        }
        
        return medicalHistoryRepository.save(existing);
    }
    
    private MedicalHistoryDTO convertToDTO(MedicalHistory medicalHistory) {
        MedicalHistoryDTO dto = new MedicalHistoryDTO();
        dto.setId(medicalHistory.getId());
        dto.setProviderName(medicalHistory.getProviderName());
        dto.setVisitType(medicalHistory.getVisitType());
        dto.setVisitDate(medicalHistory.getVisitDate());
        dto.setChiefComplaint(medicalHistory.getChiefComplaint());
        dto.setDiagnosis(medicalHistory.getDiagnosis());
        dto.setTreatmentPlan(medicalHistory.getTreatmentPlan());
        dto.setPrescription(medicalHistory.getPrescription());
        dto.setNotes(medicalHistory.getNotes());
        dto.setVitalSigns(medicalHistory.getVitalSigns());
        dto.setNextAppointment(medicalHistory.getNextAppointment());
        dto.setFollowUpInstructions(medicalHistory.getFollowUpInstructions());
        dto.setAttachments(medicalHistory.getAttachments());
        dto.setStatus(medicalHistory.getStatus());
        return dto;
    }
}
