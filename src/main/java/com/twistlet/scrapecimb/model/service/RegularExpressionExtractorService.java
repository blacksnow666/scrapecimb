package com.twistlet.scrapecimb.model.service;

import java.util.List;

public interface RegularExpressionExtractorService {

	List<String> extract(String regex, String input);
}
