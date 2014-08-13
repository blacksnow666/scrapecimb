package com.twistlet.scrapecimb.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.twistlet.scrapecimb.model.entity.AuctionPhrase;

public class AuctionPhraseRepositoryImpl implements
		AuctionPhraseRepositoryCustom {

	private final MongoOperations mongoOperations;

	@Autowired
	public AuctionPhraseRepositoryImpl(final MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	@Override
	public void addUrl(final String id, final String url) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update().addToSet("url", url).inc("count", 1);
		mongoOperations.findAndModify(query, update, AuctionPhrase.class);
	}

}
