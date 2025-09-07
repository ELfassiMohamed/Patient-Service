package com.patient_service.model;

import java.util.Collection;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Document(collection = "patients")
public class Patient implements UserDetails{
	 	@Id
	    private String id;
	    
	    @NotBlank(message = "Name is required")
	    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	    private String name;
	    
	    @NotBlank(message = "Email is required")
	    @Email(message = "Email should be valid")
	    @Indexed(unique = true)
	    private String email;
	    
	    @NotBlank(message = "Password is required")
	    @Size(min = 6, message = "Password must be at least 6 characters")
	    private String password;
	    
	    private boolean enabled = true;
	    
	    // Constructors
	    public Patient() {}
	    
	    public Patient(String name, String email, String password) {
	        this.name = name;
	        this.email = email;
	        this.password = password;
	    }
	    
	    // UserDetails implementation
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return Collections.emptyList();
	    }
	    
	    @Override
	    public String getUsername() {
	        return email;
	    }
	    
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }
	    
	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }
	    
	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }
	    
	    @Override
	    public boolean isEnabled() {
	        return enabled;
	    }
	    
	    // Getters and Setters
	    public String getId() { return id; }
	    public void setId(String id) { this.id = id; }
	    
	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }
	    
	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }
	    
	    @Override
	    public String getPassword() { return password; }
	    public void setPassword(String password) { this.password = password; }
	    
	    public void setEnabled(boolean enabled) { this.enabled = enabled; }
	}

