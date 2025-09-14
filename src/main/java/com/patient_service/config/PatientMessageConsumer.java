package com.patient_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient_service.models.MedicalHistory;
import com.patient_service.models.Patient;
import com.patient_service.services.MedicalHistoryService;
import com.patient_service.services.PatientService;

@Component
public class PatientMessageConsumer {
	 private static final Logger logger = LoggerFactory.getLogger(PatientMessageConsumer.class);
	    
	    @Autowired
	    private PatientService patientService;
	    
	    @Autowired
	    private MedicalHistoryService medicalHistoryService;
	    
	    @Autowired
	    private ObjectMapper objectMapper;
	    
	    @RabbitListener(queues = "${rabbitmq.queue.patient-activation}")
	    public void handlePatientActivation(String message) {
	        try {
	            logger.info("Received patient activation message: {}", message);
	            
	            JsonNode jsonNode = objectMapper.readTree(message);
	            String patientId = jsonNode.get("patientId").asText();
	            String providerName = jsonNode.has("providerName") ? jsonNode.get("providerName").asText() : null;
	            String providerId = jsonNode.has("providerId") ? jsonNode.get("providerId").asText() : null;
	            
	            // Activate the patient
	            Patient patient = patientService.activatePatient(patientId);
	            
	            // Update provider assignment if provided
	            if (providerName != null) {
	                patient.setAssignedProviderName(providerName);
	                patient.setAssignedProviderId(providerId);
	                patientService.updatePatientProfile(patientId, patient);
	            }
	            
	            logger.info("Successfully activated patient with ID: {}", patientId);
	            
	        } catch (Exception e) {
	            logger.error("Error processing patient activation message: {}", e.getMessage(), e);
	        }
	    }
	    
	    @RabbitListener(queues = "${rabbitmq.queue.medical-updates}")
	    public void handleMedicalUpdates(String message) {
	        try {
	            logger.info("Received medical update message: {}", message);
	            
	            JsonNode jsonNode = objectMapper.readTree(message);
	            String action = jsonNode.get("action").asText(); // "CREATE" or "UPDATE"
	            
	            if ("CREATE".equals(action)) {
	                // Create new medical history record
	                MedicalHistory medicalHistory = objectMapper.treeToValue(jsonNode.get("data"), MedicalHistory.class);
	                medicalHistoryService.addMedicalHistory(medicalHistory);
	                logger.info("Created new medical history record for patient: {}", medicalHistory.getPatientId());
	                
	            } else if ("UPDATE".equals(action)) {
	                // Update existing medical history record
	                String historyId = jsonNode.get("historyId").asText();
	                MedicalHistory updates = objectMapper.treeToValue(jsonNode.get("data"), MedicalHistory.class);
	                medicalHistoryService.updateMedicalHistory(historyId, updates);
	                logger.info("Updated medical history record: {}", historyId);
	            }
	            
	        } catch (Exception e) {
	            logger.error("Error processing medical update message: {}", e.getMessage(), e);
	        }
	    }
}
