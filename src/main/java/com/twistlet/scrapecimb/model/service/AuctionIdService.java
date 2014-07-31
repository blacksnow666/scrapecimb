package com.twistlet.scrapecimb.model.service;

import java.util.List;

import org.jsoup.nodes.Document;

public interface AuctionIdService {

	List<Long> listAuctionId(Document document);

}
