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
import com.twistlet.scrapecimb.model.service.DatabaseService;

public class IndexControllerTest {

	private IndexController sut;

	private final List<AuctionHouse> list = new ArrayList<AuctionHouse>();;
	@Mock
	DatabaseService databaseService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		when(databaseService.findHighProfitLowCost(10_000.00, 100_000.00))
				.thenReturn(list);
		sut = new IndexController(databaseService);
	}

	@Test
	public void testIndex() {
		ModelAndView mav = sut.index();
		Object listFromModel = mav.getModel().get("list");
		assertThat(listFromModel, is(list));
	}

}
