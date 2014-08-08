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
		assertEquals("[Lorem, Ipsum, Dolor, Sit, Amet, Lorem Ipsum, Lorem Dolor, Lorem Sit, Lorem Amet, Ipsum Dolor, Ipsum Sit, Ipsum Amet, Dolor Sit, Dolor Amet, Sit Amet]", actual);
	}

}
