package org.hisrc.dbodtc.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import org.hisrc.dbodtc.service.TripService;
import org.springframework.stereotype.Service;

import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.Product;
import de.schildbach.pte.dto.QueryTripsResult;

@Service
public class TripServiceImpl implements TripService {

	@Inject
	private NetworkProvider provider;

	@Override
	public QueryTripsResult queryTrips(Location from, Location to)
			throws IOException {
		QueryTripsResult result = provider.queryTrips(from, null, to, new Date(), true,
				Collections.singleton(Product.HIGH_SPEED_TRAIN),
				WalkSpeed.NORMAL, Accessibility.NEUTRAL, null);
		
		result = TripScoring.addScores(result);
		
		return result;
	}

}
