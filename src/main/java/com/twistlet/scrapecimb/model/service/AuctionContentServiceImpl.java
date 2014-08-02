package com.twistlet.scrapecimb.model.service;

import static java.util.Optional.*;
import static org.apache.commons.lang3.text.WordUtils.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;

@Service("auctionContentService")
public class AuctionContentServiceImpl implements AuctionContentService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Map<String, String> toMap(final Document document) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String cssSelector = "table tbody tr td table tbody tr td table.Content3 tbody tr td table.outerbox tbody tr td table.Content tbody tr";
		Elements elements = document.select(cssSelector);
		for (Element tr : elements) {
			Element elementKey = tr.select("td:eq(0)").first();
			Element elementValue = tr.select("td:eq(2)").first();
			if ((elementKey != null) && (elementValue != null)) {
				String key = StringUtils.trimToNull(elementKey.text());
				String value = StringUtils.trimToNull(elementValue.text());
				if ((key != null) && (value != null)) {
					map.put(key, value);
				}

			}
		}
		map.put("url", document.location());
		return map;
	}

	@Override
	public AuctionHouse toAuctionHouse(final Map<String, String> map) {
		AuctionHouse auctionHouse = new AuctionHouse();
		ofNullable(map.get("url")).ifPresent(auctionHouse::setUrl);
		ofNullable(map.get("Reference No")).ifPresent(auctionHouse::setRef);
		ofNullable(map.get("Price From (RM)")).ifPresent(
				x -> auctionHouse.setPriceAuction(toDouble(x)));
		ofNullable(map.get("Market Price (RM)")).ifPresent(
				x -> auctionHouse.setPriceMarket(toDouble(x)));
		if ((auctionHouse.getPriceAuction() != null)
				&& (auctionHouse.getPriceMarket() != null)) {
			double auction = auctionHouse.getPriceAuction();
			double market = auctionHouse.getPriceMarket();
			if (auction != 0.00) {
				double percentage = (market / auction) * 100.00;
				auctionHouse.setPercentage(percentage);
			}
			auctionHouse.setDifference(market - auction);
		}
		ofNullable(map.get("Address")).ifPresent(
				x -> auctionHouse.setAddress(trimMultiLine(x)));
		ofNullable(map.get("Area")).ifPresent(
				x -> auctionHouse.setArea(capitalizeFully(x)));
		ofNullable(map.get("State")).ifPresent(
				x -> auctionHouse.setState(capitalizeFully(x)));
		ofNullable(map.get("Size")).ifPresent(
				x -> auctionHouse.setSqFeet(toSqFeet(x)));
		ofNullable(map.get("Restriction")).ifPresent(
				auctionHouse::setRestriction);
		ofNullable(map.get("Property Description")).ifPresent(
				auctionHouse::setPropertyDescription);
		ofNullable(map.get("Auction Date & Time")).ifPresent(
				x -> auctionHouse.setAuctionDate(toDate(x)));
		ofNullable(map.get("Auction Venue")).ifPresent(
				auctionHouse::setAuctionLocation);
		ofNullable(map.get("Auction Venue")).ifPresent(
				auctionHouse::setAuctionLocation);
		ofNullable(map.get("Auctioneer")).ifPresent(
				auctionHouse::setAuctionCompany);
		return auctionHouse;
	}

	private Date toDate(final String val) {
		String[] components = StringUtils.split(val, ",");
		FastDateFormat df = null;
		if (components.length == 3) {
			df = FastDateFormat.getInstance("EEEE, dd MMM yyyy, hh:mm aa");
		} else {
			df = FastDateFormat.getInstance("EEEE, dd MMM yyyy,");
		}

		try {
			return df.parse(StringUtils.trimToEmpty(val));
		} catch (Exception e) {
			logger.error("{} - {}", e.toString(), val);
		}
		return null;
	}

	private Double toSqFeet(final String value) {
		String[] items = StringUtils.split(value);
		return toDouble(items[0]);
	}

	private Double toDouble(final String val) {
		try {
			return new Double(StringUtils.remove(val, ","));
		} catch (NumberFormatException e) {
			logger.error("{} - {}", e.toString(), val);
		}
		return null;
	}

	private String trimMultiLine(final String line) {
		String result = line;
		result = StringUtils.remove(result, StringUtils.CR);
		result = StringUtils.remove(result, StringUtils.LF);
		return StringUtils.trimToNull(result);
	}
}
