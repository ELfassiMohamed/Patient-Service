package com.patient_service.JWTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.patient_service.ENTITY.Patient;
import com.patient_service.REPO.PatientRepository;

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
    
    public Patient registerPatient(String email, String password) {
        if (patientRepository.existsByEmail(email)) {
            throw new RuntimeException("Patient already exists with email: " + email);
        }
        
        Patient patient = new Patient();
        patient.setEmail(email);
        patient.setPassword(passwordEncoder.encode(password));
        
        return patientRepository.save(patient);
    }
    
    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient not found with email: " + email));
    }
}
