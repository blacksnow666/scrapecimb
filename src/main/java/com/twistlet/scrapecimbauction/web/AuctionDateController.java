package com.twistlet.scrapecimbauction.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.twistlet.scrapecimb.model.entity.AuctionDate;
import com.twistlet.scrapecimb.model.entity.AuctionDatePrice;
import com.twistlet.scrapecimb.model.service.AuctionDateService;

@Controller
public class AuctionDateController {

	private final AuctionDateService auctionDateService;

	@Autowired
	public AuctionDateController(final AuctionDateService auctionDateService) {
		this.auctionDateService = auctionDateService;
	}

	@ResponseBody
	@RequestMapping("/list/auction-date")
	public List<AuctionDatePrice> list(@RequestParam("id") final String id) {
		AuctionDate item = auctionDateService.get(id);
		if (item == null) {
			return Collections.emptyList();
		} else {
			List<AuctionDatePrice> list = item.getAuctionDatePrices();
			Collections.sort(list, new AuctionDatePriceComparator());
			return list;
		}
	}

	private class AuctionDatePriceComparator implements
			Comparator<AuctionDatePrice> {

		@Override
		public int compare(final AuctionDatePrice o1, final AuctionDatePrice o2) {
			return (o1.getDate().compareTo(o2.getDate()));
		}
	}
}
