package com.twistlet.scrapecimb.model.service;

import org.jsoup.nodes.Document;

public interface DocumentService {
	Document get(final String uri);

}
