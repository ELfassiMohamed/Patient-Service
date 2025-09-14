package com.patient_service.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient_service.models.Patient;

@Service
public class MessagePublisherService {
private static final Logger logger = LoggerFactory.getLogger(MessagePublisherService.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Value("${rabbitmq.queue.patient-registration}")
    private String patientRegistrationQueue;
    
    private static final String EXCHANGE_NAME = "patient-care-exchange";
    
    public void publishPatientRegistration(Patient patient) {
        try {
            Map<String, Object> message = new HashMap<>();
            message.put("patientId", patient.getId());
            message.put("email", patient.getEmail());
            message.put("fullName", patient.getFullName());
            message.put("registrationDate", LocalDateTime.now());
            message.put("accountStatus", patient.getAccountStatus());
            
            String jsonMessage = objectMapper.writeValueAsString(message);
            
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "patient.registration", jsonMessage);
            
            logger.info("Published patient registration message for: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error publishing patient registration message: {}", e.getMessage(), e);
        }
    }
}
