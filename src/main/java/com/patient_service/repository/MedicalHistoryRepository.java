package com.patient_service.repository;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.patient_service.models.MedicalHistory;
@Repository
public interface MedicalHistoryRepository extends MongoRepository<MedicalHistory,String> {
	// Find all medical history for a specific patient
    List<MedicalHistory> findByPatientIdOrderByVisitDateDesc(String patientId);
    
    // Find paginated medical history for a patient
    Page<MedicalHistory> findByPatientIdOrderByVisitDateDesc(String patientId, Pageable pageable);
    
    // Find medical history by patient and provider
    List<MedicalHistory> findByPatientIdAndProviderId(String patientId, String providerId);
    
    // Find recent medical history (last 30 days)
    @Query("{'patientId': ?0, 'visitDate': {$gte: ?1}}")
    List<MedicalHistory> findRecentByPatientId(String patientId, LocalDateTime since);
    
    // Find by visit type
    List<MedicalHistory> findByPatientIdAndVisitType(String patientId, String visitType);
    
    // Find by status
    List<MedicalHistory> findByPatientIdAndStatus(String patientId, String status);
    
    // Count total visits for a patient
    long countByPatientId(String patientId);
    
    // Find latest visit for a patient
    MedicalHistory findFirstByPatientIdOrderByVisitDateDesc(String patientId);

}
