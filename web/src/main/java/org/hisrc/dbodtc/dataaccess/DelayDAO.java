package org.hisrc.dbodtc.dataaccess;

import java.util.Date;

import org.hisrc.dbodts.dto.HistoricDelayMap;

import de.schildbach.pte.dto.HistoricDelay;

public interface DelayDAO {

	public double getAverageDelay(String locationId, int lineId, Date start,
			Date end);
	
	public HistoricDelayMap getHistoricDelayMap(String locationId, int lineId, Date start,
			Date end);
	
}
