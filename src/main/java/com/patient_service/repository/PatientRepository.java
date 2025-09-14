package com.patient_service.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.patient_service.models.Patient;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String>{
	Optional<Patient> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
