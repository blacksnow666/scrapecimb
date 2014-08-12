package com.twistlet.scrapecimb.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.twistlet.scrapecimb.model.entity.AuctionPhrase;

public interface AuctionPhraseRepository extends
		MongoRepository<AuctionPhrase, String> {

}
