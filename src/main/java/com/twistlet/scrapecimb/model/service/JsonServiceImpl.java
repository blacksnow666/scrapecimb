package com.twistlet.scrapecimb.model.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonServiceImpl implements JsonService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public <T> T toObject(final String value, final Class<T> t) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(value, t);
		} catch (IOException e) {
			logger.error(e.toString());
		}
		return null;
	}

}
