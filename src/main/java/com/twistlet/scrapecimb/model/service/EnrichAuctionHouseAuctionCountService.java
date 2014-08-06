package com.twistlet.scrapecimb.model.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twistlet.scrapecimb.model.entity.AuctionDate;
import com.twistlet.scrapecimb.model.entity.AuctionDatePrice;
import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.repository.AuctionDateRepository;

@Service("enrichAuctionHouseAuctionCountService")
public class EnrichAuctionHouseAuctionCountService implements
		EnrichAuctionHouseService {

	private final AuctionDateRepository auctionDateRepository;

	@Autowired
	public EnrichAuctionHouseAuctionCountService(
			final AuctionDateRepository auctionDateRepository) {
		this.auctionDateRepository = auctionDateRepository;
	}

	@Override
	public void enrich(final AuctionHouse auctionHouse) {
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

}
