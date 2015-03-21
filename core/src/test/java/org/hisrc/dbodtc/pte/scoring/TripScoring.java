package org.hisrc.dbodtc.pte.scoring;

import de.schildbach.pte.dto.QueryTripsResult;
import de.schildbach.pte.dto.Score;
import de.schildbach.pte.dto.Trip;
import de.schildbach.pte.dto.Trip.Leg;
import de.schildbach.pte.dto.Trip.Public;

public class TripScoring {
	
	public static QueryTripsResult addScores(QueryTripsResult result) {
		for (Trip t : result.trips) {
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
			
			Trip.score = new Score(65.4321);
		}
		return result;
	}
	
}
