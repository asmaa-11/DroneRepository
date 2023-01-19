package com.droneapp.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//mark class as an Entity 
@Entity
//defining class name as Table name
@Table
public class DroneAudit {

	// mark id as primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// defining id as column name
	@Column
	private Integer id;
	@Column
	private float droneCapacity;
	// defining code as column name
	@Column
	private String auditDate;

	@ManyToOne
	@JoinColumn(name = "drone_id", nullable = false)
	private Drone drone;

	public DroneAudit(float droneCapacity, String auditDate, Drone drone) {
		super();
		this.droneCapacity = droneCapacity;
		this.auditDate = auditDate;
		this.drone = drone;
	}

	public float getDroneCapacity() {
		return droneCapacity;
	}

	public void setDroneCapacity(float droneCapacity) {
		this.droneCapacity = droneCapacity;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Drone getDrone() {
		return drone;
	}

	public void setDrone(Drone drone) {
		this.drone = drone;
	}

}