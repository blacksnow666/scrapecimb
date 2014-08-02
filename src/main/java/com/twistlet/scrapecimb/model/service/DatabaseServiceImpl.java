package com.twistlet.scrapecimb.model.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.twistlet.scrapecimb.model.entity.AuctionDate;
import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.repository.AuctionDateRepository;
import com.twistlet.scrapecimb.model.repository.AuctionHouseRepository;

@Service("databaseService")
public class DatabaseServiceImpl implements DatabaseService {

	private final AuctionHouseRepository auctionHouseRepository;
	private final AuctionDateRepository auctionDateRepository;

	@Autowired
	public DatabaseServiceImpl(
			final AuctionHouseRepository auctionHouseRepository,
			final AuctionDateRepository auctionDateRepository) {
		this.auctionHouseRepository = auctionHouseRepository;
		this.auctionDateRepository = auctionDateRepository;
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
		String ref = auctionHouse.getRef();
		Date dateOnly = DateUtils.truncate(auctionHouse.getAuctionDate(),
				Calendar.DATE);
		AuctionDate item = auctionDateRepository.findOne(ref);
		if (item == null) {
			item = new AuctionDate();
			item.setId(ref);
			item.setDates(new LinkedHashSet<Date>());
		}
		Set<Date> dates = item.getDates();
		if (dates.add(dateOnly)) {
			auctionDateRepository.save(item);
		}
		int count = dates.size();
		auctionHouse.setPreviousAuctionCount(new Integer(count - 1));
		auctionHouseRepository.save(auctionHouse);

	}

}
