package com.twistlet.scrapecimbauction.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.service.DatabaseService;

@Controller
public class AuctionAreaController {

	private final DatabaseService databaseService;

	@Autowired
	public AuctionAreaController(final DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@RequestMapping("/admin/area/list")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", databaseService.listAuctionArea());
		return mav;
	}

	@RequestMapping(value = "/admin/area/save", method = RequestMethod.POST)
	public ModelAndView save(final AuctionArea item) {
		ModelAndView mav = new ModelAndView("redirect:/admin/area/list");
		databaseService.saveAuctionArea(item);
		return mav;
	}

	@RequestMapping(value = "/admin/area/remove", method = RequestMethod.POST)
	public ModelAndView remove(final String id) {
		ModelAndView mav = new ModelAndView("redirect:/admin/area/list");
		databaseService.removeAuctionArea(id);
		return mav;
	}

	@RequestMapping(value = "/admin/area/create", method = RequestMethod.POST)
	public ModelAndView create(final AuctionArea item) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("item", new AuctionArea());
		return mav;
	}

	@RequestMapping(value = "/admin/area/edit", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam("id") final String id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("item", databaseService.getAuctionArea(id));
		return mav;
	}

}
