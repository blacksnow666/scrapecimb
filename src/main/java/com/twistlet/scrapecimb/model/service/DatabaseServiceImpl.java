package com.twistlet.scrapecimb.model.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.twistlet.scrapecimb.model.entity.AuctionArea;
import com.twistlet.scrapecimb.model.entity.AuctionHouse;
import com.twistlet.scrapecimb.model.entity.AuctionPhrase;
import com.twistlet.scrapecimb.model.repository.AuctionAreaRepository;
import com.twistlet.scrapecimb.model.repository.AuctionHouseRepository;
import com.twistlet.scrapecimb.model.repository.AuctionPhraseRepository;

@Service("databaseService")
public class DatabaseServiceImpl implements DatabaseService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private final AuctionHouseRepository auctionHouseRepository;
	private final AuctionAreaRepository auctionAreaRepository;
	private final AuctionPhraseRepository auctionPhraseRepository;
	private final List<EnrichAuctionHouseService> listEnrichAuctionHouseService;

	@Autowired
	public DatabaseServiceImpl(
			final AuctionHouseRepository auctionHouseRepository,
			final AuctionAreaRepository auctionAreaRepository,
			final AuctionPhraseRepository auctionPhraseRepository,
			@Value("#{listEnrichAuctionHouseService}") final List<EnrichAuctionHouseService> listEnrichAuctionHouseService) {
		this.auctionHouseRepository = auctionHouseRepository;
		this.auctionAreaRepository = auctionAreaRepository;
		this.auctionPhraseRepository = auctionPhraseRepository;
		this.listEnrichAuctionHouseService = listEnrichAuctionHouseService;
	}

	@Override
	public List<AuctionHouse> findHighProfitLowCost(final double profit,
			final double value, final boolean includeBumi) {
		if (includeBumi) {
			return auctionHouseRepository
					.findByDifferenceGreaterThanAndPriceAuctionLessThan(profit,
							value, new PageRequest(0, 500, Direction.DESC,
									"difference"));
		} else {
			List<String> list = Arrays
					.asList("Bumiputra Lot", "Malay Reserved");
			return auctionHouseRepository
					.findByDifferenceGreaterThanAndPriceAuctionLessThanAndRestrictionNotIn(
							profit, value, list, new PageRequest(0, 500,
									Direction.DESC, "difference"));
		}
	}

	@Override
	public void saveAuctionHouse(final AuctionHouse auctionHouse) {
		for (EnrichAuctionHouseService enrichAuctionHouseService : listEnrichAuctionHouseService) {
			enrichAuctionHouseService.enrich(auctionHouse);
		}
		auctionHouseRepository.save(auctionHouse);

	}

	@Override
	public List<AuctionArea> listAuctionArea() {
		return auctionAreaRepository.findAll();
	}

	@Override
	public void saveAuctionArea(final AuctionArea item) {
		auctionAreaRepository.save(item);
	}

	@Override
	public void removeAuctionArea(final String id) {
		auctionAreaRepository.delete(id);
	}

	@Override
	public AuctionArea getAuctionArea(final String id) {
		return auctionAreaRepository.findOne(id);
	}

	@Override
	public void removeAllAuctionPhrase() {
		auctionPhraseRepository.deleteAll();
	}

	@Override
	public List<AuctionHouse> listAuctionHouse() {
		PageRequest pageRequest = new PageRequest(0, 10_000);
		Page<AuctionHouse> page = auctionHouseRepository.findAll(pageRequest);
		return page.getContent();
	}

	@Override
	public List<AuctionPhrase> findAuctionPhrase(final String keyword,
			final String state, final String area) {
		PageRequest pageRequest = new PageRequest(0, 10_000);
		Page<AuctionPhrase> page = auctionPhraseRepository
				.findByNameAndStateAndArea(keyword, state, area, pageRequest);
		return page.getContent();
	}

	@Override
	public void saveAuctionPhrase(final AuctionPhrase auctionPhrase) {
		auctionPhraseRepository.save(auctionPhrase);
	}

	@Override
	public void addToAuctionPhrase(final String id, final String url) {
		auctionPhraseRepository.addUrl(id, url);
	}

}
