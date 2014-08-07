package com.twistlet.scrapecimb.model.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.repository.AuctionAreaRepository;

@Service("auctionAreaPopulateService")
public class AuctionAreaPopulateService implements PopulateService {

	private final AuctionAreaRepository auctionAreaRepository;
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public AuctionAreaPopulateService(
			final AuctionAreaRepository auctionAreaRepository) {
		this.auctionAreaRepository = auctionAreaRepository;
	}

	@Override
	public void populate() {
		if (auctionAreaRepository.count() == 0L) {
			return;
		}
		try {
			List<AuctionArea> list = new ArrayList<AuctionArea>();
			String resourceName = "initial-auction-area.txt";
			Resource resourse = new ClassPathResource(resourceName);
			InputStream inputStream = resourse.getInputStream();
			List<String> lines = IOUtils.readLines(inputStream);
			ObjectMapper objectMapper = new ObjectMapper();
			for (String line : lines) {
				try {
					AuctionArea item = objectMapper.readValue(line,
							AuctionArea.class);
					list.add(item);
				} catch (JsonParseException | JsonMappingException e) {
					logger.error(e.toString());
				}
			}
			auctionAreaRepository.save(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
