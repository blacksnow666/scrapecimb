package com.twistlet.scrapecimb.model.service;

import java.util.List;

import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.entity.AuctionHouse;

public interface DatabaseService {

	List<AuctionHouse> findHighProfitLowCost(double profit, double value,
			boolean includeBumi);

	void saveAuctionHouse(AuctionHouse auctionHouse);

	List<AuctionArea> listAuctionArea();

	List<AuctionHouse> listAuctionHouse();

	void saveAuctionArea(AuctionArea item);

	void removeAuctionArea(String id);

	AuctionArea getAuctionArea(String id);

	void removeAllAuctionPhrase();
}
