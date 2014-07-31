package com.twistlet.scrapecimb.model.service;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public interface SelectorService {

	List<Element> select(Document document);
}
