package com.twistlet.scrapecimbauction.web;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.service.CrawlService;
import com.twistlet.scrapecimb.model.service.DatabaseService;

public class IndexControllerTest {

	private IndexController sut;

	private final List<AuctionHouse> listNonBumi = new ArrayList<AuctionHouse>();;
	private final List<AuctionHouse> listMixed = new ArrayList<AuctionHouse>();;
	@Mock
	DatabaseService databaseService;

	@Mock
	CrawlService crawlService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		when(
				databaseService.findHighProfitLowCost(10_000.00, 100_000.00,
						false)).thenReturn(listNonBumi);
		when(databaseService.findHighProfitLowCost(10_000.00, 100_000.00, true))
				.thenReturn(listMixed);

		sut = new IndexController(databaseService, crawlService);
	}

	@Test
	public void testIndexNonBumiOnly() {
		ModelAndView mav = sut.index(10_000.00, 100_000.00, false);
		Object listFromModel = mav.getModel().get("list");
		assertThat(listFromModel, is(listNonBumi));
		verifyZeroInteractions(crawlService);
	}

	@Test
	public void testIndexMixed() {
		ModelAndView mav = sut.index(10_000.00, 100_000.00, true);
		Object listFromModel = mav.getModel().get("list");
		assertThat(listFromModel, is(listMixed));
		verifyZeroInteractions(crawlService);
	}

	@Test
	public void testCrawl() {
		assertEquals("redirect:/index", sut.crawl());
		verifyZeroInteractions(databaseService);
	}

}
