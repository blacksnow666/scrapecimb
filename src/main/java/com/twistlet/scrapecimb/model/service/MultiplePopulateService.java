package com.twistlet.scrapecimb.model.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiplePopulateService implements PopulateService {

	private final List<PopulateService> list;

	@Autowired
	public MultiplePopulateService(final List<PopulateService> list) {
		this.list = list;
	}

	@Override
	@PostConstruct
	public void populate() {
		for (PopulateService item : list) {
			if (item == this) {
				continue;
			}
			item.populate();
		}
	}

}
