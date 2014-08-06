package com.twistlet.scrapecimb.model.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.repository.AuctionAreaRepository;

@Service("enrichAuctionHouseHousingAreaService")
public class EnrichAuctionHouseHousingAreaService implements
		EnrichAuctionHouseService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private final AuctionAreaRepository auctionAreaRepository;

	@Autowired
	public EnrichAuctionHouseHousingAreaService(
			final AuctionAreaRepository auctionAreaRepository) {
		this.auctionAreaRepository = auctionAreaRepository;
	}

	@Override
	public void enrich(final AuctionHouse auctionHouse) {
		String state = auctionHouse.getState();
		String area = auctionHouse.getArea();
		List<AuctionArea> list = auctionAreaRepository.findByStateAndArea(
				state, area, new PageRequest(0, 500));
		String matchedArea = null;
		for (AuctionArea item : list) {
			String address = auctionHouse.getAddress().toLowerCase();
			String regex = item.getExpression();
			if (address.matches(regex)) {
				if (matchedArea == null) {
					matchedArea = item.getName();
					logger.info("Setting area: {}", matchedArea);
				} else {
					matchedArea = matchedArea + "/" + item.getName();
					logger.warn("Multiple matches for [{}] - [{}]", address,
							matchedArea);
				}
			}
		}
		auctionHouse.setHousingArea(matchedArea);
	}

}
