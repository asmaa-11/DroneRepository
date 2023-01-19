package com.droneapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.droneapp.entity.DroneAudit;
import com.droneapp.entity.Model;
import com.droneapp.entity.State;

public interface ModelRepository extends CrudRepository<Model, Integer> {
	Model findByName(String name);

}
