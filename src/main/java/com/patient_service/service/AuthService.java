package com.patient_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.patient_service.dto.JwtResponse;
import com.patient_service.dto.LoginRequest;
import com.patient_service.dto.RegisterRequest;
import com.patient_service.model.Patient;
import com.patient_service.repository.PatientRepository;
import com.patient_service.security.JwtUtils;
@Service
public class AuthService {
	@Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    PatientRepository patientRepository;
    
    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    JwtUtils jwtUtils;
    
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        
        Patient patient = (Patient) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(patient);
        
        return new JwtResponse(jwt, patient.getId(), patient.getName(), patient.getEmail());
    }
    
    public Patient registerUser(RegisterRequest signUpRequest) {
        // Create new patient
        Patient patient = new Patient(signUpRequest.getName(),
                                    signUpRequest.getEmail(),
                                    encoder.encode(signUpRequest.getPassword()));
        
        return patientRepository.save(patient);
    }
    
    public boolean existsByEmail(String email) {
        return patientRepository.existsByEmail(email);
    }
}
