package com.twistlet.scrapecimb.model.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AuctionDate {

	@Id
	private String id;

	private List<AuctionDatePrice> auctionDatePrices;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public List<AuctionDatePrice> getAuctionDatePrices() {
		return auctionDatePrices;
	}

	public void setAuctionDatePrices(final List<AuctionDatePrice> auctionDatePrices) {
		this.auctionDatePrices = auctionDatePrices;
	}

}
