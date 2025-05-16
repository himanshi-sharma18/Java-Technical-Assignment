package com.xcelore.controller;

import com.xcelore.entity.*;
import com.xcelore.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/doctors")
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.addDoctor(doctor));
    }

    @PostMapping("/patients")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(doctorService.addPatient(patient));
    }

    @GetMapping("/suggest-doctor/{patientId}")
    public ResponseEntity<List<Doctor>> suggestDoctor(@PathVariable Long patientId) {
        return ResponseEntity.ok(doctorService.suggestDoctors(patientId));
    }
}
