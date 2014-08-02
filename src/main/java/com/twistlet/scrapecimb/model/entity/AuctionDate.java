package com.twistlet.scrapecimb.model.entity;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AuctionDate {

	@Id
	private String id;

	@Indexed
	private Set<Date> dates;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Set<Date> getDates() {
		return dates;
	}

	public void setDates(final Set<Date> dates) {
		this.dates = dates;
	}

}
