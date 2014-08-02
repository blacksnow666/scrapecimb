package com.twistlet.scrapecimb.model.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("auctionIdService")
public class AuctionIdServiceImpl implements AuctionIdService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final RegularExpressionExtractorService regularExpressionExtractorService;

	@Autowired
	public AuctionIdServiceImpl(
			final RegularExpressionExtractorService regularExpressionExtractorService) {
		super();
		this.regularExpressionExtractorService = regularExpressionExtractorService;
	}

	@Override
	public List<Long> listAuctionId(final Document document) {
		List<Long> list = new ArrayList<Long>();
		Elements elements = document
				.select("div#ct1 table.outerbox tbody tr.Content3 td a");
		String regex = "aucID=(.*)";
		for (Element element : elements) {
			String href = element.attr("href");
			List<String> items = regularExpressionExtractorService.extract(
					regex, href);
			if (items.size() == 1) {
				try {
					Long number = new Long(items.get(0));
					list.add(number);
				} catch (NumberFormatException e) {
					logger.error("Error extracting number from {}", href);
				}
			}
		}
		return list;
	}

}
