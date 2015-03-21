package org.hisrc.dbodtc.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import org.hisrc.dbodtc.service.DelayService;
import org.hisrc.dbodtc.service.TripService;
import org.springframework.stereotype.Service;

import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.DelayInfo;
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
	private DelayService delayService;

	@Override
	public QueryTripsResult queryTrips(Location from, Location to, Date date)
			throws IOException {
		QueryTripsResult result = provider.queryTrips(from, null, to, date,
				true, Collections.singleton(Product.HIGH_SPEED_TRAIN),
				WalkSpeed.NORMAL, Accessibility.NEUTRAL, null);

		// result = TripScoring.addScores(result);
		for (Trip t : result.trips) {
			processTrip(t);
		}

		return result;
	}

	private void processTrip(Trip trip) {
		for (int index = 0; index < trip.legs.size(); index++) {
			Leg currentLeg = trip.legs.get(index);
			if (currentLeg instanceof Public) {
				Public currentPublic = (Public) currentLeg;
				String locationId = currentPublic.arrival.id;
				String stringLineId = currentPublic.line.label;
				stringLineId = stringLineId.replaceAll("[^0-9]", "");
				Integer lineId = Integer.valueOf(stringLineId);

				int transferTime = 0;

				final Date currentLegArrivalTime = currentLeg.getArrivalTime();

				if ((index + 1) < trip.legs.size()) {
					final Leg nextLeg = trip.legs.get(index + 1);
					final Date nextLegDepartureTime = nextLeg
							.getDepartureTime();
					transferTime = (int) (nextLegDepartureTime.getTime() - currentLegArrivalTime
							.getTime()) / (1000 * 60);
				}

				final DelayInfo delayInfo = delayService.getDelay(locationId,
						lineId, transferTime);
				currentPublic.delayInfo = delayInfo;
			}
		}
	}
}
