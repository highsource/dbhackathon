package org.hisrc.dbodtc.pte.tests;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import de.schildbach.pte.BahnProvider;
import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.LocationType;
import de.schildbach.pte.dto.Product;
import de.schildbach.pte.dto.QueryTripsResult;
import de.schildbach.pte.dto.SuggestLocationsResult;

public class Line30Example {

	public static void main(String[] args) {
		
		NetworkProvider provider;
		
		provider = new BahnProvider();
		
		try {
			List<Location> locationFromList = provider.suggestLocations("Hamburg Hbf").getLocations();
			List<Location> locationToList = provider.suggestLocations("Basel SBB").getLocations();

			//Location from = new Location(LocationType.STATION, "8002549", null, "Hamburg Hbf");
			Location from = locationFromList.get(0);
			
			Location via = null;

			//Location to = new Location(LocationType.STATION, "8500010", null, "Basel SBB");
			Location to = locationToList.get(0);
			
			Date date = new Date();
			boolean dep = true;
			Set<Product> products = Product.ALL;
			WalkSpeed walkSpeed = WalkSpeed.NORMAL;
			Accessibility accessibility = Accessibility.NEUTRAL;

			QueryTripsResult result = provider.queryTrips(from, via, to, date, dep, products, walkSpeed, accessibility, null);
			System.out.println(result.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
