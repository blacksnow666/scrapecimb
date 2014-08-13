package com.twistlet.scrapecimb.model.service;

import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.entity.AuctionPhrase;

@Service
public class PhraseServiceImpl implements PhraseService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final DatabaseService databaseService;
	private final KeywordService keywordService;

	@Autowired
	public PhraseServiceImpl(final DatabaseService databaseService,
			final KeywordService keywordService) {
		this.databaseService = databaseService;
		this.keywordService = keywordService;
	}

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
			List<String> keywords = keywordService.toKeywords(address);
			for (String keyword : keywords) {
				String state = auctionHouse.getState();
				String area = auctionHouse.getArea();
				List<AuctionPhrase> list = databaseService.findAuctionPhrase(
						keyword, state, area);
				if (list.size() > 1) {
					logger.error("More than 1 {} in {} -> {}", new Object[] {
							keyword, state, area });
				}
				AuctionPhrase auctionPhrase = null;
				if (list.size() == 1) {
					auctionPhrase = list.get(0);
				} else {
					auctionPhrase = new AuctionPhrase();
					auctionPhrase.setName(keyword);
					auctionPhrase.setState(state);
					auctionPhrase.setArea(area);
					auctionPhrase.setCount(0);
					auctionPhrase.setUrl(new LinkedHashSet<String>());
					databaseService.saveAuctionPhrase(auctionPhrase);
				}
				databaseService.addToAuctionPhrase(auctionPhrase.getId(),
						auctionHouse.getUrl());
			}
		}
		logger.info("Processing done");
	}

}
