package com.xcelore.service;

import com.xcelore.entity.*;
import com.xcelore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Doctor> suggestDoctors(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        String city = patient.getCity();
        if (!List.of("Delhi", "Noida", "Faridabad").contains(city)) {
            throw new RuntimeException("We are still waiting to expand to your location");
        }

        Speciality spec = mapSymptomToSpeciality(patient.getSymptom());
        List<Doctor> doctors = doctorRepository.findByCityAndSpeciality(city, spec);

        if (doctors.isEmpty()) {
            throw new RuntimeException("There isn’t any doctor present at your location for your symptom");
        }
        return doctors;
    }

    private Speciality mapSymptomToSpeciality(Symptom symptom) {
        return switch (symptom) {
            case ARTHRITIS, BACK_PAIN, TISSUE_INJURIES -> Speciality.ORTHOPAEDIC;
            case DYSMENORRHEA -> Speciality.GYNECOLOGY;
            case SKIN_INFECTION, SKIN_BURN -> Speciality.DERMATOLOGY;
            case EAR_PAIN -> Speciality.ENT;
        };
    }
}
