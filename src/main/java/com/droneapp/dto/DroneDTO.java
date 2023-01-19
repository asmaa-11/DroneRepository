package com.droneapp.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class DroneDTO {

	private Integer id;
	@Size(max = 100, message = "SerialNumber Max Length is 100 ")
	@NotBlank(message = "SerialNumber Required")
	private String serialNumber;
	@Valid
	@NotNull(message = "model Required")
	private ModelDTO model;
	@Max(value = 500, message = "weightLimit Max value is 500gr ")
	@NotNull(message = "weightLimit Required")
	@PositiveOrZero(message = "weightLimit must be 0 or positive")
	private Float weightLimit;
	@PositiveOrZero(message = "bettaryCapacity must be 0 or positive")
	@Max(value = 100, message = "bettaryCapacity Max value is 100 ")
	@NotNull(message = "bettaryCapacity Required")
	private Float bettaryCapacity;
	@Valid
	@NotNull(message = "state Required")
	private StateDTO state;
	@Valid
	@NotEmpty(message = "medications Required")
	private List<MedicationDTO> medications;

	public DroneDTO() {
		super();
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Float getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(Float weightLimit) {
		this.weightLimit = weightLimit;
	}

	public Float getBettaryCapacity() {
		return bettaryCapacity;
	}

	public void setBettaryCapacity(Float bettaryCapacity) {
		this.bettaryCapacity = bettaryCapacity;
	}

	public ModelDTO getModel() {
		return model;
	}

	public void setModel(ModelDTO model) {
		this.model = model;
	}

	public StateDTO getState() {
		return state;
	}

	public void setState(StateDTO state) {
		this.state = state;
	}

	public List<MedicationDTO> getMedications() {
		return medications;
	}

	public void setMedications(List<MedicationDTO> medications) {
		this.medications = medications;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}