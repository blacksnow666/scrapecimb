package com.twistlet.scrapecimb.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.twistlet.scrapecimb.model.entity.AuctionArea;

public interface AuctionAreaRepository extends
		MongoRepository<AuctionArea, String> {

}
