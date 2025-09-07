package com.patient_service.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.patient_service.model.Patient;

public interface PatientRepository extends MongoRepository<Patient, String>{
	Optional<Patient> findByEmail(String email);
    boolean existsByEmail(String email);
}
