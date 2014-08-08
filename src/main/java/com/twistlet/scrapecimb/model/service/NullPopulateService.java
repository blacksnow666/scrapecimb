package com.twistlet.scrapecimb.model.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("populateService")
public class NullPopulateService implements PopulateService {

	@Override
	public void populate() {
	}

}
