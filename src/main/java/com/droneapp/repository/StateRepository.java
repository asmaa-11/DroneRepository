package com.droneapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.droneapp.entity.DroneAudit;
import com.droneapp.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {

	State findByName(String name);
}
