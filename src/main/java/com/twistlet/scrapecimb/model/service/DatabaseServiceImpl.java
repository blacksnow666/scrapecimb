package com.twistlet.scrapecimb.model.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.repository.AuctionAreaRepository;
import com.twistlet.scrapecimb.model.repository.AuctionHouseRepository;

@Service("databaseService")
public class DatabaseServiceImpl implements DatabaseService {

	private final AuctionHouseRepository auctionHouseRepository;
	private final AuctionAreaRepository auctionAreaRepository;
	private final List<EnrichAuctionHouseService> listEnrichAuctionHouseService;
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public DatabaseServiceImpl(
			final AuctionHouseRepository auctionHouseRepository,
			final AuctionAreaRepository auctionAreaRepository,
			@Qualifier("listEnrichAuctionHouseService") final List<EnrichAuctionHouseService> listEnrichAuctionHouseService) {
		this.auctionHouseRepository = auctionHouseRepository;
		this.auctionAreaRepository = auctionAreaRepository;
		this.listEnrichAuctionHouseService = listEnrichAuctionHouseService;
	}

	@Override
	public List<AuctionHouse> findHighProfitLowCost(final double profit,
			final double value, final boolean includeBumi) {
		if (includeBumi) {
			return auctionHouseRepository
					.findByDifferenceGreaterThanAndPriceAuctionLessThan(profit,
							value, new PageRequest(0, 500, Direction.DESC,
									"difference"));
		} else {
			List<String> list = Arrays
					.asList("Bumiputra Lot", "Malay Reserved");
			return auctionHouseRepository
					.findByDifferenceGreaterThanAndPriceAuctionLessThanAndRestrictionNotIn(
							profit, value, list, new PageRequest(0, 500,
									Direction.DESC, "difference"));
		}
	}

	@Override
	public void saveAuctionHouse(final AuctionHouse auctionHouse) {
		for (EnrichAuctionHouseService enrichAuctionHouseService : listEnrichAuctionHouseService) {
			enrichAuctionHouseService.enrich(auctionHouse);
		}
		auctionHouseRepository.save(auctionHouse);

	}

	@Override
	public List<AuctionArea> listAuctionArea() {
		return auctionAreaRepository.findAll();
	}

	@Override
	public void saveAuctionArea(final AuctionArea item) {
		auctionAreaRepository.save(item);
	}

	@Override
	public void removeAuctionArea(final String id) {
		auctionAreaRepository.delete(id);
	}

	@Override
	public AuctionArea getAuctionArea(final String id) {
		return auctionAreaRepository.findOne(id);
	}
}
