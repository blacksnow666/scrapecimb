package com.twistlet.scrapecimb.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;

@Repository("auctionHouseRepository")
public interface AuctionHouseRepository extends
		MongoRepository<AuctionHouse, String> {

	List<AuctionHouse> findByDifferenceGreaterThanAndPriceAuctionLessThan(
			double diff, double price, Pageable pageable);
}
