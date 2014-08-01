package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessageFormatServiceImplTest {

	@Test
	public void testFormat() {
		MessageFormatServiceImpl sut = new MessageFormatServiceImpl("Hello {0}");
		assertEquals("Hello world", sut.format("world"));
	}
}
