package com.twistlet.scrapecimb.model.service;

import java.text.MessageFormat;

public class MessageFormatServiceImpl implements MessageFormatService {

	private final String pattern;

	public MessageFormatServiceImpl(final String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String format(final Object object) {
		Object[] objects = { object.toString() };
		return MessageFormat.format(pattern, objects);
	}

}
