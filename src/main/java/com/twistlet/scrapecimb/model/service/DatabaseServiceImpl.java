package com.twistlet.scrapecimb.model.service;

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
			final double cost) {
		return auctionHouseRepository
				.findByDifferenceGreaterThanAndPriceAuctionLessThan(profit,
						cost, new PageRequest(0, 250, Direction.DESC,
								"difference"));
	}

}
