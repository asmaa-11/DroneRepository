package com.droneapp.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//mark class as an Entity 
@Entity
//defining class name as Table name
@Table
public class Drone {

	// mark id as primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// defining id as column name
	@Column
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// defining name as column serialNumber
	@Column(length = 100)
	private String serialNumber;
	// defining model as column name
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id")
	private Model model;
	// defining weightLimit as column name
	@Column
	private float weightLimit;
	// defining bettaryCapacity as column name
	@Column
	private float bettaryCapacity;
	// defining state as column name
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	private State state;
	// define the one to many relationship [drone(1) ----> medication (m)]
	@OneToMany(mappedBy = "drone", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<Medication> medications;

	public Drone() {
		super();
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	
	public float getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(float weightLimit) {
		this.weightLimit = weightLimit;
	}

	public float getBettaryCapacity() {
		return bettaryCapacity;
	}

	public void setBettaryCapacity(float bettaryCapacity) {
		this.bettaryCapacity = bettaryCapacity;
	}

	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Set<Medication> getMedications() {
		return medications;
	}

	public void setMedications(Set<Medication> medications) {
		this.medications = medications;
	}
}