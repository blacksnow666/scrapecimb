package com.twistlet.scrapecimb.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.twistlet.scrapecimb.model.repository.AuctionHouseRepository;

@Service
public class CrawlServiceImpl implements CrawlService {

	private final AuctionHouseRepository auctionHouseRepository;
	private final EchoService echoService;
	private final MessageChannel channel;

	@Autowired
	public CrawlServiceImpl(
			final AuctionHouseRepository auctionHouseRepository,
			final EchoService echoService,
			@Qualifier("channelBaseUrl") final MessageChannel channel) {
		super();
		this.auctionHouseRepository = auctionHouseRepository;
		this.echoService = echoService;
		this.channel = channel;
	}

	@Async
	@Override
	public void crawl() {
		auctionHouseRepository.deleteAll();
		String content = echoService.echo();
		Message<String> message = MessageBuilder.withPayload(content).build();
		channel.send(message);
	}
}
