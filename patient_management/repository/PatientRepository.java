package com.pm.patient_management.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pm.patient_management.model.Patient;

@Repository
//Using JPA repository to perform CRUD operations on the database
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id); //This method checks if an email address already exists in the database for a different patient record, which is useful when updating a patient's information to ensure that the new email address is unique.
} 