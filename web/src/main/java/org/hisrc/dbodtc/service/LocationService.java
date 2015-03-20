package org.hisrc.dbodtc.service;

import java.io.IOException;

import de.schildbach.pte.dto.SuggestLocationsResult;

public interface LocationService {

	public SuggestLocationsResult suggestLocations(String constraint)
			throws IOException;
}
