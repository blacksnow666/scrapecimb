package com.twistlet.scrapecimb.model.service;

public interface JsonService {

	<T> T toObject(String value, Class<T> t);
}
