package com.twistlet.scrapecimb.model.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(type = "auction_date", indexName = "#{elasticsearch['elasticsearch.index']}")
public class AuctionDate {

	@Id
	private String id;

	@Field(type = FieldType.Nested)
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

	public void setAuctionDatePrices(
			final List<AuctionDatePrice> auctionDatePrices) {
		this.auctionDatePrices = auctionDatePrices;
	}

}
