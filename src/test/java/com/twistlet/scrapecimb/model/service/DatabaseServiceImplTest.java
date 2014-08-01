package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

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

}
