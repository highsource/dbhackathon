package org.hisrc.dbodtc.service.impl;

import org.hisrc.dbodtc.service.DelayService;

public class DummyDelayServiceImpl implements DelayService {

	@Override
	public double getAverageDelay(String locationId, String lineId) {
		// Always 7 Minutes.			
		return 7;
	}

}
