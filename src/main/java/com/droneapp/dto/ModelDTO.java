package com.droneapp.dto;

import javax.validation.constraints.NotBlank;

public class ModelDTO {

	private Integer id;
	@NotBlank(message = "ModelDTO.name Required")
	private String name;

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

}