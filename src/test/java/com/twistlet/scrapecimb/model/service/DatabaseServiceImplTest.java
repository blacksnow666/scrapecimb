package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.entity.AuctionDate;
import com.twistlet.scrapecimb.model.entity.AuctionDatePrice;
import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.repository.AuctionAreaRepository;
import com.twistlet.scrapecimb.model.repository.AuctionDateRepository;
import com.twistlet.scrapecimb.model.repository.AuctionHouseRepository;

public class DatabaseServiceImplTest {

	DatabaseServiceImpl sut;

	@Mock
	private AuctionHouseRepository auctionHouseRepository;
	@Mock
	private AuctionDateRepository auctionDateRepository;
	@Mock
	private AuctionAreaRepository auctionAreaRepository;

	List<AuctionHouse> listAll = new ArrayList<AuctionHouse>();
	List<AuctionHouse> listNonBumiOnly = new ArrayList<AuctionHouse>();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		sut = new DatabaseServiceImpl(auctionHouseRepository,
				auctionDateRepository, auctionAreaRepository);
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
		ArgumentCaptor<AuctionHouse> houseCaptor = ArgumentCaptor
				.forClass(AuctionHouse.class);
		AuctionHouse auctionHouse = new AuctionHouse();
		auctionHouse.setRef("ID#1");
		auctionHouse.setAuctionDate(new Date());
		auctionHouse.setPriceAuction(10_000.00);
		sut.saveAuctionHouse(auctionHouse);
		verify(auctionHouseRepository).save(houseCaptor.capture());
		assertEquals(new Integer(0), houseCaptor.getValue()
				.getPreviousAuctionCount());
	}

	@Test
	public void testSaveSecondTimeAuctionedHouse() throws ParseException {
		FastDateFormat df = FastDateFormat.getInstance("yyyy-MM-dd hh:mm aa");
		Date auctionDate = df.parse("2010-10-20 10:00 am");
		Date existingDate = DateUtils.truncate(auctionDate, Calendar.DATE);
		Date existingDate1 = DateUtils.addDays(existingDate, -1);
		Date existingDate2 = DateUtils.addDays(existingDate, -2);

		ArgumentCaptor<AuctionHouse> houseCaptor = ArgumentCaptor
				.forClass(AuctionHouse.class);
		ArgumentCaptor<AuctionDate> dateCaptor = ArgumentCaptor
				.forClass(AuctionDate.class);

		AuctionDate item = new AuctionDate();
		AuctionDatePrice auctionDatePrice1 = new AuctionDatePrice();
		auctionDatePrice1.setDate(existingDate1);
		auctionDatePrice1.setPrice(10.00);
		AuctionDatePrice auctionDatePrice2 = new AuctionDatePrice();
		auctionDatePrice2.setDate(existingDate2);
		auctionDatePrice2.setPrice(10.00);
		item.setAuctionDatePrices(Arrays.asList(auctionDatePrice1,
				auctionDatePrice2));
		when(auctionDateRepository.findOne("ID#1")).thenReturn(item);

		AuctionHouse auctionHouse = new AuctionHouse();
		auctionHouse.setAuctionDate(auctionDate);
		auctionHouse.setRef("ID#1");
		auctionHouse.setPriceAuction(10_000.00);
		sut.saveAuctionHouse(auctionHouse);
		verify(auctionHouseRepository).save(houseCaptor.capture());
		verify(auctionDateRepository).save(dateCaptor.capture());
		assertEquals(new Integer(2), houseCaptor.getValue()
				.getPreviousAuctionCount());
		assertEquals(3, dateCaptor.getValue().getAuctionDatePrices().size());

	}

	@Test
	public void testSaveSecondTimeAuctionedHouseReRun() throws ParseException {
		FastDateFormat df = FastDateFormat.getInstance("yyyy-MM-dd hh:mm aa");
		Date auctionDate = df.parse("2010-10-20 10:00 am");
		Date existingDate = DateUtils.truncate(auctionDate, Calendar.DATE);
		Date existingDate1 = DateUtils.addDays(existingDate, -1);

		ArgumentCaptor<AuctionHouse> houseCaptor = ArgumentCaptor
				.forClass(AuctionHouse.class);

		AuctionDatePrice auctionDatePrice = new AuctionDatePrice();
		auctionDatePrice.setDate(existingDate);
		auctionDatePrice.setPrice(10.00);
		AuctionDatePrice auctionDatePrice1 = new AuctionDatePrice();
		auctionDatePrice1.setDate(existingDate1);
		auctionDatePrice1.setPrice(10.00);

		AuctionDate item = new AuctionDate();
		item.setAuctionDatePrices(Arrays.asList(auctionDatePrice,
				auctionDatePrice1));
		when(auctionDateRepository.findOne("ID#1")).thenReturn(item);

		AuctionHouse auctionHouse = new AuctionHouse();
		auctionHouse.setAuctionDate(auctionDate);
		auctionHouse.setRef("ID#1");
		sut.saveAuctionHouse(auctionHouse);
		verify(auctionHouseRepository).save(houseCaptor.capture());
		verify(auctionDateRepository).findOne("ID#1");
		verifyNoMoreInteractions(auctionDateRepository);
		assertEquals(new Integer(1), houseCaptor.getValue()
				.getPreviousAuctionCount());

	}

	@Test
	public void testListAuctionArea() {
		List<AuctionArea> list = new ArrayList<AuctionArea>();
		when(auctionAreaRepository.findAll()).thenReturn(list);
		List<AuctionArea> fromDb = sut.listAuctionArea();
		assertSame(list, fromDb);
	}

	@Test
	public void testSaveAuctionArea() {
		sut.saveAuctionArea(null);

	}
}
