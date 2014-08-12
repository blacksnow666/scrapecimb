package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class KeywordServiceImplTest {

	@Test
	public void testToKeywords() {
		KeywordServiceImpl sut = new KeywordServiceImpl(1, 2);
		List<String> list = sut.toKeywords("Lorem ipsum dolor sit amet");
		String actual = list.toString();
		assertEquals(
				"[Lorem, Ipsum, Dolor, Sit, Amet, Lorem Ipsum, Ipsum Dolor, Dolor Sit, Sit Amet]",
				actual);
	}

	@Test
	public void testToKeywords33() {
		KeywordServiceImpl sut = new KeywordServiceImpl(3, 3);
		List<String> list = sut.toKeywords("this is sparta");
		String actual = list.toString();
		assertEquals("[This Is Sparta]", actual);
	}

	@Test
	public void testToKeywords11() {
		KeywordServiceImpl sut = new KeywordServiceImpl(1, 1);
		List<String> list = sut.toKeywords("this is sparta");
		String actual = list.toString();
		assertEquals("[This, Is, Sparta]", actual);
	}

}
