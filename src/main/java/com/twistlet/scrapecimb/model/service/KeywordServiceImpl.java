package com.twistlet.scrapecimb.model.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

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
		String text = removeBadCharacters(content);
		List<String> list = new ArrayList<String>();
		String[] words = StringUtils.split(text);
		words = filterBadWords(words);
		words = translateWords(words);

		for (int i = min; i <= max; i++) {
			List<String> line = generateWords(words, i);
			list.addAll(line);
		}
		return list;
	}

	private List<String> generateWords(final String[] words, final int count) {
		List<String> list = new ArrayList<String>();
		if (count < 1) {
			return list;
		}
		int length = words.length;
		for (int i = 0; i < ((length - count) + 1); i++) {
			List<String> phrase = new ArrayList<String>();
			for (int j = 0; j < count; j++) {
				phrase.add(words[i + j]);
			}
			String line = StringUtils.join(phrase.toArray(new Object[] {}),
					StringUtils.SPACE);
			list.add(WordUtils.capitalizeFully(line));
		}
		return list;
	}

	private String[] translateWords(final String[] words) {
		return words;
	}

	private String[] filterBadWords(final String[] words) {
		return words;
	}

	private String removeBadCharacters(final String content) {
		String text = content;
		String listOfBadCharacters = ",";
		char[] a = listOfBadCharacters.toCharArray();
		for (char c : a) {
			text = StringUtils.remove(text, c);
		}
		return text;
	}

}
