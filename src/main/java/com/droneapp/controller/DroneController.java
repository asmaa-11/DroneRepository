package com.droneapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.droneapp.dto.DroneDTO;
import com.droneapp.dto.MedicationDTO;
import com.droneapp.exception.BussinessException;
import com.droneapp.service.DroneService;

//creating RestController
@RestController
@RequestMapping("/drone")
public class DroneController {

	@Autowired
	DroneService droneService;

	/*-1 registering a drone;*/
	@PostMapping("/register")
	private String addDrone(@Valid @RequestBody DroneDTO droneDto) throws BussinessException {
		return droneService.addDrone(droneDto);

	}

	/*-2 loading a drone with medication items;*/
	@GetMapping("/loadDronesWithMedications")
	private List<DroneDTO> getAllDrone() {
		return droneService.getAllDrone();
	}

	/*-3 checking loaded medication items for a given drone;*/
	@GetMapping("/getMedicationByDroneSerialNumber/{serialNumber}")
	private List<MedicationDTO> getMedicationByDroneSerialNumber(@PathVariable("serialNumber") String serialNumber) {
		return droneService.getMedicationByDroneSerialNumber(serialNumber);
	}

	/*-4 checking available drones for loading;*/
	@GetMapping("/getDroneWithLoadingState")
	private List<DroneDTO> getDroneWithLoadingState() {
		return droneService.getDroneWithLoadingState();
	}

	/*-5 check drone battery level for a given drone;*/
	@GetMapping("/getBatteryCapacityByDroneSerialNumber/{serialNumber}")
	private String getBatteryCapacityByDroneSerialNumber(@PathVariable("serialNumber") String serialNumber) {
		return droneService.getBatteryCapacityByDroneSerialNumber(serialNumber);
	}
}
