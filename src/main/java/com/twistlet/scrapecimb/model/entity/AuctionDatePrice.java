package com.twistlet.scrapecimb.model.entity;

import java.util.Date;

public class AuctionDatePrice {

	private Date date;
	private double price;

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

}
