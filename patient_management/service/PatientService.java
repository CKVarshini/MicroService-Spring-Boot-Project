package com.pm.patient_management.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pm.patient_management.dto.PatientRequestDTO;
import com.pm.patient_management.dto.PatientResponseDTO;
import com.pm.patient_management.exception.EmailAlreadyExistsException;
import com.pm.patient_management.exception.PatientNotFoundException;
import com.pm.patient_management.mapper.PatientMapper;
import com.pm.patient_management.model.Patient;
import com.pm.patient_management.repository.PatientRepository;

import jakarta.validation.Valid;

@Service
public class PatientService {
    
    //Dependency Injection of the PatientRepository : Patinentservice class recieves its dependency from the Spring container rather than creating it itself. This promotes loose coupling and makes the code more testable and maintainable.
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    } 

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients =  patientRepository.findAll();
        
        // List<PatientResponseDTO> patientResponseDTOs = patients.stream()
        //     .map(patient -> com.pm.patient_management.mapper.PatientMapper.toPatientResponseDTO(patient))
        //     .toList();

        // return patientResponseDTOs;

        return patients.stream()
            .map(patient -> com.pm.patient_management.mapper.PatientMapper.toPatientResponseDTO(patient))
            .toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {  
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email address already exists : " + patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toPatientResponseDTO(newPatient);
        
    }
     

    //Method to handle the business logic for updating a patient record. It takes the patient's ID and the updated patient data as input, checks if the patient exists, and then updates the patient's information in the database. If the patient does not exist, it throws a ResourceNotFoundException.
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));

            // when we try to upadte the the patient entity , Checks if the new email address already exists for a different patient record
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("Email address already exists : " + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        //patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        
        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toPatientResponseDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        if(!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

}
