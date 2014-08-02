package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class ToNumberServiceImplTest {

	@Test
	public void testGoodInteger() {
		ToNumberServiceImpl sut = new ToNumberServiceImpl();
		Integer value = sut.toNumber("23", Integer.class);
		assertEquals(new Integer(23), value);
	}

	@Test
	public void testGoodDouble() {
		ToNumberServiceImpl sut = new ToNumberServiceImpl();
		Double value = sut.toNumber("23", Double.class);
		assertEquals(new Double(23), value);
	}

	@Test
	public void testBadInput() {
		ToNumberServiceImpl sut = new ToNumberServiceImpl();
		Double value = sut.toNumber("invalid", Double.class);
		assertNull(value);
	}
}
