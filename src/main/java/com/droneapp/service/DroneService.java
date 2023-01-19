package com.droneapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.droneapp.dto.DroneDTO;
import com.droneapp.dto.MedicationDTO;
import com.droneapp.entity.Drone;
import com.droneapp.entity.Medication;
import com.droneapp.entity.Model;
import com.droneapp.entity.State;
import com.droneapp.exception.BussinessException;
import com.droneapp.mapper.DroneMapper;
import com.droneapp.repository.DroneRepository;
import com.droneapp.repository.MedicationRepository;
import com.droneapp.repository.ModelRepository;
import com.droneapp.repository.StateRepository;

//defining the business logic
@Service
public class DroneService {

	@Autowired
	DroneRepository droneRepository;
	@Autowired
	MedicationRepository medicationRepository;
	@Autowired
	ModelRepository modelRepository;
	@Autowired
	StateRepository stateRepository;
	final static String DRONE_LOADED_STATE = "LOADED";
	final static String DRONE_LOADING_STATE = "LOADING";

	/*-1 registering a drone;*/
	public String addDrone(DroneDTO droneDTO) throws BussinessException {
		/*count the registered drones*/
		Long registredDronesCount=droneRepository.count();
		
		if (registredDronesCount >10) {
			throw new BussinessException("Drones Fleet is 10 drones only ");

		}
		/*convert from DTO TO ENTITY*/
		Drone drone = DroneMapper.convertToEntity(droneDTO);
		/* find the id of loaded state */
		State state = stateRepository.findByName(droneDTO.getState().getName());
		/* check the state is exist */
		if (state == null) {
			throw new BussinessException("The State not found");

		}
		drone.setState(state);
		/* check the model */
		Model model = modelRepository.findByName(droneDTO.getModel().getName());
		/* check the model is exist */
		if (model == null) {
			throw new BussinessException("The Model not found");

		}
		drone.setModel(model);
		/* if the Drone in loaded state check the medications weight */
		if (drone.getState().getName().equalsIgnoreCase(DRONE_LOADED_STATE)) {
			if (isDroneWeightLimitExceed(drone)) {
				throw new BussinessException(
						"the drone prevent to be in loaded state because it exceed the drone weight");
			}
		}
		/* if the Drone in loading state check the battery to be above 25% */
		if (drone.getState().getName().equalsIgnoreCase(DRONE_LOADING_STATE)) {
			if (drone.getBettaryCapacity() < 25) {
				throw new BussinessException(
						"the drone prevent to be in LOADING  state because the battery is below 25 %");
			}
		}

		Set<Medication> medications = drone.getMedications();
		drone.setMedications(null);
		/* check if serialNumber duplicate */
		Drone oldDrone = droneRepository.findBySerialNumber(drone.getSerialNumber());
		if (oldDrone != null)
			throw new BussinessException("Serial Number is Duplicate");
		
		drone = droneRepository.save(drone);
		/* save medication */
		for (Medication medication : medications) {
			medication.setDrone(drone);
			medicationRepository.save(medication);
		}
		return "Drone registred successfully";
	}

	/*-2 loading a drone with medication items;*/
	public List<DroneDTO> getAllDrone() {

		List<DroneDTO> droneDTOs = new ArrayList<DroneDTO>();
		droneRepository.findAll().forEach(drone -> {
			droneDTOs.add(DroneMapper.convertToDto(drone, true));
		});

		return droneDTOs;
	}

	/*-3 checking loaded medication items for a given drone;*/
	public List<MedicationDTO> getMedicationByDroneSerialNumber(String serialNumber) {

		List<MedicationDTO> medicationDTOs = new ArrayList<MedicationDTO>();
		/* check if serialNumber exist*/
		Drone drone = droneRepository.findBySerialNumber(serialNumber);
		if (drone != null)
			medicationDTOs = DroneMapper.convertToDto(drone, true).getMedications();

		return medicationDTOs;

	}

	// getting a specific record
	/*-4 checking available drones for loading;*/
	public List<DroneDTO> getDroneWithLoadingState() {

		List<DroneDTO> droneDTOs = new ArrayList<DroneDTO>();
		/*get state object with name loading */
		State state = stateRepository.findByName(DRONE_LOADING_STATE);
		droneRepository.findByState(state).forEach(drone -> {
			droneDTOs.add(DroneMapper.convertToDto(drone, false));
		});

		return droneDTOs;

	}

	// getting a specific record
	/*-5 check drone battery level for a given drone;*/
	public String getBatteryCapacityByDroneSerialNumber(String serialNumber) {

		/* check if serialNumber exist*/
		Drone drone = droneRepository.findBySerialNumber(serialNumber);
		if (drone != null) {
			float bettaryCapacity = DroneMapper.convertToDto(drone, false).getBettaryCapacity();
			return bettaryCapacity + " %";
		} else {
			return "Drone not found";
		}

	}

	private Boolean isDroneWeightLimitExceed(Drone drone) {
		float medicationsWeight = 0;

		for (Medication medication : drone.getMedications()) {
			medicationsWeight += medication.getWeight();

		}

		if (drone.getWeightLimit() < medicationsWeight)
			return true;

		return false;
	}

}