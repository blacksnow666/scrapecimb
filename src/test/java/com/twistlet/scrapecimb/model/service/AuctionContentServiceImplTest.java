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
	public void testToMapWithSomeTrNoTd() throws IOException {
		Document document = Jsoup.parse(new ClassPathResource(
				"html/faulty-tr-but-no-td.html").getInputStream(), "UTF-8",
				"http://www.cimb.com.my/");
		Map<String, String> map = sut.toMap(document);
		String expected = "{Industry=Banking, Name=CIMB, url=http://www.cimb.com.my/}";
		assertEquals(expected, map.toString());
	}

	@Test
	public void testToMapWithSomeTrNoTdPair() throws IOException {
		Document document = Jsoup.parse(new ClassPathResource(
				"html/faulty-tr-but-no-td-pair.html").getInputStream(),
				"UTF-8", "http://www.cimb.com.my/");
		Map<String, String> map = sut.toMap(document);
		String expected = "{Industry=Banking, url=http://www.cimb.com.my/}";
		assertEquals(expected, map.toString());
	}

	@Test
	public void testToMapWithSomeEmptyTd00() throws IOException {
		Document document = Jsoup.parse(new ClassPathResource(
				"html/faulty-tr-but-no-td-value-00.html").getInputStream(),
				"UTF-8", "http://www.cimb.com.my/");
		Map<String, String> map = sut.toMap(document);
		String expected = "{Industry=Banking, url=http://www.cimb.com.my/}";
		assertEquals(expected, map.toString());
	}

	@Test
	public void testToMapWithSomeEmptyTd01() throws IOException {
		Document document = Jsoup.parse(new ClassPathResource(
				"html/faulty-tr-but-no-td-value-01.html").getInputStream(),
				"UTF-8", "http://www.cimb.com.my/");
		Map<String, String> map = sut.toMap(document);
		String expected = "{Industry=Banking, url=http://www.cimb.com.my/}";
		assertEquals(expected, map.toString());
	}

	@Test
	public void testToMapWithSomeEmptyTd10() throws IOException {
		Document document = Jsoup.parse(new ClassPathResource(
				"html/faulty-tr-but-no-td-value-10.html").getInputStream(),
				"UTF-8", "http://www.cimb.com.my/");
		Map<String, String> map = sut.toMap(document);
		String expected = "{Industry=Banking, url=http://www.cimb.com.my/}";
		assertEquals(expected, map.toString());
	}

	@Test
	public void testToAuctionHouseFull() throws JsonProcessingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Reference No", "DC10008179");
		map.put("Price From (RM)", "29,160.00");
		map.put("Market Price (RM)", "50,000.00");
		map.put("Address",
				"44, 3rd Floor, Block D Jalan Mulia, Taman Mulia Jaya Ampang, Area");
		map.put("Area", "AMPANG");
		map.put("State", "Selangor");
		map.put("Size", "528.00 Sqft.");
		map.put("Restriction", "Not Stated");
		map.put("Property Description",
				"3rd floor of 5 storey low cost flat, 2 bedroom(s)");
		map.put("Auction Date & Time", "Saturday, 16 Aug 2014,");
		map.put("Auction Venue", "Hotel Istana, Wilayah Persekutuan");
		map.put("Auctioneer",
				"Property Auction House Sdn Bhd-Kuala Lumpur-Wilayah Persekutuan");
		map.put("url", "http://www.cimb.com.my/");
		AuctionHouse auctionHouse = sut.toAuctionHouse(map);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":\"http://www.cimb.com.my/\",\"priceAuction\":29160.0,\"priceMarket\":50000.0,\"percentage\":171.46776406035664,\"difference\":20840.0,\"address\":\"44, 3rd Floor, Block D Jalan Mulia, Taman Mulia Jaya Ampang, Area\",\"area\":\"Ampang\",\"state\":\"Selangor\",\"sqFeet\":528.0,\"restriction\":\"Not Stated\",\"propertyDescription\":\"3rd floor of 5 storey low cost flat, 2 bedroom(s)\",\"auctionDate\":\"2014/08/16 12:00:00 AM\",\"auctionLocation\":\"Hotel Istana, Wilayah Persekutuan\",\"auctionCompany\":\"Property Auction House Sdn Bhd-Kuala Lumpur-Wilayah Persekutuan\",\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

	@Test
	public void testToAuctionHouseNothing() throws JsonProcessingException {
		Map<String, String> nothing = new LinkedHashMap<String, String>();
		AuctionHouse auctionHouse = sut.toAuctionHouse(nothing);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":null,\"priceAuction\":null,\"priceMarket\":null,\"percentage\":null,\"difference\":null,\"address\":null,\"area\":null,\"state\":null,\"sqFeet\":null,\"restriction\":null,\"propertyDescription\":null,\"auctionDate\":null,\"auctionLocation\":null,\"auctionCompany\":null,\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

	@Test
	public void testToAuctionHousePriceYesMarketYes()
			throws JsonProcessingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Price From (RM)", "29,160.00");
		map.put("Market Price (RM)", "50,000.00");
		map.put("url", "http://www.cimb.com.my/");
		AuctionHouse auctionHouse = sut.toAuctionHouse(map);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":\"http://www.cimb.com.my/\",\"priceAuction\":29160.0,\"priceMarket\":50000.0,\"percentage\":171.46776406035664,\"difference\":20840.0,\"address\":null,\"area\":null,\"state\":null,\"sqFeet\":null,\"restriction\":null,\"propertyDescription\":null,\"auctionDate\":null,\"auctionLocation\":null,\"auctionCompany\":null,\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

	@Test
	public void testToAuctionHousePriceNoMarketNo()
			throws JsonProcessingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("url", "http://www.cimb.com.my/");
		AuctionHouse auctionHouse = sut.toAuctionHouse(map);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":\"http://www.cimb.com.my/\",\"priceAuction\":null,\"priceMarket\":null,\"percentage\":null,\"difference\":null,\"address\":null,\"area\":null,\"state\":null,\"sqFeet\":null,\"restriction\":null,\"propertyDescription\":null,\"auctionDate\":null,\"auctionLocation\":null,\"auctionCompany\":null,\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

	@Test
	public void testToAuctionHousePriceYesMarketNo()
			throws JsonProcessingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Price From (RM)", "29,160.00");
		map.put("url", "http://www.cimb.com.my/");
		AuctionHouse auctionHouse = sut.toAuctionHouse(map);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":\"http://www.cimb.com.my/\",\"priceAuction\":29160.0,\"priceMarket\":null,\"percentage\":null,\"difference\":null,\"address\":null,\"area\":null,\"state\":null,\"sqFeet\":null,\"restriction\":null,\"propertyDescription\":null,\"auctionDate\":null,\"auctionLocation\":null,\"auctionCompany\":null,\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

	@Test
	public void testToAuctionHousePriceNoMarketYes()
			throws JsonProcessingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Market Price (RM)", "50,000.00");
		map.put("url", "http://www.cimb.com.my/");
		AuctionHouse auctionHouse = sut.toAuctionHouse(map);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":\"http://www.cimb.com.my/\",\"priceAuction\":null,\"priceMarket\":50000.0,\"percentage\":null,\"difference\":null,\"address\":null,\"area\":null,\"state\":null,\"sqFeet\":null,\"restriction\":null,\"propertyDescription\":null,\"auctionDate\":null,\"auctionLocation\":null,\"auctionCompany\":null,\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

	@Test
	public void testToAuctionHousePriceYesZeroMarketYes()
			throws JsonProcessingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Price From (RM)", "0.00");
		map.put("Market Price (RM)", "50,000.00");
		map.put("url", "http://www.cimb.com.my/");
		AuctionHouse auctionHouse = sut.toAuctionHouse(map);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":\"http://www.cimb.com.my/\",\"priceAuction\":0.0,\"priceMarket\":50000.0,\"percentage\":null,\"difference\":50000.0,\"address\":null,\"area\":null,\"state\":null,\"sqFeet\":null,\"restriction\":null,\"propertyDescription\":null,\"auctionDate\":null,\"auctionLocation\":null,\"auctionCompany\":null,\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

	@Test
	public void testToAuctionHouseDateWithoutTime()
			throws JsonProcessingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Auction Date & Time", "Saturday, 16 Aug 2014,");
		map.put("url", "http://www.cimb.com.my/");
		AuctionHouse auctionHouse = sut.toAuctionHouse(map);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":\"http://www.cimb.com.my/\",\"priceAuction\":null,\"priceMarket\":null,\"percentage\":null,\"difference\":null,\"address\":null,\"area\":null,\"state\":null,\"sqFeet\":null,\"restriction\":null,\"propertyDescription\":null,\"auctionDate\":\"2014/08/16 12:00:00 AM\",\"auctionLocation\":null,\"auctionCompany\":null,\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

	@Test
	public void testToAuctionHouseDateWithTime() throws JsonProcessingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Auction Date & Time", "Saturday, 10 Aug 2014, 10:00 am");
		map.put("url", "http://www.cimb.com.my/");
		AuctionHouse auctionHouse = sut.toAuctionHouse(map);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":\"http://www.cimb.com.my/\",\"priceAuction\":null,\"priceMarket\":null,\"percentage\":null,\"difference\":null,\"address\":null,\"area\":null,\"state\":null,\"sqFeet\":null,\"restriction\":null,\"propertyDescription\":null,\"auctionDate\":\"2014/08/10 10:00:00 AM\",\"auctionLocation\":null,\"auctionCompany\":null,\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

	@Test
	public void testToAuctionHouseBadDateTime() throws JsonProcessingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Auction Date & Time", "N/A");
		map.put("url", "http://www.cimb.com.my/");
		AuctionHouse auctionHouse = sut.toAuctionHouse(map);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":\"http://www.cimb.com.my/\",\"priceAuction\":null,\"priceMarket\":null,\"percentage\":null,\"difference\":null,\"address\":null,\"area\":null,\"state\":null,\"sqFeet\":null,\"restriction\":null,\"propertyDescription\":null,\"auctionDate\":null,\"auctionLocation\":null,\"auctionCompany\":null,\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

	@Test
	public void testToAuctionHousePrice() throws JsonProcessingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Price From (RM)", "N/A");
		map.put("url", "http://www.cimb.com.my/");
		AuctionHouse auctionHouse = sut.toAuctionHouse(map);
		ObjectMapper objectMapper = new ObjectMapper();
		String actual = objectMapper.writeValueAsString(auctionHouse);
		String expected = "{\"url\":\"http://www.cimb.com.my/\",\"priceAuction\":null,\"priceMarket\":null,\"percentage\":null,\"difference\":null,\"address\":null,\"area\":null,\"state\":null,\"sqFeet\":null,\"restriction\":null,\"propertyDescription\":null,\"auctionDate\":null,\"auctionLocation\":null,\"auctionCompany\":null,\"auctionId\":null,\"housingArea\":null}";
		assertEquals(expected, actual);
	}

}
