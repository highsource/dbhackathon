package org.hisrc.dbodtc.dataaccess;

import java.util.Date;
import java.util.List;

import org.hisrc.dbodtc.dto.HistoricDelayMap;

import de.schildbach.pte.dto.DelayEntry;

public interface DelayDAO {

	public double getAverageDelay(String locationId, int lineId, Date start,
			Date end);
	
	public HistoricDelayMap getHistoricDelayMap(String locationId, int lineId, Date start,
			Date end);
	
	public List<DelayEntry> findDelays(String locationId, int lineId, Date start,
			Date end);
	
}
