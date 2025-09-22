package com.patient_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.patient_service.dto.ProfileCompletionRequest;
import com.patient_service.dto.ProfileStatusResponse;
import com.patient_service.dto.RegisterRequest;
import com.patient_service.models.AccountStatus;
import com.patient_service.models.Patient;
import com.patient_service.repository.PatientRepository;

@Service
public class PatientService implements UserDetailsService{
	@Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
   
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Patient not found with email: " + email));
    }
    
    public Patient registerPatient(String email, String password, RegisterRequest registerRequest) {
        if (patientRepository.existsByEmail(email)) {
            throw new RuntimeException("Patient already exists with email: " + email);
        }
        
        Patient patient = new Patient();
        patient.setEmail(email);
        patient.setPassword(passwordEncoder.encode(password));
        patient.setAccountStatus(AccountStatus.PENDING); // Default status
        
     // Set basic profile information if provided during registration
        if (registerRequest.getFirstName() != null) {
            patient.setFirstName(registerRequest.getFirstName());
        }
        if (registerRequest.getLastName() != null) {
            patient.setLastName(registerRequest.getLastName());
        }
        if (registerRequest.getPhone() != null) {
            patient.setPhone(registerRequest.getPhone());
        }
        if (registerRequest.getDateOfBirth() != null) {
            patient.setDateOfBirth(registerRequest.getDateOfBirth());
        }
        if (registerRequest.getGender() != null) {
            patient.setGender(registerRequest.getGender());
        }
        if (registerRequest.getAddress() != null) {
            patient.setAddress(registerRequest.getAddress());
        }
        if (registerRequest.getCity() != null) {
            patient.setCity(registerRequest.getCity());
        }
        if (registerRequest.getState() != null) {
            patient.setState(registerRequest.getState());
        }
        if (registerRequest.getZipCode() != null) {
            patient.setZipCode(registerRequest.getZipCode());
        }
        if (registerRequest.getCountry() != null) {
            patient.setCountry(registerRequest.getCountry());
        }
        
        Patient savedPatient = patientRepository.save(patient);
        
        
        return savedPatient;
    }
    
    // Complete patient basic profile (patient fills this)
    public Patient completePatientProfile(String patientId, ProfileCompletionRequest request) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
        
        // Update basic information (what patients can edit)
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setPhone(request.getPhone());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setAddress(request.getAddress());
        patient.setCity(request.getCity());
        patient.setState(request.getState());
        patient.setZipCode(request.getZipCode());
        patient.setCountry(request.getCountry());
        
        Patient savedPatient = patientRepository.save(patient);
        
        return savedPatient;
    }
    
    // Get profile status and completion percentage
    public ProfileStatusResponse getProfileStatus(String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
        
        ProfileStatusResponse response = new ProfileStatusResponse(patient.getId(), patient.getEmail(), patient.getAccountStatus());
        
        // Check basic profile completion (patient's responsibility)
        boolean hasPersonalInfo = patient.getFirstName() != null && patient.getLastName() != null && patient.getDateOfBirth() != null;
        boolean hasContactInfo = patient.getPhone() != null && patient.getEmail() != null;
        boolean hasAddressInfo = patient.getAddress() != null && patient.getCity() != null;
        
        
        response.setHasPersonalInfo(hasPersonalInfo);
        response.setHasContactInfo(hasContactInfo);
        response.setHasAddressInfo(hasAddressInfo);
        
        // Calculate completion percentages
        boolean basicProfileComplete = hasPersonalInfo && hasContactInfo;
        
        response.setBasicProfileComplete(basicProfileComplete);
        
        // Calculate overall completion percentage
        int completedSections = 0;
        int totalSections = 5; // personal, contact, address, medical, emergency
        
        if (hasPersonalInfo) completedSections++;
        if (hasContactInfo) completedSections++;
        if (hasAddressInfo) completedSections++;
        
        int completionPercentage = (completedSections * 100) / totalSections;
        response.setCompletionPercentage(completionPercentage);
        
        // Determine next step and message
        if (patient.getAccountStatus() == AccountStatus.PENDING) {
            if (!basicProfileComplete) {
                response.setNextStep("Complete your basic profile information");
                response.setMessage("Please complete your personal details to help providers serve you better");
            } else {
                response.setNextStep("Wait for provider activation");
                response.setMessage("Your profile is complete. Waiting for provider to activate your account");
            }
        } else if (patient.getAccountStatus() == AccountStatus.ACTIVE) {
            response.setNextStep("Profile complete - Account active");
            response.setMessage("Your account is active and you can access all services");
        }
        
        return response;
    }
    
    // Check if patient has completed their part of the profile
    public boolean hasPatientCompletedBasicProfile(Patient patient) {
        return patient.getFirstName() != null && 
               patient.getLastName() != null && 
               patient.getPhone() != null && 
               patient.getDateOfBirth() != null;
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
        
        
        
        return patientRepository.save(existingPatient);
    }
    
    public Patient findById(String patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
    }
}
