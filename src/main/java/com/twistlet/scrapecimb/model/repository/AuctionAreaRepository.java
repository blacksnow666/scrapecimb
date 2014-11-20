package com.twistlet.scrapecimb.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.twistlet.scrapecimb.model.entity.AuctionArea;

@Repository
public interface AuctionAreaRepository extends
		ElasticsearchRepository<AuctionArea, String> {
	List<AuctionArea> findByStateAndArea(String state, String area,
			Pageable pageable);
}
