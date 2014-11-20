package com.twistlet.scrapecimbauction.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.service.CrawlService;
import com.twistlet.scrapecimb.model.service.DatabaseService;

@Controller
public class IndexController {

	private final DatabaseService databaseService;
	private final CrawlService crawlService;

	@Autowired
	public IndexController(final DatabaseService databaseService,
			final CrawlService crawlService) {
		this.databaseService = databaseService;
		this.crawlService = crawlService;
	}

	@RequestMapping("/index")
	public ModelAndView index(
			@RequestParam(value = "profit", defaultValue = "10000") final double profit,
			@RequestParam(value = "value", defaultValue = "100000") final double value,
			@RequestParam(value = "nonBumiOnly", defaultValue = "false") final boolean nonBumiOnly) {
		final ModelAndView mav = new ModelAndView();
		final long count = databaseService.count();
		if (count == 0) {
			mav.addObject("list", new ArrayList<AuctionHouse>());
		} else {
			final List<AuctionHouse> list = databaseService
					.findHighProfitLowCost(profit, value, !nonBumiOnly);
			mav.addObject("list", list);
		}
		mav.addObject("profit", profit);
		mav.addObject("value", value);
		mav.addObject("nonBumiOnly", nonBumiOnly);
		return mav;
	}

	@RequestMapping(value = "/crawl", method = RequestMethod.POST)
	public String crawl() {
		crawlService.crawl();
		return "redirect:/index";
	}

}
