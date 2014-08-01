package com.twistlet.scrapecimb.model.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.repository.AuctionHouseRepository;

@Service
public class DatabaseServiceImpl implements DatabaseService {

	private final AuctionHouseRepository auctionHouseRepository;

	@Autowired
	public DatabaseServiceImpl(
			final AuctionHouseRepository auctionHouseRepository) {
		this.auctionHouseRepository = auctionHouseRepository;
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

}
