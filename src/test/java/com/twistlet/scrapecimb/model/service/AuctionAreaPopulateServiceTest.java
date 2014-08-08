package com.twistlet.scrapecimb.model.service;

import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.repository.AuctionAreaRepository;

public class AuctionAreaPopulateServiceTest {

	@Mock
	private AuctionAreaRepository auctionAreaRepository;

	@Mock
	private JsonService jsonService;

	@Mock
	private ResourceService resourceService;

	@Captor
	private ArgumentCaptor<List<AuctionArea>> listAuctionCaptor;

	private AuctionAreaPopulateService sut;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		sut = new AuctionAreaPopulateService(jsonService, resourceService,
				auctionAreaRepository);
	}

	@Test
	public void testShouldPopulateNo() {
		when(auctionAreaRepository.count()).thenReturn(1L);
		sut.populate();
		verify(auctionAreaRepository).count();
		verifyNoMoreInteractions(auctionAreaRepository);
		verifyZeroInteractions(jsonService, resourceService);
	}

	@Test
	public void testPopulateYesEmptyResource() {
		when(auctionAreaRepository.count()).thenReturn(0L);
		when(resourceService.readLines(any())).thenReturn(null);
		sut.populate();
		verify(auctionAreaRepository).count();
		verify(resourceService).readLines(any());
		verifyNoMoreInteractions(auctionAreaRepository, resourceService,
				jsonService);
	}

	@Test
	public void testPopulateYesWithAllGoodResource() {
		when(auctionAreaRepository.count()).thenReturn(0L);
		when(resourceService.readLines(any())).thenReturn(
				Arrays.asList("A", "B"));
		when(jsonService.toObject(any(), any())).thenReturn(new AuctionArea(),
				new AuctionArea());
		sut.populate();

		verify(auctionAreaRepository).count();
		verify(auctionAreaRepository).save(listAuctionCaptor.capture());
		verify(resourceService).readLines(any());
		verify(jsonService, times(2)).toObject(any(), any());
		verifyNoMoreInteractions(auctionAreaRepository, resourceService,
				jsonService);
		List<AuctionArea> list = listAuctionCaptor.getValue();
		assertThat(list.size(), equalTo(2));
	}

	@Test
	public void testPopulateBadJson() {
		when(auctionAreaRepository.count()).thenReturn(0L);
		when(resourceService.readLines(any())).thenReturn(Arrays.asList("A"));
		when(jsonService.toObject(any(), any())).thenReturn(null);
		sut.populate();
		verify(auctionAreaRepository).count();
		verify(resourceService).readLines(any());
		verify(jsonService).toObject(any(), any());
		verifyNoMoreInteractions(auctionAreaRepository, resourceService,
				jsonService);
	}
}
