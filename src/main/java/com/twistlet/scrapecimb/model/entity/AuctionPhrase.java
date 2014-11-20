package com.twistlet.scrapecimb.model.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(type = "auction_phrase", indexName = "#{elasticsearch['elasticsearch.index']}")
public class AuctionPhrase {

	@Id
	private String id;
	private String name;
	private int count;
	private String state;
	private String area;

	@Field(type = FieldType.Nested)
	private Set<String> url;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

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
