package org.hisrc.dbodtc.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import org.hisrc.dbodtc.dataaccess.DelayDAO;
import org.hisrc.dbodtc.service.TripService;
import org.springframework.stereotype.Service;

import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.Product;
import de.schildbach.pte.dto.QueryTripsResult;
import de.schildbach.pte.dto.Trip;
import de.schildbach.pte.dto.Trip.Leg;
import de.schildbach.pte.dto.Trip.Public;

@Service
public class TripServiceImpl implements TripService {

	@Inject
	private NetworkProvider provider;

	@Inject
	private DelayDAO delayDAO;

	@Override
	public QueryTripsResult queryTrips(Location from, Location to,
			Date historyStart, Date historyEnd) throws IOException {
		QueryTripsResult result = provider.queryTrips(from, null, to,
				new Date(), true,
				Collections.singleton(Product.HIGH_SPEED_TRAIN),
				WalkSpeed.NORMAL, Accessibility.NEUTRAL, null);

		result = TripScoring.addScores(result);
		for (Trip t : result.trips) {
			for (Leg l : t.legs) {
				Public p = (Public) l;
				String locationId = p.arrival.id;
				String stringLineId = p.line.label;
				stringLineId = stringLineId.replaceAll("[^0-9]", "");
				Integer lineId = Integer.valueOf(stringLineId);
				final double averageDelay = delayDAO.getAverageDelay(
						locationId, lineId, historyStart, historyEnd);
				p.averageDelay = averageDelay;
			}
		}

		return result;
	}

}
