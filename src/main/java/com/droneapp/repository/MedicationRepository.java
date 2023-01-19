package com.droneapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.droneapp.entity.Medication;

public interface MedicationRepository extends CrudRepository<Medication, Integer> {
}
