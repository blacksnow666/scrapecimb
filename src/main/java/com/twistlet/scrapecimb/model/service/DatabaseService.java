package com.twistlet.scrapecimb.model.service;

import java.util.List;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;

public interface DatabaseService {

	List<AuctionHouse> findHighProfitLowCost(double profit, double value,
			boolean includeBumi);
}
