package com.twistlet.scrapecimb.model.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AuctionPhrase {

	@Id
	private String name;
	@Indexed
	private int count;
	private String state;
	private String area;
	private Set<String> url;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(final int count) {
		this.count = count;
	}

	public String getState() {
		return state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getArea() {
		return area;
	}

	public void setArea(final String area) {
		this.area = area;
	}

	public Set<String> getUrl() {
		return url;
	}

	public void setUrl(final Set<String> url) {
		this.url = url;
	}

}
