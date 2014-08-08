package com.twistlet.scrapecimb.model.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

public class KeywordServiceImpl implements KeywordService {

	private final int min;
	private final int max;

	public KeywordServiceImpl(final int min, final int max) {
		this.min = min;
		this.max = max;
	}

	public KeywordServiceImpl() {
		this(2, 4);
	}

	@Override
	public List<String> toKeywords(final String content) {
		List<String> list = new ArrayList<String>();
		String[] words = StringUtils.split(content);
		ICombinatoricsVector<String> initialVector = Factory
				.createVector(words);
		for (int i = min; i <= max; i++) {
			Generator<String> gen = Factory.createSimpleCombinationGenerator(
					initialVector, i);
			for (ICombinatoricsVector<String> combination : gen) {
				String line = StringUtils.join(
						combination.getVector().toArray(new String[] {}),
						StringUtils.SPACE);
				list.add(WordUtils.capitalizeFully(line));
			}
		}
		return list;
	}

}
