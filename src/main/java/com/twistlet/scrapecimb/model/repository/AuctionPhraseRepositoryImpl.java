package com.twistlet.scrapecimb.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import com.twistlet.scrapecimb.model.entity.AuctionPhrase;

public class AuctionPhraseRepositoryImpl implements
		AuctionPhraseRepositoryCustom {

	private final ElasticsearchOperations elasticsearchOperations;

	@Autowired
	public AuctionPhraseRepositoryImpl(
			final ElasticsearchOperations elasticsearchOperations) {
		this.elasticsearchOperations = elasticsearchOperations;
	}

	@Override
	public void addUrl(final String id, final String url) {
		final GetQuery query = new GetQuery();
		query.setId(id);
		final AuctionPhrase item = elasticsearchOperations.queryForObject(
				query, AuctionPhrase.class);
		item.getUrl().add(url);
		item.setCount(item.getUrl().size());
		final IndexQueryBuilder indexQueryBuilder = new IndexQueryBuilder();
		indexQueryBuilder.withId(id);
		indexQueryBuilder.withObject(item);
		elasticsearchOperations.index(indexQueryBuilder.build());
	}
}
