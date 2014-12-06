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
	private final DateFormatService dateFormatService;

	@Autowired
	public EnrichAuctionHouseAuctionCountService(
			final AuctionDateRepository auctionDateRepository,
			final DateFormatService dateFormatService) {
		this.auctionDateRepository = auctionDateRepository;
		this.dateFormatService = dateFormatService;
	}

	@Override
	public void enrich(final AuctionHouse auctionHouse) {
		final String ref = auctionHouse.getRef();
		final Date dateOnly = DateUtils.truncate(auctionHouse.getAuctionDate(),
				Calendar.DATE);
		AuctionDate item = auctionDateRepository.findOne(ref);
		if (item == null) {
			item = new AuctionDate();
			item.setId(ref);
			item.setAuctionDatePrices(new ArrayList<AuctionDatePrice>());
		}
		final Set<String> dates = toDateSet(item.getAuctionDatePrices());
		final String formattedDateOnly = dateFormatService.format(dateOnly);
		if (dates.add(formattedDateOnly)) {
			final AuctionDatePrice auctionDatePrice = new AuctionDatePrice();
			auctionDatePrice.setDate(formattedDateOnly);
			auctionDatePrice.setPrice(auctionHouse.getPriceAuction());
			final List<AuctionDatePrice> list = new ArrayList<>(
					item.getAuctionDatePrices());
			list.add(auctionDatePrice);
			item.setAuctionDatePrices(list);
			auctionDateRepository.save(item);
		}
		final int count = dates.size();
		auctionHouse.setPreviousAuctionCount(new Integer(count - 1));
	}

	private Set<String> toDateSet(final List<AuctionDatePrice> auctionDatePrices) {
		final Set<String> set = new LinkedHashSet<String>();
		for (final AuctionDatePrice auctionDatePrice : auctionDatePrices) {
			set.add(auctionDatePrice.getDate());
		}
		return set;
	}

}
