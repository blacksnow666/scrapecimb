package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

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

public class EnrichAuctionHouseHousingAreaServiceTest {

	private EnrichAuctionHouseHousingAreaService sut;

	private List<AuctionArea> listAuctionArea;
	private AuctionArea auctionArea1;
	private AuctionArea auctionArea2;
	private AuctionArea auctionArea3;
	@Mock
	private AuctionAreaRepository auctionAreaRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		sut = new EnrichAuctionHouseHousingAreaService(auctionAreaRepository);
		auctionArea1 = new AuctionArea();
		auctionArea1.setArea("Shah Alam");
		auctionArea1.setId("1");
		auctionArea1.setState("Selangor");
		auctionArea1.setName("Taman Sri Muda");
		auctionArea1.setExpression(".*[T|t]aman [S|s]e?ri [M|m]uda.*");
		auctionArea2 = new AuctionArea();
		auctionArea2.setArea("Petaling Jaya");
		auctionArea2.setId("2");
		auctionArea2.setState("Selangor");
		auctionArea2.setName("Taman Seri Manja");
		auctionArea2.setExpression(".*[T|t]aman [S|s]e?ri [M|m]anja.*");
		auctionArea3 = new AuctionArea();
		auctionArea3.setArea("Shah Alam");
		auctionArea3.setId("3");
		auctionArea3.setState("Selangor");
		auctionArea3.setName("Anything");
		auctionArea3.setExpression(".*");
	}

	@Test
	public void testEnrichHitZero() {
		listAuctionArea = Arrays.asList(auctionArea1);
		when(
				auctionAreaRepository.findByStateAndArea(eq("Selangor"),
						eq("Petaling Jaya"), any(Pageable.class))).thenReturn(
				listAuctionArea);
		AuctionHouse auctionHouse = new AuctionHouse();
		auctionHouse.setState("Selangor");
		auctionHouse.setArea("Petaling Jaya");
		auctionHouse
				.setAddress("302, Block C, Jalan PJS 3/55, Taman Sri Manja, Petaling Jaya");
		auctionHouse.setHousingArea(null);
		sut.enrich(auctionHouse);
		assertNull(auctionHouse.getHousingArea());
	}

	@Test
	public void testEnrichHitOne() {
		listAuctionArea = Arrays.asList(auctionArea1, auctionArea2);
		when(
				auctionAreaRepository.findByStateAndArea(eq("Selangor"),
						eq("Petaling Jaya"), any(Pageable.class))).thenReturn(
				listAuctionArea);
		AuctionHouse auctionHouse = new AuctionHouse();
		auctionHouse.setState("Selangor");
		auctionHouse.setArea("Petaling Jaya");
		auctionHouse
				.setAddress("302, Block C, Jalan PJS 3/55, Taman Sri Manja, Petaling Jaya");
		auctionHouse.setHousingArea(null);
		sut.enrich(auctionHouse);
		assertEquals("Taman Seri Manja", auctionHouse.getHousingArea());
	}

	@Test
	public void testEnrichHitTwo() {
		listAuctionArea = Arrays.asList(auctionArea1, auctionArea2,
				auctionArea3);
		when(
				auctionAreaRepository.findByStateAndArea(eq("Selangor"),
						eq("Petaling Jaya"), any(Pageable.class))).thenReturn(
				listAuctionArea);
		AuctionHouse auctionHouse = new AuctionHouse();
		auctionHouse.setState("Selangor");
		auctionHouse.setArea("Petaling Jaya");
		auctionHouse
				.setAddress("302, Block C, Jalan PJS 3/55, Taman Sri Manja, Petaling Jaya");
		auctionHouse.setHousingArea(null);
		sut.enrich(auctionHouse);
		assertEquals("Taman Seri Manja/Anything", auctionHouse.getHousingArea());
	}
}
