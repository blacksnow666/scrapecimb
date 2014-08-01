package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class SelectorServiceImplTest {

	@Test
	public void testSelect() throws IOException {
		SelectorServiceImpl sut = new SelectorServiceImpl("a");
		Document document = Jsoup.parse(IOUtils.toString(new ClassPathResource(
				"html/detail-silver-1").getInputStream()));
		List<Element> items = sut.select(document);
		assertEquals(32, items.size());
	}

}
