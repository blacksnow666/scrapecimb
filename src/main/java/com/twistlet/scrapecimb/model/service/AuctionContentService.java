package com.twistlet.scrapecimb.model.service;

import java.util.Map;

import org.jsoup.nodes.Document;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;

public interface AuctionContentService {

	Map<String, String> toMap(Document document);

	AuctionHouse toAuctionHouse(Map<String, String> map);
}
