package com.twistlet.scrapecimb.model.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.repository.AuctionAreaRepository;

public class AuctionAreaPopulateServiceTest {

	@Mock
	private AuctionAreaRepository auctionAreaRepository;

	private AuctionAreaPopulateService sut;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		sut = new AuctionAreaPopulateService(auctionAreaRepository);
	}

	@Test
	public void testShouldPopulateNo() {
		when(auctionAreaRepository.count()).thenReturn(1L);
		sut.populate();
		verify(auctionAreaRepository).count();
		verifyNoMoreInteractions(auctionAreaRepository);
	}

	@Test
	public void testPopulateYes() {
		when(auctionAreaRepository.count()).thenReturn(0L);
		sut.populate();
		verify(auctionAreaRepository).count();
		verify(auctionAreaRepository).save(any(AuctionArea.class));
	}

}
