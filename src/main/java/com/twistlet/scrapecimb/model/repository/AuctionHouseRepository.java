package com.twistlet.scrapecimb.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.twistlet.scrapecimb.model.entity.AuctionHouse;

@Repository("auctionHouseRepository")
public interface AuctionHouseRepository extends
		ElasticsearchRepository<AuctionHouse, String> {

	List<AuctionHouse> findByDifferenceGreaterThanAndPriceAuctionLessThan(
			double diff, double price, Pageable pageable);

	List<AuctionHouse> findByDifferenceGreaterThanAndPriceAuctionLessThanAndRestrictionNotIn(
			double diff, double price, List<String> list, Pageable pageable);
}
