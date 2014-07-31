package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.twistlet.scrapecimb.model.service.AuctionIdServiceImpl;

public class AuctionIdServiceImplTest {

	@Test
	public void testListAuctionId() throws IOException {
		String baseUri = "http://www.cimbbank.com.my/propertymart/";
		String charsetName = "UTF-8";
		Resource resource = new ClassPathResource("html/index");
		Document document = Jsoup.parse(resource.getInputStream(), charsetName,
				baseUri);
		AuctionIdServiceImpl sut = new AuctionIdServiceImpl();
		List<Long> list = sut.listAuctionId(document);
		String actual = list.toString();
		String expected = "[10203, 10204, 10211, 10209, 10208, 10207, 10206, 10210, 10205, 10212, 10213]";
		assertEquals(expected, actual);
	}

}
