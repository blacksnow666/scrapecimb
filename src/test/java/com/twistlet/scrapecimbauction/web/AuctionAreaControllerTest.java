package com.twistlet.scrapecimbauction.web;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twistlet.scrapecimb.model.service.DatabaseService;

public class AuctionAreaControllerTest {

	private AuctionAreaController sut;

	@Mock
	private DatabaseService databaseService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		sut = new AuctionAreaController(databaseService);
	}

	@Test
	public void testList() {
		sut.list();
	}

	@Test
	public void testSave() {
		sut.save(null);
	}

	@Test
	public void testRemove() {
		sut.remove(null);
	}

	@Test
	public void testCreateAuctionArea() {
		sut.create();
	}

	@Test
	public void testEditString() {
		sut.edit("10");
	}

}
