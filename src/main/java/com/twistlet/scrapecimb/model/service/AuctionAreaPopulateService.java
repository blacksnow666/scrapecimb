package com.twistlet.scrapecimb.model.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.repository.AuctionAreaRepository;

@Service("auctionAreaPopulateService")
@Qualifier("populateService")
public class AuctionAreaPopulateService implements PopulateService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private final JsonService jsonService;
	private final ResourceService resourceService;
	private final AuctionAreaRepository auctionAreaRepository;

	@Autowired
	public AuctionAreaPopulateService(final JsonService jsonService,
			final ResourceService resourceService,
			final AuctionAreaRepository auctionAreaRepository) {
		this.jsonService = jsonService;
		this.resourceService = resourceService;
		this.auctionAreaRepository = auctionAreaRepository;
	}

	@Override
	public void populate() {
		Long count = auctionAreaRepository.count();
		if (count > 0L) {
			return;
		}
		List<AuctionArea> list = new ArrayList<AuctionArea>();
		String resourceName = "initial-auction-area.txt";
		List<String> lines = resourceService.readLines(resourceName);
		if (lines == null) {
			return;
		}
		for (String line : lines) {
			AuctionArea item = jsonService.toObject(line, AuctionArea.class);
			if (item != null) {
				list.add(item);
			}
		}
		if (list.size() > 0) {
			auctionAreaRepository.save(list);
		}
	}
}
