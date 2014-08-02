package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.repository.AuctionHouseRepository;

public class DatabaseServiceImplTest {

	DatabaseServiceImpl sut;

	@Mock
	private AuctionHouseRepository auctionHouseRepository;

	List<AuctionHouse> listAll = new ArrayList<AuctionHouse>();
	List<AuctionHouse> listNonBumiOnly = new ArrayList<AuctionHouse>();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		sut = new DatabaseServiceImpl(auctionHouseRepository);
		when(
				auctionHouseRepository
						.findByDifferenceGreaterThanAndPriceAuctionLessThan(
								eq(10.000), eq(100.00), any(Pageable.class)))
				.thenReturn(listAll);
		when(
				auctionHouseRepository
						.findByDifferenceGreaterThanAndPriceAuctionLessThanAndRestrictionNotIn(
								eq(10.000), eq(100.00), any(),
								any(Pageable.class))).thenReturn(
				listNonBumiOnly);
	}

	@Test
	public void testFindHighProfitLowCostBumiAndNonBumi() {
		List<AuctionHouse> listFromDb = sut
				.findHighProfitLowCost(10, 100, true);
		assertSame(listAll, listFromDb);
	}

	@Test
	public void testFindHighProfitLowCostNonBumiOnly() {
		List<AuctionHouse> listFromDb = sut.findHighProfitLowCost(10, 100,
				false);
		assertSame(listNonBumiOnly, listFromDb);
	}

	@Test
	public void testSaveFirstTimeAuctionedHouse() throws ParseException {
		AuctionHouse auctionHouse = new AuctionHouse();
		sut.saveAuctionHouse(auctionHouse);
		verify(auctionHouseRepository).save(any(AuctionHouse.class));
	}

	public void testSaveSecondTimeAuctionedHouse() throws ParseException {
		FastDateFormat df = FastDateFormat.getInstance("yyyy-MM-dd hh:mm aa");
		Date auctionDate = df.parse("2010-10-20 10:00 am");
		Date date = DateUtils.truncate(auctionDate, Calendar.DATE);

		AuctionHouse auctionHouse = new AuctionHouse();
		auctionHouse.setAuctionDate(auctionDate);
		sut.saveAuctionHouse(auctionHouse);
		verify(auctionHouseRepository).save(any(AuctionHouse.class));

	}

}
