package org.hisrc.dbodtc.dataaccess;

import java.util.Date;

public interface DelayDAO {

	public Double getAverageDelay(String locationId, Integer lineId,
			Date start, Date end);
}
