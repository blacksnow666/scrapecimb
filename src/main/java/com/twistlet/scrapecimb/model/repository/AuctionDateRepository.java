package com.twistlet.scrapecimb.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.twistlet.scrapecimb.model.entity.AuctionDate;

@Repository("auctionDateRepository")
public interface AuctionDateRepository extends
		MongoRepository<AuctionDate, String> {

}
