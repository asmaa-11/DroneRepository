package com.droneapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.droneapp.entity.Drone;
import com.droneapp.entity.State;

public interface DroneRepository extends CrudRepository<Drone, Integer> {

	Drone findBySerialNumber(String serialNumber);
	List<Drone> findByState(State state);
	
}
