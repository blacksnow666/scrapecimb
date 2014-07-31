package com.twistlet.scrapecimbauction.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.service.DatabaseService;

@Controller
public class IndexController {

	private final DatabaseService databaseService;

	@Autowired
	public IndexController(final DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@RequestMapping("/index")
	public ModelAndView index() {
		final ModelAndView mav = new ModelAndView();
		final List<AuctionHouse> list = databaseService.findHighProfitLowCost(
				10_000.00, 100_000.00);
		mav.addObject("list", list);
		return mav;
	}
}
