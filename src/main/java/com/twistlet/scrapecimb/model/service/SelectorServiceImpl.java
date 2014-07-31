package com.twistlet.scrapecimb.model.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SelectorServiceImpl implements SelectorService {

	final private String cssQuery;

	public SelectorServiceImpl(final String cssQuery) {
		super();
		this.cssQuery = cssQuery;
	}

	@Override
	public List<Element> select(final Document document) {
		List<Element> list = new ArrayList<Element>();
		Elements elements = document.select(cssQuery);
		list.addAll(elements);
		return list;
	}

}
