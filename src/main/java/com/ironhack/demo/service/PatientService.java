package com.ironhack.demo.service;

import com.ironhack.demo.model.Employee;
import com.ironhack.demo.model.Patient;
import com.ironhack.demo.model.Status;
import com.ironhack.demo.repository.EmployeeRepository;
import com.ironhack.demo.repository.PatientRepository;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PatientService implements IPatientService{

    @Autowired
    private PatientRepository patientRepository;
    public void updatePatient(Integer id, Patient patient) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            Patient newPatient = patientOptional.get();
            newPatient.setName(patient.getName());
            newPatient.setDateOfBirth(patient.getDateOfBirth());
            newPatient.setAdmittedBy(patient.getAdmittedBy());
            patientRepository.save(patient);
        }
    }
}

