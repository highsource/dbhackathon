package org.hisrc.dbodtc.service.impl;

import java.io.IOException;

import javax.inject.Inject;

import org.hisrc.dbodtc.service.LocationService;
import org.springframework.stereotype.Service;

import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.dto.SuggestLocationsResult;

@Service
public class LocationServiceImpl implements LocationService {

	@Inject
	private NetworkProvider provider;

	@Override
	public SuggestLocationsResult suggestLocations(String text)
			throws IOException {
		return provider.suggestLocations(text);
	}

}
