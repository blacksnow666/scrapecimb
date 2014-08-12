package com.twistlet.scrapecimb.model.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;

public class PhraseServiceImpl implements PhraseService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private DatabaseService databaseService;

	@Async
	@Override
	public void execute() {
		databaseService.removeAllAuctionPhrase();
		List<AuctionHouse> auctionHouses = databaseService.listAuctionHouse();
		int total = auctionHouses.size();
		int index = 0;
		for (AuctionHouse auctionHouse : auctionHouses) {
			index++;
			logger.info("Processing phrase {}/{}", index, total);
			String address = auctionHouse.getAddress();
		}

	}

}
