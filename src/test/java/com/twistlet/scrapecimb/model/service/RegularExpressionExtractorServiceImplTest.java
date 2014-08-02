package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class RegularExpressionExtractorServiceImplTest {

	@Test
	public void testExtractNoMatch() {
		RegularExpressionExtractorServiceImpl sut = new RegularExpressionExtractorServiceImpl();
		List<String> list = sut.extract("([0-9]+)", "i need two dozen eggs");
		assertEquals("[]", list.toString());
	}

	@Test
	public void testExtractMatchSingle() {
		RegularExpressionExtractorServiceImpl sut = new RegularExpressionExtractorServiceImpl();
		List<String> list = sut.extract("need ([0-9]+)", "i need 2 dozen eggs");
		assertEquals("[2]", list.toString());
	}

	@Test
	public void testExtractMatchMultiple() {
		RegularExpressionExtractorServiceImpl sut = new RegularExpressionExtractorServiceImpl();
		List<String> list = sut
				.extract("Name: (.+), Age: (.+),",
						"Name: Master Yoda, Age: Very Very Old, Height: Very Very Short");
		assertEquals("[Master Yoda, Very Very Old]", list.toString());
	}

	@Test
	public void testExtractMatchMultipleTimes() {
		RegularExpressionExtractorServiceImpl sut = new RegularExpressionExtractorServiceImpl();
		List<String> list = sut.extract("([0-9])", "A-113");
		assertEquals("[1, 1, 3]", list.toString());
	}
}
