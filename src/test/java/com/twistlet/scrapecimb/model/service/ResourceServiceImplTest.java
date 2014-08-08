package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ResourceServiceImplTest {
	private ResourceServiceImpl sut;

	@Before
	public void init() {
		sut = new ResourceServiceImpl();
	}

	@Test
	public void testSingleLine() {
		List<String> lines = sut.readLines("txt/abc-single-line.txt");
		assertEquals(1, lines.size());
		assertEquals("abc", lines.get(0));
	}

	@Test
	public void testMultipleLine() {
		List<String> lines = sut.readLines("txt/abc-multiple-line.txt");
		assertEquals(3, lines.size());
		assertEquals("a", lines.get(0));
		assertEquals("b", lines.get(1));
		assertEquals("c", lines.get(2));
	}

	@Test
	public void testInvalid() {
		List<String> lines = sut.readLines("txt/file-does-not-exist.txt");
		assertNull(lines);
	}

}
