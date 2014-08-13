package com.twistlet.scrapecimb.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.twistlet.scrapecimb.model.entity.AuctionPhrase;

public interface AuctionPhraseRepository extends
		MongoRepository<AuctionPhrase, String>, AuctionPhraseRepositoryCustom {

	Page<AuctionPhrase> findByNameAndStateAndArea(String name, String state,
			String area, Pageable pageable);
}
