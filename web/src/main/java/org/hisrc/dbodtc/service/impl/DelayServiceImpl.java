package org.hisrc.dbodtc.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hisrc.dbodtc.dataaccess.DelayDAO;
import org.hisrc.dbodtc.service.DelayService;
import org.springframework.stereotype.Service;

import de.schildbach.pte.dto.DelayEntry;
import de.schildbach.pte.dto.DelayInfo;

@Service
public class DelayServiceImpl implements DelayService {

	@Inject
	private DelayDAO delayDAO;

	@Override
	public double getAverageDelay(String locationId, String lineId) {
		return 0;
	}

	@Override
	public DelayInfo getDelay(String locationId, int lineId) {
		final List<DelayEntry> delays = this.delayDAO.findDelays(locationId,
				Integer.valueOf(lineId), new Date(113, 11, 13), new Date(114,
						11, 12));

		long totalDelay = 0;
		int count = 0;
		int maxDelay = 0;
		double averageDelay = 0;
		for (DelayEntry delayEntry : delays) {
			count++;
			totalDelay += delayEntry.delay;
			maxDelay = Math.max(delayEntry.delay, maxDelay);
		}
		if (count > 0) {
			averageDelay = ((double) totalDelay) / count;
		}
		final DelayInfo delayInfo = new DelayInfo();
		delayInfo.maxDelay = maxDelay;
		delayInfo.averageDelay = averageDelay;
		return delayInfo;
	}

}
