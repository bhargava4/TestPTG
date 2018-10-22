package com.ln.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ln.domain.ReturnResponse;
import com.ln.entity.Location;
import com.ln.service.LocationService;

@RestController
public class LocationController {

	@Autowired
	LocationService locationService;

	@RequestMapping(value = "/public/states", method = RequestMethod.GET)
	public ReturnResponse getStates() {
		return ReturnResponse.getHttpStatusResponse("States retrieved successfully", HttpStatus.OK, locationService.findStates());
		//return ResponseEntity.ok().body(locationService.findStates());
	}

	@RequestMapping(value = "/public/locations", method = RequestMethod.GET)
	public ReturnResponse getLocations(@RequestParam(required = false) String state) {
		List<Location> locations = locationService.getLocationsByState(state);
		return ReturnResponse.getHttpStatusResponse("Locations retrieved successfully", HttpStatus.OK, locations);
		//return ResponseEntity.ok().body(locations);
	}

	@RequestMapping(value = "/admin/location", method = RequestMethod.POST)
	public ReturnResponse saveLocation(@RequestBody Location location) {
		locationService.createLocation(location);
		return ReturnResponse.getHttpStatusResponse("Location created successfully", HttpStatus.OK, null);
		//return ResponseEntity.ok().body("Location created successfully");
	}

}
