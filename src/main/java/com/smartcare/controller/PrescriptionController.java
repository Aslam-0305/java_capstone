package com.smartcare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PrescriptionController {

    @PostMapping
    public ResponseEntity<Map<String, String>> createPrescription(@RequestBody Map<String, String> prescription) {
        // For now, this is a placeholder. In production, integrate with MongoDB
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Prescription created successfully");
        response.put("prescriptionId", "PRX-" + System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getPatientPrescriptions(@PathVariable Integer patientId) {
        Map<String, Object> response = new HashMap<>();
        response.put("patientId", patientId);
        response.put("prescriptions", java.util.Arrays.asList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<Map<String, String>> getPrescriptionById(@PathVariable String prescriptionId) {
        Map<String, String> response = new HashMap<>();
        response.put("prescriptionId", prescriptionId);
        response.put("status", "found");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{prescriptionId}")
    public ResponseEntity<Map<String, String>> updatePrescription(
            @PathVariable String prescriptionId,
            @RequestBody Map<String, String> prescription) {
        Map<String, String> response = new HashMap<>();
        response.put("prescriptionId", prescriptionId);
        response.put("status", "updated");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<Void> deletePrescription(@PathVariable String prescriptionId) {
        return ResponseEntity.noContent().build();
    }
}
