package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twistlet.scrapecimb.model.entity.AuctionDate;
import com.twistlet.scrapecimb.model.entity.AuctionDatePrice;
import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.repository.AuctionDateRepository;

public class EnrichAuctionHouseAuctionCountServiceTest {

	private EnrichAuctionHouseAuctionCountService sut;
	@Mock
	private AuctionDateRepository auctionDateRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		sut = new EnrichAuctionHouseAuctionCountService(auctionDateRepository);
	}

	@Test
	public void testSaveSecondTimeAuctionedHouse() throws ParseException {
		FastDateFormat df = FastDateFormat.getInstance("yyyy-MM-dd hh:mm aa");
		Date auctionDate = df.parse("2010-10-20 10:00 am");
		Date existingDate = DateUtils.truncate(auctionDate, Calendar.DATE);
		Date existingDate1 = DateUtils.addDays(existingDate, -1);
		Date existingDate2 = DateUtils.addDays(existingDate, -2);

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
		sut.enrich(auctionHouse);
		verify(auctionDateRepository).save(dateCaptor.capture());
		assertEquals(new Integer(2), auctionHouse.getPreviousAuctionCount());
		assertEquals(3, dateCaptor.getValue().getAuctionDatePrices().size());

	}

	@Test
	public void testSaveSecondTimeAuctionedHouseReRun() throws ParseException {
		FastDateFormat df = FastDateFormat.getInstance("yyyy-MM-dd hh:mm aa");
		Date auctionDate = df.parse("2010-10-20 10:00 am");
		Date existingDate = DateUtils.truncate(auctionDate, Calendar.DATE);
		Date existingDate1 = DateUtils.addDays(existingDate, -1);

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
		sut.enrich(auctionHouse);
		verify(auctionDateRepository).findOne("ID#1");
		verifyNoMoreInteractions(auctionDateRepository);
		assertEquals(new Integer(1), auctionHouse.getPreviousAuctionCount());

	}

	@Test
	public void testSaveFirstTimeAuctionedHouse() throws ParseException {
		AuctionHouse auctionHouse = new AuctionHouse();
		auctionHouse.setRef("ID#1");
		auctionHouse.setAuctionDate(new Date());
		auctionHouse.setPriceAuction(10_000.00);
		sut.enrich(auctionHouse);

		assertEquals(new Integer(0), auctionHouse.getPreviousAuctionCount());
	}

}
