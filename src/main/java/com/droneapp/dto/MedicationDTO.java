package com.droneapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

public class MedicationDTO {

	private Integer id;
	@Pattern(regexp = ("[a-zA-Z0-9_-]*"), message = "MedicationDTO.name allowed only letters, numbers, ‘-‘, ‘_’")
	@NotBlank(message = "MedicationDTO.name Required")
	private String name;
	@PositiveOrZero(message = "MedicationDTO.weight must be 0 or positive")
	@NotNull (message = "MedicationDTO.weight Required")
	private Float weight;
	@Pattern(regexp = ("[A-Z0-9_]*"), message = "MedicationDTO.code allowed only upper case letters, underscore and numbers")
	@NotBlank(message = "MedicationDTO.code Required")
	private String code;
	@NotBlank(message = "MedicationDTO.image Required")
	private String image;

	public MedicationDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}