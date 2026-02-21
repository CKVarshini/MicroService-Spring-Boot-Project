package com.pm.patient_management.mapper;

import java.time.LocalDate;

import com.pm.patient_management.dto.PatientRequestDTO;
import com.pm.patient_management.dto.PatientResponseDTO;
import com.pm.patient_management.model.Patient;

// 2. Fetch from Model, Put into DTO
// patientdto.setName(patient.getName()); 
    
// It works like this:
// patientdto.setName  <-- (This is the DTO SETTER)
// patient.getName()   <-- (This is the MODEL GETTER)

public class PatientMapper {
    public static PatientResponseDTO toPatientResponseDTO(Patient patient) {
        PatientResponseDTO patientdto = new PatientResponseDTO();
        patientdto.setId(patient.getId().toString());
        patientdto.setName(patient.getName());
        patientdto.setEmail(patient.getEmail());
        patientdto.setAddress(patient.getAddress());
        patientdto.setDateOfBirth(patient.getDateOfBirth().toString());
        patientdto.setRegisteredDate(patient.getRegisteredDate().toString());
        
        return patientdto;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.now());
        
        return patient;
    }
}
