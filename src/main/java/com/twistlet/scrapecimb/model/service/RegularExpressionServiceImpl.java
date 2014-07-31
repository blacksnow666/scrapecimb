package com.twistlet.scrapecimb.model.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionServiceImpl implements RegularExpressionService {

	private final String pattern;

	public RegularExpressionServiceImpl(final String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String extract(final String input) {
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(input);
		if (matcher.matches()) {
			if (matcher.groupCount() == 1) {
				return matcher.group(1);
			}
		}
		return null;
	}

}
