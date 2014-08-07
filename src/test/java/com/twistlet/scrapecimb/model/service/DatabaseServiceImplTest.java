package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.repository.AuctionAreaRepository;
import com.twistlet.scrapecimb.model.repository.AuctionHouseRepository;

public class DatabaseServiceImplTest {

	DatabaseServiceImpl sut;

	@Mock
	private AuctionHouseRepository auctionHouseRepository;
	@Mock
	private AuctionAreaRepository auctionAreaRepository;
	@Mock
	private EnrichAuctionHouseService enrichAuctionHouseService;;

	List<AuctionHouse> listAll = new ArrayList<AuctionHouse>();
	List<AuctionHouse> listNonBumiOnly = new ArrayList<AuctionHouse>();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		sut = new DatabaseServiceImpl(auctionHouseRepository,
				auctionAreaRepository, Arrays.asList(enrichAuctionHouseService));
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

	@Test
	public void testSaveAuctionHouse() {
		sut.saveAuctionHouse(null);

	}

	@Test
	public void testRemoveAuctionArea() {
		sut.removeAuctionArea(null);

	}

	@Test
	public void testGetAuctionArea() {
		sut.getAuctionArea(null);

	}
}
