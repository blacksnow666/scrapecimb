package com.twistlet.scrapecimb.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("auctionIdService")
public class AuctionIdServiceImpl implements AuctionIdService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<Long> listAuctionId(final Document document) {
		List<Long> list = new ArrayList<Long>();
		Elements elements = document
				.select("div#ct1 table.outerbox tbody tr.Content3 td a");
		String regex = ".*aucID=(.*)";
		Pattern pattern = Pattern.compile(regex);
		for (Element element : elements) {
			String href = element.attr("href");
			Matcher matcher = pattern.matcher(href);
			if (matcher.find()) {
				try {
					Long number = new Long(matcher.group(1));
					list.add(number);
				} catch (NumberFormatException e) {
					logger.error("Error extracting number from {}", href);
				}
			}

		}
		return list;
	}

}
