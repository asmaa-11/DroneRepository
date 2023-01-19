package com.droneapp.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.droneapp.entity.Drone;
import com.droneapp.entity.DroneAudit;
import com.droneapp.repository.DroneAuditRepository;
import com.droneapp.repository.DroneRepository;

@Component
public class DroneJob {

	@Autowired
	DroneAuditRepository droneAuditRepository;
	@Autowired
	DroneRepository droneRepository;
	static final Logger LOGGER = Logger.getLogger(DroneJob.class.getName());

	/*run every 1 min*/
	@Scheduled(cron = "0 0/1 * * * *")
	public void AuditDroneCapacity() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		droneRepository.findAll().forEach(drone -> {

			Date now = new Date();
			String currentDate = sdf.format(now);

			DroneAudit droneAudit = new DroneAudit(drone.getBettaryCapacity(), currentDate, drone);
			droneAuditRepository.save(droneAudit);
			
			LOGGER.info(" the drone with Id " + drone.getId() + " the battery capacity is " + drone.getBettaryCapacity()
					+ " @ " + currentDate);

		});

	}

}