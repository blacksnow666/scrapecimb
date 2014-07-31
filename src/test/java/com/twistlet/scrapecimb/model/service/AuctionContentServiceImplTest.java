package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twistlet.scrapecimb.model.entity.AuctionHouse;

public class AuctionContentServiceImplTest {

	AuctionContentServiceImpl sut;

	@Before
	public void init() {
		sut = new AuctionContentServiceImpl();
	}

	@Test
	public void testToMap() throws IOException {
		Document document = Jsoup.parse(new ClassPathResource(
				"html/detail-red-1").getInputStream(), "UTF-8",
				"http://www.cimb.com.my/");
		Map<String, String> map = sut.toMap(document);
		String expected = "{Reference No=DC10008179, Price From (RM)=29,160.00, Market Price (RM)=50,000.00, Address=44, 3rd Floor, Block D Jalan Mulia, Taman Mulia Jaya Ampang, Area=AMPANG, State=Selangor, Size=528.00 Sqft., Restriction=Not Stated, Property Description=3rd floor of 5 storey low cost flat, 2 bedroom(s), Auction Date & Time=Saturday, 16 Aug 2014,, Auction Venue=Hotel Istana, Wilayah Persekutuan, Auctioneer=Property Auction House Sdn Bhd-Kuala Lumpur-Wilayah Persekutuan, url=http://www.cimb.com.my/}";
		assertEquals(expected, map.toString());
	}

	@Test
	public void testToAuctionHouse() throws JsonProcessingException {
		Map<String, String> nothing = new LinkedHashMap<String, String>();
		AuctionHouse auctionHouse = sut.toAuctionHouse(nothing);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":null,\"priceAuction\":null,\"priceMarket\":null,\"percentage\":null,\"difference\":null,\"address\":null,\"area\":null,\"state\":null,\"sqFeet\":null,\"restriction\":null,\"propertyDescription\":null,\"auctionDate\":null,\"auctionLocation\":null,\"auctionCompany\":null,\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

}