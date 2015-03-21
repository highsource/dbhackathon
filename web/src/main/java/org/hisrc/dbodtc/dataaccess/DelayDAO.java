package org.hisrc.dbodtc.dataaccess;

import java.util.Date;

public interface DelayDAO {

	public double getAverageDelay(String locationId, int lineId, Date start,
			Date end);
}
