package com.twistlet.scrapecimb.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twistlet.scrapecimb.model.entity.AuctionDate;
import com.twistlet.scrapecimb.model.repository.AuctionDateRepository;

@Service
public class AuctionDateServiceImpl implements AuctionDateService {

	private final AuctionDateRepository auctionDateRepository;

	@Autowired
	public AuctionDateServiceImpl(
			final AuctionDateRepository auctionDateRepository) {
		this.auctionDateRepository = auctionDateRepository;
	}

	@Override
	public AuctionDate get(final String id) {
		return auctionDateRepository.findOne(id);
	}

}
