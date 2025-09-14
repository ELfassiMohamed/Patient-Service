package com.patient_service.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import java.util.Collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Document(collection = "patients")
public class Patient implements UserDetails{
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    // Status & timestamps
    private AccountStatus accountStatus;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Personal info
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;

    // Address
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    // Medical info
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String bloodType;
    private String allergies;
    private String currentMedications;
    private String medicalConditions;

    // Provider info
    private String assignedProviderId;
    private String assignedProviderName;

    // Constructors
    public Patient() {}

    public Patient(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // No roles for now
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public AccountStatus getAccountStatus() { return accountStatus; }
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.updatedAt = LocalDateTime.now();
    }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) {
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.updatedAt = LocalDateTime.now();
    }

    public String getGender() { return gender; }
    public void setGender(String gender) {
        this.gender = gender;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAddress() { return address; }
    public void setAddress(String address) {
        this.address = address;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCity() { return city; }
    public void setCity(String city) {
        this.city = city;
        this.updatedAt = LocalDateTime.now();
    }

    public String getState() { return state; }
    public void setState(String state) {
        this.state = state;
        this.updatedAt = LocalDateTime.now();
    }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCountry() { return country; }
    public void setCountry(String country) {
        this.country = country;
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
        this.updatedAt = LocalDateTime.now();
    }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) {
        this.allergies = allergies;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCurrentMedications() { return currentMedications; }
    public void setCurrentMedications(String currentMedications) {
        this.currentMedications = currentMedications;
        this.updatedAt = LocalDateTime.now();
    }

    public String getMedicalConditions() { return medicalConditions; }
    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAssignedProviderId() { return assignedProviderId; }
    public void setAssignedProviderId(String assignedProviderId) {
        this.assignedProviderId = assignedProviderId;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAssignedProviderName() { return assignedProviderName; }
    public void setAssignedProviderName(String assignedProviderName) {
        this.assignedProviderName = assignedProviderName;
        this.updatedAt = LocalDateTime.now();
    }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setAccountNonExpired(boolean accountNonExpired) { this.accountNonExpired = accountNonExpired; }
    public void setAccountNonLocked(boolean accountNonLocked) { this.accountNonLocked = accountNonLocked; }
    public void setCredentialsNonExpired(boolean credentialsNonExpired) { this.credentialsNonExpired = credentialsNonExpired; }

    // Helper method to get full name
    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        }
        return email; // Fallback to email if name not set
    }

    // Helper method to check if profile is complete
    public boolean isProfileComplete() {
        return firstName != null && lastName != null && phone != null &&
               dateOfBirth != null && address != null && city != null &&
               emergencyContactName != null && emergencyContactPhone != null;
    }
}
