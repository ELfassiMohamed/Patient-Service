package com.patient_service.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.patient_service.models.AccountStatus;
import com.patient_service.models.Patient;
import com.patient_service.repository.PatientRepository;

@Service
public class PatientService implements UserDetailsService{
	@Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MessagePublisherService messagePublisherService;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Patient not found with email: " + email));
    }
    
    public Patient registerPatient(String email, String password) {
        if (patientRepository.existsByEmail(email)) {
            throw new RuntimeException("Patient already exists with email: " + email);
        }
        
        Patient patient = new Patient();
        patient.setEmail(email);
        patient.setPassword(passwordEncoder.encode(password));
        patient.setAccountStatus(AccountStatus.PENDING); // Default status
        
        Patient savedPatient = patientRepository.save(patient);
        
        // Publish registration message to Provider Service
        messagePublisherService.publishPatientRegistration(savedPatient);
        
        return savedPatient;
    }
    
    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient not found with email: " + email));
    }
    
    // New methods for account management
    public Patient activatePatient(String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
        
        patient.setAccountStatus(AccountStatus.ACTIVE);
        return patientRepository.save(patient);
    }
    
    public Patient deactivatePatient(String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
        
        patient.setAccountStatus(AccountStatus.INACTIVE);
        return patientRepository.save(patient);
    }
    
    public boolean canAccessMedicalHistory(Patient patient) {
        return patient.getAccountStatus() == AccountStatus.ACTIVE;
    }
    
    public Patient updatePatientProfile(String patientId, Patient profileUpdates) {
        Patient existingPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
        
        // Update personal information
        if (profileUpdates.getFirstName() != null) {
            existingPatient.setFirstName(profileUpdates.getFirstName());
        }
        if (profileUpdates.getLastName() != null) {
            existingPatient.setLastName(profileUpdates.getLastName());
        }
        if (profileUpdates.getPhone() != null) {
            existingPatient.setPhone(profileUpdates.getPhone());
        }
        if (profileUpdates.getDateOfBirth() != null) {
            existingPatient.setDateOfBirth(profileUpdates.getDateOfBirth());
        }
        if (profileUpdates.getGender() != null) {
            existingPatient.setGender(profileUpdates.getGender());
        }
        
        // Update address information
        if (profileUpdates.getAddress() != null) {
            existingPatient.setAddress(profileUpdates.getAddress());
        }
        if (profileUpdates.getCity() != null) {
            existingPatient.setCity(profileUpdates.getCity());
        }
        if (profileUpdates.getState() != null) {
            existingPatient.setState(profileUpdates.getState());
        }
        if (profileUpdates.getZipCode() != null) {
            existingPatient.setZipCode(profileUpdates.getZipCode());
        }
        if (profileUpdates.getCountry() != null) {
            existingPatient.setCountry(profileUpdates.getCountry());
        }
        
        // Update medical information
        if (profileUpdates.getEmergencyContactName() != null) {
            existingPatient.setEmergencyContactName(profileUpdates.getEmergencyContactName());
        }
        if (profileUpdates.getEmergencyContactPhone() != null) {
            existingPatient.setEmergencyContactPhone(profileUpdates.getEmergencyContactPhone());
        }
        if (profileUpdates.getBloodType() != null) {
            existingPatient.setBloodType(profileUpdates.getBloodType());
        }
        if (profileUpdates.getAllergies() != null) {
            existingPatient.setAllergies(profileUpdates.getAllergies());
        }
        if (profileUpdates.getCurrentMedications() != null) {
            existingPatient.setCurrentMedications(profileUpdates.getCurrentMedications());
        }
        if (profileUpdates.getMedicalConditions() != null) {
            existingPatient.setMedicalConditions(profileUpdates.getMedicalConditions());
        }
        
        // Update provider assignment
        if (profileUpdates.getAssignedProviderId() != null) {
            existingPatient.setAssignedProviderId(profileUpdates.getAssignedProviderId());
        }
        if (profileUpdates.getAssignedProviderName() != null) {
            existingPatient.setAssignedProviderName(profileUpdates.getAssignedProviderName());
        }
        
        return patientRepository.save(existingPatient);
    }
    
    public Patient findById(String patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
    }
}
