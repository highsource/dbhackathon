package org.hisrc.dbodtc.service;

import de.schildbach.pte.dto.DelayInfo;

public interface DelayService {

	public double getAverageDelay(String locationId, String lineId);

	public DelayInfo getDelay(String locationId, int lineId);
}
