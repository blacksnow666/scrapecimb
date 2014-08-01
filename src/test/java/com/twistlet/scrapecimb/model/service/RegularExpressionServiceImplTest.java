package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegularExpressionServiceImplTest {

	@Test
	public void testWithMatch() {
		RegularExpressionServiceImpl sut = new RegularExpressionServiceImpl(
				"what a wonderful (.*)");
		String content = sut.extract("what a wonderful world");
		assertEquals("world", content);
	}

	@Test
	public void testNoMatch() {
		RegularExpressionServiceImpl sut = new RegularExpressionServiceImpl(
				"what a wonderful (.*)");
		String content = sut.extract("nothing");
		assertNull(content);
	}

	@Test
	public void testMultipleMatches() {
		RegularExpressionServiceImpl sut = new RegularExpressionServiceImpl(
				"(.)(.).");
		String content = sut.extract("BAD");
		assertNull(content);
	}

}
