package com.twistlet.scrapecimb.model.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(type = "auction_house", indexName = "#{elasticsearch['elasticsearch.index']}")
public class AuctionHouse {

	@Id
	private String url;

	private String ref;

	private Double priceAuction;

	private Double priceMarket;

	private Double percentage;

	private Double difference;

	private String address;

	private String area;

	private String state;

	private Double sqFeet;

	private String restriction;

	private String propertyDescription;

	@JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss aa", timezone = "Asia/Kuala_Lumpur")
	private Date auctionDate;

	private String auctionLocation;

	private String auctionCompany;

	private Long auctionId;

	private String housingArea;

	private Integer previousAuctionCount;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(final String ref) {
		this.ref = ref;
	}

	public Double getPriceAuction() {
		return priceAuction;
	}

	public void setPriceAuction(final Double priceAuction) {
		this.priceAuction = priceAuction;
	}

	public Double getPriceMarket() {
		return priceMarket;
	}

	public void setPriceMarket(final Double priceMarket) {
		this.priceMarket = priceMarket;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(final Double percentage) {
		this.percentage = percentage;
	}

	public Double getDifference() {
		return difference;
	}

	public void setDifference(final Double difference) {
		this.difference = difference;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(final String area) {
		this.area = area;
	}

	public String getState() {
		return state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public Double getSqFeet() {
		return sqFeet;
	}

	public void setSqFeet(final Double sqFeet) {
		this.sqFeet = sqFeet;
	}

	public String getRestriction() {
		return restriction;
	}

	public void setRestriction(final String restriction) {
		this.restriction = restriction;
	}

	public String getPropertyDescription() {
		return propertyDescription;
	}

	public void setPropertyDescription(final String propertyDescription) {
		this.propertyDescription = propertyDescription;
	}

	public Date getAuctionDate() {
		return auctionDate;
	}

	public void setAuctionDate(final Date auctionDate) {
		this.auctionDate = auctionDate;
	}

	public String getAuctionLocation() {
		return auctionLocation;
	}

	public void setAuctionLocation(final String auctionLocation) {
		this.auctionLocation = auctionLocation;
	}

	public String getAuctionCompany() {
		return auctionCompany;
	}

	public void setAuctionCompany(final String auctionCompany) {
		this.auctionCompany = auctionCompany;
	}

	public Long getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(final Long auctionId) {
		this.auctionId = auctionId;
	}

	public String getHousingArea() {
		return housingArea;
	}

	public void setHousingArea(final String housingArea) {
		this.housingArea = housingArea;
	}

	public Integer getPreviousAuctionCount() {
		return previousAuctionCount;
	}

	public void setPreviousAuctionCount(final Integer previousAuctionCount) {
		this.previousAuctionCount = previousAuctionCount;
	}

}
