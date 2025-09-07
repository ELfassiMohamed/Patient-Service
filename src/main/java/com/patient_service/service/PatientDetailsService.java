package com.patient_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.patient_service.model.Patient;
import com.patient_service.repository.PatientRepository;
@Service
public class PatientDetailsService implements UserDetailsService {
	@Autowired
    PatientRepository patientRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Patient not found with email: " + email));
        
        return patient;
    }
}
