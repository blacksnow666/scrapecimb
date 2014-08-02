package com.twistlet.scrapecimb.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class RegularExpressionExtractorServiceImpl implements
		RegularExpressionExtractorService {

	@Override
	public List<String> extract(final String regex, final String input) {
		List<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			int count = matcher.groupCount();
			int groupCount = 0;
			for (int i = 1; i <= count; i++) {
				groupCount++;
				String group = matcher.group(groupCount);
				result.add(group);
			}
		}
		return result;
	}

}
