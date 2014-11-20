package com.twistlet.scrapecimb.model.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.twistlet.scrapecimb.model.entity.AuctionDate;

@Repository("auctionDateRepository")
public interface AuctionDateRepository extends
		ElasticsearchRepository<AuctionDate, String> {

}
