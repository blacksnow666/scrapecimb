package com.twistlet.scrapecimb.model.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.MessageChannel;

import com.twistlet.scrapecimb.model.repository.AuctionHouseRepository;

public class CrawlServiceImplTest {

	CrawlServiceImpl sut;

	@Mock
	private AuctionHouseRepository auctionHouseRepository;

	@Mock
	private EchoService echoService;

	@Mock
	MessageChannel messageChannel;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		sut = new CrawlServiceImpl(auctionHouseRepository, echoService,
				messageChannel);
	}

	@Test
	public void testCrawl() {
		when(echoService.echo()).thenReturn("VALUE");
		sut.crawl();
		verify(auctionHouseRepository).deleteAll();
		verify(echoService).echo();
		verify(messageChannel).send(any());
	}

}
