package com.twistlet.scrapecimb.model.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<String> readLines(final String resourcename) {
		try {
			Resource resourse = new ClassPathResource(resourcename);
			InputStream inputStream = resourse.getInputStream();
			List<String> lines = IOUtils.readLines(inputStream);
			IOUtils.closeQuietly(inputStream);
			return lines;
		} catch (IOException e) {
			logger.error(e.toString());
		}
		return null;
	}

}
