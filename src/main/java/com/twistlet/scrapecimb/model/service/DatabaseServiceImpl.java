package com.twistlet.scrapecimb.model.service;

import java.util.ArrayList;
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

import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.entity.AuctionDate;
import com.twistlet.scrapecimb.model.entity.AuctionDatePrice;
import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.repository.AuctionAreaRepository;
import com.twistlet.scrapecimb.model.repository.AuctionDateRepository;
import com.twistlet.scrapecimb.model.repository.AuctionHouseRepository;

@Service("databaseService")
public class DatabaseServiceImpl implements DatabaseService {

	private final AuctionHouseRepository auctionHouseRepository;
	private final AuctionDateRepository auctionDateRepository;
	private final AuctionAreaRepository auctionAreaRepository;

	@Autowired
	public DatabaseServiceImpl(
			final AuctionHouseRepository auctionHouseRepository,
			final AuctionDateRepository auctionDateRepository,
			final AuctionAreaRepository auctionAreaRepository) {
		this.auctionHouseRepository = auctionHouseRepository;
		this.auctionDateRepository = auctionDateRepository;
		this.auctionAreaRepository = auctionAreaRepository;
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
		populateDatePrice(auctionHouse);
		auctionHouseRepository.save(auctionHouse);

	}

	private void populateDatePrice(final AuctionHouse auctionHouse) {
		String ref = auctionHouse.getRef();
		Date dateOnly = DateUtils.truncate(auctionHouse.getAuctionDate(),
				Calendar.DATE);
		AuctionDate item = auctionDateRepository.findOne(ref);
		if (item == null) {
			item = new AuctionDate();
			item.setId(ref);
			item.setAuctionDatePrices(new ArrayList<AuctionDatePrice>());
		}
		Set<Date> dates = toDateSet(item.getAuctionDatePrices());
		if (dates.add(dateOnly)) {
			AuctionDatePrice auctionDatePrice = new AuctionDatePrice();
			auctionDatePrice.setDate(dateOnly);
			auctionDatePrice.setPrice(auctionHouse.getPriceAuction());
			List<AuctionDatePrice> list = new ArrayList<>(
					item.getAuctionDatePrices());
			list.add(auctionDatePrice);
			item.setAuctionDatePrices(list);
			auctionDateRepository.save(item);
		}
		int count = dates.size();
		auctionHouse.setPreviousAuctionCount(new Integer(count - 1));
	}

	private Set<Date> toDateSet(final List<AuctionDatePrice> auctionDatePrices) {
		Set<Date> set = new LinkedHashSet<Date>();
		for (AuctionDatePrice auctionDatePrice : auctionDatePrices) {
			set.add(auctionDatePrice.getDate());
		}
		return set;
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
