package com.patient_service.AuthController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patient_service.ENTITY.Patient;
import com.patient_service.JWTService.JwtService;
import com.patient_service.JWTService.PatientService;
import com.patient_service.JwtDTO.AuthRequest;
import com.patient_service.JwtDTO.AuthResponse;
import com.patient_service.JwtDTO.RegisterRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	    @Autowired
	    private PatientService patientService;
	    
	    @Autowired
	    private JwtService jwtService;
	    
	    @Autowired
	    private AuthenticationManager authenticationManager;
	    
	    @PostMapping("/register")
	    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
	        try {
	            Patient patient = patientService.registerPatient(request.getEmail(), request.getPassword());
	            String token = jwtService.generateToken(patient);
	            
	            return ResponseEntity.ok(new AuthResponse(token, "Registration successful", patient.getEmail()));
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body(new AuthResponse(null, "Registration failed: " + e.getMessage(), null));
	        }
	    }
	    
	    @PostMapping("/login")
	    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
	        try {
	            Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
	            );
	            
	            Patient patient = (Patient) authentication.getPrincipal();
	            String token = jwtService.generateToken(patient);
	            
	            return ResponseEntity.ok(new AuthResponse(token, "Login successful", patient.getEmail()));
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body(new AuthResponse(null, "Invalid credentials", null));
	        }
	    }
	    
	    @GetMapping("/profile")
	    public ResponseEntity<Patient> getProfile(Authentication authentication) {
	        Patient patient = (Patient) authentication.getPrincipal();
	        return ResponseEntity.ok(patient);
	    }
}
