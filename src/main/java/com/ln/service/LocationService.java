package com.ln.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ln.dao.LocationDao;
import com.ln.entity.Location;
import com.ln.entity.States;

@Service
public class LocationService {

	@Autowired
	private LocationDao locationDao;

	public void createLocation(Location location) {
		locationDao.createLocation(location);
	}

	public List<Location> getLocationsByState(String state) {
		return locationDao.findLocationsByState(state);
	}
	
	public List<States> findStates(){
		return locationDao.findStates();
	}

}
