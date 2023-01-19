package com.droneapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//mark class as an Entity 
@Entity
//defining class name as Table name
@Table
public class Medication {

	// mark id as primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// defining id as column name
	@Column
	private Integer id;
	// defining name as column name
	@Column(length = 100)
	private String name;
	// defining weight as column name
	@Column
	private float weight;
	// defining code as column name
	@Column
	private String code;
	// defining image as column name
	@Column
	@Lob
	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drone_id")
	private Drone drone;

	public Medication() {
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

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
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

	public Drone getDrone() {
		return drone;
	}

	public void setDrone(Drone drone) {
		this.drone = drone;
	}

}