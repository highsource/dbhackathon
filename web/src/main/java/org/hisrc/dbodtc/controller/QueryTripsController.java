package org.hisrc.dbodtc.controller;

import java.io.IOException;

import javax.inject.Inject;

import org.hisrc.dbodtc.service.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.LocationType;
import de.schildbach.pte.dto.QueryTripsResult;

@Controller
public class QueryTripsController {

	@Inject
	private TripService service;

	@RequestMapping(value = "/queryTrips", method = RequestMethod.GET)
	@ResponseBody
	public QueryTripsResult queryTrips(
			@RequestParam(value = "fromId", required = true) final String fromId,
			@RequestParam(value = "toId", required = true) final String toId)
			throws IOException {
		final Location fromLocation = new Location(LocationType.STATION,
				fromId, null, null);
		final Location toLocation = new Location(LocationType.STATION, toId,
				null, null);
		return service.queryTrips(fromLocation, toLocation);
	}

}
