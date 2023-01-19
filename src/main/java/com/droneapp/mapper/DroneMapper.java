package com.droneapp.mapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.droneapp.dto.DroneDTO;
import com.droneapp.dto.MedicationDTO;
import com.droneapp.entity.Drone;
import com.droneapp.entity.Medication;

@Service
public class DroneMapper {

	static ModelMapper modelMapper;

	public static DroneDTO convertToDto(Drone drone, boolean loadMedications) {
		modelMapper = new ModelMapper();

		DroneDTO droneDto = modelMapper.map(drone, DroneDTO.class);
		List<MedicationDTO> medicationDTOs = new ArrayList<>();
		if (loadMedications) {
			if (drone.getMedications() != null && !drone.getMedications().isEmpty()) {
				for (Medication m : drone.getMedications()) {
					MedicationDTO medicationDto = modelMapper.map(m, MedicationDTO.class);
					medicationDTOs.add(medicationDto);
				}

			}
			droneDto.setMedications(medicationDTOs);
		}
		return droneDto;
	}

	public static Drone convertToEntity(DroneDTO droneDto) {
		modelMapper = new ModelMapper();
		Drone drone = modelMapper.map(droneDto, Drone.class);

		return drone;
	}

}
