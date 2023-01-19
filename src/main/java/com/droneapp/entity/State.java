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
public class State {

	// mark id as primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// defining id as column name
	@Column
	private Integer id;
	// defining name as column name
	@Column
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