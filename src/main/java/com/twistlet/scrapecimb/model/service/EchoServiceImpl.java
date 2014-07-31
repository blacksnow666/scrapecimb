package com.twistlet.scrapecimb.model.service;

public class EchoServiceImpl implements EchoService {

	private final String content;

	public EchoServiceImpl(final String content) {
		super();
		this.content = content;
	}

	@Override
	public String echo() {
		return content;
	}

}
