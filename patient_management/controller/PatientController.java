package com.pm.patient_management.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.patient_management.dto.PatientRequestDTO;
import com.pm.patient_management.dto.PatientResponseDTO;
import com.pm.patient_management.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/patients")
@Tag(name = "Patient Management API", description = "API for managing patient records, including creating, retrieving, updating, and deleting patients.")
public class PatientController {

    //Dependency Injection of the PatientService : PatientController class recieves its dependency from the Spring container rather than creating it itself. This promotes loose coupling and makes the code more testable and maintainable.
    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

     @GetMapping
     @Operation(summary = "Get All Patients", description = "Retrieve a list of all patients in the system.")
     public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
         List<PatientResponseDTO> patients = patientService.getPatients();
         return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a New Patient", description = "Create a new patient record in the system with the provided patient details.")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {

        PatientResponseDTO createdPatient = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(createdPatient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id, @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO updatedPatient = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(updatedPatient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
