package com.droneapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.droneapp.entity.DroneAudit;

public interface DroneAuditRepository extends CrudRepository<DroneAudit, Integer> {

}
