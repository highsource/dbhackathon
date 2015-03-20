package org.hisrc.dbodtc.pte.tests;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import org.junit.Test;

import de.schildbach.pte.BahnProvider;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.LocationType;
import de.schildbach.pte.dto.Product;
import de.schildbach.pte.dto.QueryTripsResult;

public class LocationTest {
	private final BahnProvider provider = new BahnProvider();

	@Test
	public void testTrip() throws IOException {
		final Location fromLocation = new Location(LocationType.STATION,
				"8002549", null, "Hamburg Hbf");
		final Location toLocation = new Location(LocationType.STATION,
				"8500010", null, "Basel SBB");
		final QueryTripsResult queryTrips = provider.queryTrips(fromLocation,
				null, toLocation, new Date(), true,
				// Product.ALL,
				Collections.singleton(Product.HIGH_SPEED_TRAIN),
				WalkSpeed.NORMAL, Accessibility.NEUTRAL, null);
		System.out.println(queryTrips);

	}

}
