package org.hisrc.dbodtc.pte.tests;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import de.schildbach.pte.BahnProvider;
import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.Product;
import de.schildbach.pte.dto.QueryTripsResult;
import de.schildbach.pte.dto.Trip;
import de.schildbach.pte.dto.Trip.Leg;
import de.schildbach.pte.dto.Trip.Public;

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
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 23);
			cal.set(Calendar.MONTH, 3);
			cal.set(Calendar.YEAR, 2015);
			cal.set(Calendar.HOUR, 8);
			cal.set(Calendar.MINUTE, 0);
			//Date date = cal.getTime();

			Date date = new Date();
			
			boolean dep = true;
			Set<Product> products = Product.ALL;
			WalkSpeed walkSpeed = WalkSpeed.NORMAL;
			Accessibility accessibility = Accessibility.NEUTRAL;

			QueryTripsResult result = provider.queryTrips(from, via, to, date, dep, products, walkSpeed, accessibility, null);
			
			
			
			
			for (Trip t : result.trips) {
				System.out.println("---  Trip ---");
				for (Leg l : t.legs) {
					Public p = (Public)l;
					String locationId = p.arrival.id;
					String lineId = p.line.label;

					// remove all letters and other chars except 0-9 from lineId
					lineId = lineId.replaceAll("[^0-9]", "");
					
					// TODO: call DelayService.getAverageDelay() 
					double averageDelay = 12.3456;
					System.out.println("getAverageDelay("+ locationId + "," + lineId +");");
					// TODO
					
					p.averageDelay = averageDelay;
				}
				
				// TODO: delays -> score
				
				t.score = 65.4321;
			}
			
		

			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
