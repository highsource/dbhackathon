package org.hisrc.dbodtc.service;

import java.io.IOException;
import java.util.Date;

import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.QueryTripsResult;

public interface TripService {
	
		public QueryTripsResult queryTrips(Location from, Location to, Date date) throws IOException;
}
