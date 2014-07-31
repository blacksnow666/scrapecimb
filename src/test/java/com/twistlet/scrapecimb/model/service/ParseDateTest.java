package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class ParseDateTest {

	@Test
	public void testParseDate() {
		String val = "Saturday, 09 Aug 2014,";
		String[] components = StringUtils.split(val, ",");
		assertEquals(2, components.length);
	}
}
