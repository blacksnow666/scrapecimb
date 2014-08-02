package com.twistlet.scrapecimb.model.service;

public interface ToNumberService {

	<T extends Number> T toNumber(String value, Class<T> c);
}
